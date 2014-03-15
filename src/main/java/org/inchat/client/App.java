/*
 * Copyright (C) 2013, 2014 inchat.org
 *
 * This file is part of inchat-client.
 *
 * inchat-client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * inchat-client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.inchat.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.security.KeyPair;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.inchat.client.ui.Frames;
import org.inchat.client.ui.MainWindow;
import org.inchat.client.ui.SetUpDialog;
import org.inchat.common.Config;
import org.inchat.common.Participant;
import org.inchat.common.crypto.EccKeyPairGenerator;
import org.inchat.common.crypto.EncryptedKeyPair;
import org.inchat.common.crypto.KeyPairCryptor;

/**
 * The main class of this application.
 */
public class App {

    public final static String DEFAULT_KEY_PAIR_PASSWORD = "default-password";
    public final static Color DEFAULT_BACKGROUND = null; // is really null
    public final static Color ERROR_BACKGROUND = new Color(255, 153, 153);
    static String CONFIG_DIRECTORY = System.getProperty("user.home") + "/.inchat-client/";
    static String CONFIG_FILE = CONFIG_DIRECTORY + "client.conf";
    static String CONTACTS_STORAGE_FILE = CONFIG_DIRECTORY + "contacts.storage";
    static Config config;
    static Controller controller;
    static Model model;
    static MainWindow mainWindow;

    public static void main(String args[]) {
        setNativeLookAndFeel();
        showMainWindow();
        loadConfigControllerModel();
        loadParticipant();
        readContactList();
    }

    private static void setNativeLookAndFeel() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        boolean isUnix = operatingSystem.contains("nix")
                || operatingSystem.contains("nux")
                || operatingSystem.contains("aix");

        try {
            if (isUnix) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new IllegalStateException("The Look&Feel could not be configured correctly: " + ex.getMessage());
        }
    }

    private static void showMainWindow() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainWindow = new MainWindow();
                Frames.setIcons(mainWindow);
                mainWindow.setVisible(true);
            }
        });
    }

    static void loadConfigControllerModel() {
        config = new Config(CONFIG_FILE);
        controller = new Controller(config);
        model = new Model();
    }

    static void loadParticipant() {
        if (isEncryptedKeyPairStored()) {
            readAndDecryptParticipantFromConfig();
        } else {
            showSetUpDialog();
            generateAndStoreParticipant();
        }
    }

    private static boolean isEncryptedKeyPairStored() {
        return config.isKeyExisting(ClientConfigKey.keyPairSalt)
                && config.isKeyExisting(ClientConfigKey.encryptedPublicKey)
                && config.isKeyExisting(ClientConfigKey.encryptedPrivateKey);
    }

    private static void readAndDecryptParticipantFromConfig() {
        String password = config.getProperty(ClientConfigKey.keyPairPassword);
        String salt = config.getProperty(ClientConfigKey.keyPairSalt);
        String encryptedPublicKey = config.getProperty(ClientConfigKey.encryptedPublicKey);
        String encryptedPrivateKey = config.getProperty(ClientConfigKey.encryptedPrivateKey);

        EncryptedKeyPair encryptedKeyPair = new EncryptedKeyPair(encryptedPublicKey, encryptedPrivateKey, salt);
        KeyPair keyPair = KeyPairCryptor.decrypt(password, encryptedKeyPair);
        model.setParticipant(new Participant(keyPair));
    }

    private static void showSetUpDialog() {
        SetUpDialog dialog = new SetUpDialog();
        Frames.setIcons(dialog);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static void generateAndStoreParticipant() {
        Participant participant = new Participant(EccKeyPairGenerator.generate());
        
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(DEFAULT_KEY_PAIR_PASSWORD, participant.getKeyPair());
        App.config.setProperty(ClientConfigKey.keyPairPassword, DEFAULT_KEY_PAIR_PASSWORD);
        App.config.setProperty(ClientConfigKey.keyPairSalt, encryptedKeyPair.getSalt());
        App.config.setProperty(ClientConfigKey.encryptedPublicKey, encryptedKeyPair.getEncryptedPublicKey());
        App.config.setProperty(ClientConfigKey.encryptedPrivateKey, encryptedKeyPair.getEncryptedPrivateKey());

        model.setParticipant(participant);
    }

    static void readContactList() {
        controller.readContactListStorage();
    }

    public static Controller getController() {
        return controller;
    }

    public static Model getModel() {
        return model;
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    public static Config getConfig() {
        return config;
    }

}
