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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.inchat.client.ui.Frames;
import org.inchat.client.ui.MainWindow;
import org.inchat.client.ui.SetUpDialog;
import org.inchat.common.Config;
import org.inchat.common.Participant;
import org.inchat.common.crypto.EccKeyPairGenerator;
import org.inchat.common.util.Base64;

/**
 * The main class of this application.
 */
public class App {

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
        if (config.isKeyExisting(ClientConfigKey.publicKey)) {
            //TODO load participant to model
        } else {
            showSetUpDialog();
            generateAndStoreParticipant();
        }
    }

    private static void showSetUpDialog() {
        SetUpDialog dialog = new SetUpDialog();
        Frames.setIcons(dialog);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static void generateAndStoreParticipant() {
        Participant participant = new Participant(EccKeyPairGenerator.generate());

        String publicKey = Base64.encode(participant.getPublicKeyAsBytes());
        String privateKey = Base64.encode(participant.getPrivateKeyAsBytes());

        config.setProperty(ClientConfigKey.publicKey, publicKey);
        config.setProperty(ClientConfigKey.privateKey, privateKey);
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
