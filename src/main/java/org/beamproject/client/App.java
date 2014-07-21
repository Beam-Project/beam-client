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

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.beamproject.client.model.MainModel;
import org.beamproject.client.view.MainWindow;

/**
 * The main class of this application.
 */
public class App {

    public final static String NAME = "beam-client";
    public final static String POM_VERSION = "0.0.1"; // Do NOT change this, Maven replaces it.
    public final static String WEBSITE = "https://www.beamproject.org/";
    public final static String CONFIG_DIRECTORY_PATH = System.getProperty("user.home") + File.separator + ".beam" + File.separator;
    public final static String CONFIG_PATH = CONFIG_DIRECTORY_PATH + "client.conf";
    public final static String ENCRYPTED_CONFIG_PATH = CONFIG_DIRECTORY_PATH + "client-encrypted.conf";
    private static MainWindow window;
    private static MainModel model;

    public static void main(String args[]) {
        setNativeLookAndFeel();

        Injector appInjector = Guice.createInjector(new AppModule());
        window = appInjector.getInstance(MainWindow.class);
        model = appInjector.getInstance(MainModel.class);
        model.bootstrap();

        window.setVisible(true);
    }

    private static void setNativeLookAndFeel() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        boolean isUnix = operatingSystem.contains("nix")
                || operatingSystem.contains("nux")
                || operatingSystem.contains("aix");

        try {
            if (isUnix) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                enableXrenderOnX11BasedDesktops();
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                useDirecd3dOnly();
            }
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new IllegalStateException("The Look&Feel could not be configured correctly: " + ex.getMessage());
        }

        enableFontAntiAliasing();
    }

    /**
     * Use sysetm anti-aliasing font settings: ANTIALIAS_LCD_HRGB<br />
     * See:
     * https://docs.oracle.com/javase/7/docs/technotes/guides/2d/flags.html#aaFonts
     */
    private static void enableFontAntiAliasing() {
        System.setProperty("awt.useSystemAAFontSettings", "lcd");
    }

    /**
     * Use D3D only on Windows platform for better performance.<br />
     * See:
     * https://docs.oracle.com/javase/7/docs/technotes/guides/2d/flags.html#noddraw
     */
    private static void useDirecd3dOnly() {
        System.setProperty("sun.java2d.noddraw", "true");
    }

    /**
     * See:
     * https://docs.oracle.com/javase/7/docs/technotes/guides/2d/flags.html#xrender
     */
    private static void enableXrenderOnX11BasedDesktops() {
        System.setProperty("sun.java2d.xrender", "true");
    }

}
