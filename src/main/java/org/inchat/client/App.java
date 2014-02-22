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

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.inchat.client.ui.Frames;
import org.inchat.client.ui.MainWindow;
import org.inchat.client.ui.SetUpDialog;
import org.inchat.common.Config;
import org.inchat.common.crypto.KeyPairStore;

public class App {

    public final static Color DEFAULT_BACKGROUND = null; // is really null
    public final static Color ERROR_BACKGROUND = new Color(255, 153, 153);
    static String CONFIG_DIRECTORY = System.getProperty("user.home") + "/.inchat-client/";
    static String CONFIG_FILE = CONFIG_DIRECTORY + "client.conf";
    static String DATABASE_FILE = CONFIG_DIRECTORY + "client.db";
    static Controller controller = new Controller();
    static Model model = new Model();
    static MainWindow mainWindow;
    
    public static void main(String args[]) {
        setNativeLookAndFeel();
        showMainWindow();
        loadConfig();
        loadParticipant();
    }

    private static void setNativeLookAndFeel() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        boolean isUnix = operatingSystem.contains("nix")
                || operatingSystem.contains("nux")
                || operatingSystem.contains("aix");

        try {
            if (isUnix) {
                UIManager.setLookAndFeel(new GTKLookAndFeel());
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

    static void loadConfig() {
        File configFile = new File(CONFIG_FILE).getAbsoluteFile();

        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }

        if (!configFile.exists()) {
            Config.createDefaultConfig(CONFIG_FILE);
        }

        Config.loadConfig(CONFIG_FILE);
    }

    static void loadParticipant() {
        if (!isProfileExisting()) {
            SetUpDialog dialog = new SetUpDialog();
            Frames.setIcons(dialog);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        Config.loadOrCreateParticipant();
    }

    private static boolean isProfileExisting() {
        String keyPairFilename = Config.getProperty(Config.Key.keyPairFilename);
        File publicKey = new File(CONFIG_DIRECTORY + keyPairFilename + KeyPairStore.PUBILC_KEY_FILE_EXTENSION);

        return publicKey.exists();
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

}
