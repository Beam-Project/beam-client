/*
 * Copyright (C) 2013, 2014 beamproject.org
 *
 * This file is part of beam-client.
 *
 * beam-client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * beam-client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beamproject.client.model;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import static java.util.logging.Level.*;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.beamproject.client.App;
import static org.beamproject.client.Event.*;
import org.beamproject.common.util.Chars;
import org.beamproject.client.util.ClipboardAccess;
import org.beamproject.client.util.ConfigKey;
import org.beamproject.common.util.Config;
import static org.beamproject.client.util.ConfigKey.*;
import org.beamproject.common.crypto.EncryptedConfig;
import org.beamproject.common.util.Files;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import static org.beamproject.common.crypto.BouncyCastleIntegrator.initBouncyCastleProvider;
import org.beamproject.common.crypto.CryptoException;
import static org.beamproject.common.crypto.EccKeyPairGenerator.fromBothKeys;
import org.beamproject.common.util.Executor;
import org.beamproject.common.util.QrCode;
import org.beamproject.common.util.Task;

/**
 * This is the model handling the very central aspects of this application such
 * as the bootstrap sequence, encryption and decryption of the configuration,
 * etc..
 */
@Singleton
public class MainModel {

    /**
     * The possible candidates of senders from whom messages will be accepted.
     */
    public enum AcceptedSender {

        /**
         * Accept messages only from contacts (in contact list).
         */
        CONTACTS,
        /**
         * Accept messages from everyone.
         */
        EVERYONE
    }
    private static final int MAXIMAL_QR_CODE_SIDE_LENGTH_IN_PX = 200;
    @Inject
    Logger log;
    private final EventBus bus;
    private final Config<ConfigKey> config;
    private final Files files;
    private final Executor executor;
    @Getter
    @Setter
    private EncryptedConfig<ConfigKey> encryptedConfig;
    @Inject
    private ClipboardAccess clipboardAccess;
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private Server server;
    @Inject
    @Getter
    private ConnectionModel connectionModel;

    @Inject
    public MainModel(EventBus bus, Config<ConfigKey> config, Files files, Executor executor) {
        this.bus = bus;
        this.config = config;
        this.files = files;
        this.executor = executor;
    }

    /**
     * Starts the application up and provides the required environment. This
     * method contains all the logic needed to bring the application into the
     * <i>ready-to-use</i> state.
     */
    public void bootstrap() {
        integrateBouncyCastle();

        if (isFirstStart()) {
            bus.post(SHOW_WIZARD_WELCOME_LAYER);
            return;
        }

        if (isPasswordRemembered()) {
            unlockData(Chars.bytesToUtfChars(config.getAsBytes(PASSWORD)));
            return;
        }

        bus.post(SHOW_UNLOCK_LAYER);
    }

