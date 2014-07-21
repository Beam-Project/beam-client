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
import java.security.KeyPair;
import lombok.experimental.Delegate;
import static org.beamproject.client.Event.*;
import static org.beamproject.client.model.MainModel.AcceptedSender.CONTACTS;
import org.beamproject.client.util.ConfigKey;
import org.beamproject.common.util.Config;
import static org.beamproject.client.util.ConfigKey.*;
import org.beamproject.common.crypto.EncryptedConfig;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.beamproject.common.crypto.PasswordCryptor;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.beamproject.common.util.Executor;
import org.beamproject.common.util.Task;

/**
 * This is the model handling the logic required by the wizard used when setting
 * up the client.
 */
@Singleton
public class WizardModel {

    @Delegate
    private final MainModel mainModel;
    private final EventBus bus;
    private final Config<ConfigKey> config;
    private final Executor executor;
    EncryptedConfig<ConfigKey> encryptedConfig;
    String username, activationCode;
    Server server;
    User user;
    char[] password;
    boolean rememberPassword;

    @Inject
    WizardModel(MainModel mainModel, EventBus bus, Config<ConfigKey> config, Executor executor) {
        this.mainModel = mainModel;
        this.bus = bus;
        this.config = config;
        this.executor = executor;
    }

    /**
     * Takes the username and creates a new {@link User} with that, what also
     * creates a new {@link KeyPair}.
     * <p>
     * The activationCode is used to configure the {@link Server} where the user
     * has an account.
     *
     * @param username The username to use.
     * @param activationCode The activation code for the server.
     */
    public void processWelcomeLayer(String username, String activationCode) {
        this.username = username;
        this.activationCode = activationCode;

        bus.post(SHOW_WIZARD_ADDRESS_LAYER);

        createUserByNameAndActivationCode();
    }

    private void createUserByNameAndActivationCode() {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                KeyPair keyPair = EccKeyPairGenerator.generate();
                server = new Server(activationCode);
                user = new User(username, keyPair, server);
                mainModel.setUser(user);

                bus.post(ENABLE_WIZARD_ADDRESS_GENERATED_ADDRESS);
            }
        });
    }

    public void processAddressLayer() {
        bus.post(SHOW_WIZARD_PASSWORD_LAYER);
    }

    /**
     * Uses the password to create an instance of {@link EncryptedConfig} and
     * stores it in plain text, depending on {@code rememberPassword}.
     *
     * @param password The password to use for the {@link PasswordCryptor}.
     * @param rememberPassword Whether or not the password should be stored in
     * plaintext.
     */
    public void processPasswordLayer(char[] password, boolean rememberPassword) {
        this.password = password;
        this.rememberPassword = rememberPassword;

        createAndStoreConfigs();
    }

    private void createAndStoreConfigs() {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                createEncryptedConfig();
                mainModel.rememberPassword(rememberPassword);
                copyAccoutDataToEncryptedConfig();
                createDefaultValues();
                mainModel.storeConfigs();
                applyUserEnvironment();
            }
        });
    }

    private void createEncryptedConfig() {
        byte[] salt = PasswordCryptor.generateSalt();
        encryptedConfig = new EncryptedConfig<>(password, salt);

        config.set(SALT, salt);
        mainModel.setEncryptedConfig(encryptedConfig);
    }

    private void copyAccoutDataToEncryptedConfig() {
        encryptedConfig.set(USERNAME, user.getUsername());
        encryptedConfig.set(USER_PUBLIC_KEY, user.getPublicKeyAsBytes());
        encryptedConfig.set(USER_PRIVATE_KEY, user.getPrivateKeyAsBytes());
        encryptedConfig.set(SERVER_ADDRESS, server.getAddress());
    }

    private void createDefaultValues() {
        encryptedConfig.set(ACCEPTED_MESSAGE_SENDER, CONTACTS.toString());
    }

}
