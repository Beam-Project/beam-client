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
package org.beamproject.client;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.aeonbits.owner.ConfigFactory;
import org.beamproject.client.ui.Frames;
import org.beamproject.client.ui.MainWindow;
import org.beamproject.client.ui.SetUpDialog;
import org.beamproject.common.Participant;
import org.beamproject.common.crypto.EncryptedKeyPair;
import org.beamproject.common.crypto.KeyPairCryptor;
import org.beamproject.common.util.ConfigWriter;

/**
 * The main class of this application.
 */
public class App {

    public final static Color DEFAULT_BACKGROUND = null; // is really null
    public final static Color ERROR_BACKGROUND = new Color(255, 153, 153);
    static String CONTACTS_STORAGE_FILE = Config.FOLDER + "contacts.storage";
    static ConfigWriter configWriter = new ConfigWriter();
    static Config config = ConfigFactory.create(Config.class);
    static Controller controller;
    static Model model;
    static MainWindow mainWindow;

    public static void main(String args[]) {
        setNativeLookAndFeel();
        showMainWindow();
        loadControllerAndModel();
        loadUser();
        loadContactList();
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
                mainWindow.setAutoRequestFocus(isFirstStart());
                mainWindow.setVisible(true);
            }
        });
    }

    static void loadControllerAndModel() {
        controller = new Controller();
        model = new Model();
    }

    static void loadUser() {
        if (isFirstStart()) {
            showSetUpDialog();
            generateUser();
            storeConfig();
        }
    }

    static boolean isFirstStart() {
        return model.getUser() == null;
    }

    private static void showSetUpDialog() {
        SetUpDialog dialog = new SetUpDialog();
        Frames.setIcons(dialog);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static void generateUser() {
        Participant user = Participant.generate();
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(config.keyPairPassword(), user.getKeyPair());
        config.setProperty("keyPairSalt", encryptedKeyPair.getSalt());
        config.setProperty("encryptedPublicKey", encryptedKeyPair.getEncryptedPublicKey());
        config.setProperty("encryptedPrivateKey", encryptedKeyPair.getEncryptedPrivateKey());

        model.setUser(user);
    }

    static void loadContactList() {
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

    public static void storeConfig() {
        configWriter.writeConfig(config, Config.FOLDER, Config.FILE);
    }

}
