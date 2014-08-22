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
package org.beamproject.client.util;

import com.google.inject.Inject;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides helper methods to open HTTP links in the default browser.
 */
public class Links {

    @Inject
    static Logger log;

    /**
     * Opens the given {@code http} link with the configured web browser.
     *
     * @param link The link to open.
     */
    public static void openHttpLink(String link) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URL(link).toURI());
                return;
            } catch (IOException | URISyntaxException ex) {
                log.log(Level.WARNING, "Could not open the website {0} since: {1}", new Object[]{link, ex.getMessage()});
            }
        }

        openAsProcess(link, "x-www-browser");
    }

    private static void openAsProcess(String link, String type) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(type, link);
            processBuilder.start();
        } catch (IOException ex) {
            log.log(Level.WARNING, "Could not open the link {0} as process since: {1}", new Object[]{link, ex.getMessage()});
        }
    }
}
