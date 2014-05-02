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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.aeonbits.owner.ConfigFactory;
import org.beamproject.common.util.ConfigWriter;
import org.beamproject.common.util.Executor;

/**
 * The main class of this application.
 */
public class App {

    public final static Color DEFAULT_BACKGROUND = null; // is really null
    public final static Color ERROR_BACKGROUND = new Color(255, 153, 153);
    static ConfigWriter configWriter = new ConfigWriter();
    static Config config = ConfigFactory.create(Config.class);
    static Executor executor = new Executor();
    static Controller controller;
    static Model model;

    public static void main(String args[]) {
        setNativeLookAndFeel();
        loadControllerAndModel();
        controller.showMainWindow();
        controller.loadUser();
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

    static void loadControllerAndModel() {
        controller = new Controller();
        model = new Model();
    }

    public static Controller getController() {
        return controller;
    }

    public static Model getModel() {
        return model;
    }

    public static Config getConfig() {
        return config;
    }

    public static Executor getExecutor() {
        return executor;
    }

}