    private void integrateBouncyCastle() {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                initBouncyCastleProvider();
            }
        });
    }

    private boolean isFirstStart() {
        return !config.contains(SALT);
    }

    /**
     * Searches the {@link Config} for password and salt. If both is there, the
     * password is expected to be remembered.
     * <p>
     * This method does not check whether or not the password (and salt) is
     * correct, just if it's there.
     *
     * @return true if salt and password are stored in {@link Config}, false
     * otherwise.
     */
    public boolean isPasswordRemembered() {
        return config.contains(PASSWORD)
                && !config.getAsString(PASSWORD).trim().isEmpty()
                && config.contains(SALT)
                && !config.getAsString(SALT).trim().isEmpty();
    }

    /**
     * Unlocks the {@link EncryptedConfig} and other locally encrypted stored
     * data such as contact list and messages.
     *
     * @param password The password, used to decrypted the data.
     */
    public void unlockData(char[] password) {
        try {
            loadEncryptedConfig(password);
            restoreUserAndServer();
            applyUserEnvironment();
        } catch (IllegalArgumentException | CryptoException ex) {
            bus.post(UNLOCK_LAYER_WRONG_PASSWORD);
            log.log(INFO, "Could not decrypt data, possibly password wrong: {0}", ex.getMessage());
        }
    }

    private void loadEncryptedConfig(char[] password) {
        Properties encryptedConfigs = files.loadConfigIfAvailable(App.ENCRYPTED_CONFIG_PATH);
        byte[] salt = config.getAsBytes(SALT);

        encryptedConfig = new EncryptedConfig<>(password, salt, encryptedConfigs);
    }

    private void restoreUserAndServer() {
        if (encryptedConfig.contains(SERVER_ADDRESS)) {
            server = new Server(encryptedConfig.getAsString(SERVER_ADDRESS));
            user = new User(encryptedConfig.getAsString(USERNAME), restoreKeyPair(), server);
        } else {
            user = new User(encryptedConfig.getAsString(USERNAME), restoreKeyPair());
        }
    }

    private KeyPair restoreKeyPair() {
        byte[] publicKeyBytes = encryptedConfig.getAsBytes(USER_PUBLIC_KEY);
        byte[] privateKeyBytes = encryptedConfig.getAsBytes(USER_PRIVATE_KEY);
        return fromBothKeys(publicKeyBytes, privateKeyBytes);
    }

    /**
     * Triggers that the view loads its default values and that all layers are
     * being hidden so that the main window will be on top.
     */
    public void applyUserEnvironment() {
        bus.post(ENCRYPTED_CONFIG_UNLOCKED);
        bus.post(SHOW_MAIN_WINDOW);
    }

    /**
     * Stores the {@link Config} and the {@link EncryptedConfig} via
     * {@link Properties} to the configured file paths.
     *
     * @throws IllegalStateException If no instance of {@link EncryptedConfig}
     * is available.
     */
    public void storeConfigs() {
        if (encryptedConfig == null) {
            throw new IllegalStateException("The encrypted config may not be null.");
        }

        Properties configProperties = config.copyToProperties();
        files.storeProperies(configProperties, App.CONFIG_PATH);

        Properties encryptedConfigProperties = encryptedConfig.copyToProperties();
        files.storeProperies(encryptedConfigProperties, App.ENCRYPTED_CONFIG_PATH);
    }

    /**
     * Generates a QR code containing the Beam address of the {@link User}. When
     * the given with is too large, the configured maximal width will be used.
     *
     * @param qrCodeLableWidth The <i>suggested</i> width of the QR code.
     * @return The image.
     */
    public BufferedImage getUserAddressQrCode(int qrCodeLableWidth) {
        int dimension = qrCodeLableWidth > MAXIMAL_QR_CODE_SIDE_LENGTH_IN_PX
                ? MAXIMAL_QR_CODE_SIDE_LENGTH_IN_PX : qrCodeLableWidth;
        return QrCode.encode(user.getAddress(), dimension);
    }

    public void copyAddressToClipboard() {
        clipboardAccess.copyTextToClipboard(user.getAddress());
    }

    /**
     * Sets the given username to the model an to the {@link EncryptedConfig}
     * (but does not store the instance of {@link EncryptedConfig} to the file
     * system).
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        user.setUsername(username);
        encryptedConfig.set(USERNAME, username);
    }

    public void connect(final boolean doConnect) {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                if (doConnect) {
                    log.info("Connecting...");
                    connectionModel.setUpConnectionPoolAndCarrier();
                    connectionModel.startAsyncReceiving();
                    connectionModel.startHandshake();
                } else {
                    log.info("Disconnecting...");
                    connectionModel.shutdown();
                }
            }
        });

        bus.post(UPDATE_CONNECTION_STATUS);
    }

    /**
     * Tells whether or not the password should be stored. If no, the password
     * in the {@link Config} instance will be overwritten. If yes, the password
     * from the {@link EncryptedConfig} instance will be stored as byte array in
     * the {@link Config} instance.
     * <p>
     * The password will never ever be handed around as a String except when
     * it's being read from the file system into the {@link Config} or written
     * to that in order to remember the password.
     * <p>
     * The password is always (once the client is unlocked) stored in the
     * instance of {@link EncryptedConfig} as char array. It is also stored as
     * byte array in the {@link Config} instance, if it should be remembered.
     *
     * @param doRemember true to remember the password, false otherwise.
     */
    public void rememberPassword(boolean doRemember) {
        destroyConfigObjectPassword();

        config.set(PASSWORD,
                doRemember
                ? Chars.utfCharsToBytes(encryptedConfig.getPassword())
                : "".getBytes());
    }

    private void destroyConfigObjectPassword() {
        if (config.contains(PASSWORD)) {
            byte[] oldPassword = config.getAsBytes(PASSWORD);
            Arrays.fill(oldPassword, (byte) 0);
        }
    }

    /**
     * Stores the configurations and destroys the password(s) held in memory if
     * the {@link EncryptedConfig} has ever been unlocked during runtime.
     * <p>
     * Afterwards, the application window is triggered to dispose.
     */
    public void shutdown() {
        if (encryptedConfig != null) {
            storeConfigs();
            destroyPassword();
        }

        bus.post(DISPOSE);
    }

    /**
     * Shuts down and starts up the application. This is used when the server
     * address changes.
     */
    public void restart() {
        if (encryptedConfig != null) {
            storeConfigs();
            destroyPassword();
        }

        runSameJarAgain();

        bus.post(DISPOSE);
    }

    private void runSameJarAgain() {
        String jrePath = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        File binary;

        try {
            binary = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            log.log(SEVERE, "Could not find the current jar file as URI: {0}", ex.getMessage());
            return;
        }

        if (!isJarFile(binary)) {
            return;
        }

        try {
            List<String> command = assembleApplicationStartCommand(jrePath, binary);
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException ex) {
            log.log(SEVERE, "An error occurred while restarting application: {0}", ex.getMessage());
        }
    }

    private boolean isJarFile(File file) {
        return file.getName().endsWith(".jar");
    }

    private List<String> assembleApplicationStartCommand(String jrePath, File binary) {
        final ArrayList<String> command = new ArrayList<>();
        command.add(jrePath);
        command.add("-jar");
        command.add(binary.getPath());

        return command;
    }

    private void destroyPassword() {
        destroyConfigObjectPassword();
        Arrays.fill(encryptedConfig.getPassword(), (char) 0);
    }

}
