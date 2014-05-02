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
package org.beamproject.client.ui;

import javax.swing.JOptionPane;

/**
 * This class collects all {@link JOptionPane} messages.
 */
public abstract class Dialogs {

    /**
     * Shows info message that says that the account/server configuration is
     * incomplete.
     */
    public static void showConfigurationIncomplete() {
        JOptionPane.showMessageDialog(null,
                "<html>Unfortunately, the configuration of your accout is incomplete.<br />"
                + "Please go to <i>Settings > Identity</i> and compete it.<br />"
                + "Make sure to enter a valid <b>Server URL</b>, combined with the <b>Public Key</b> of that server. </html>",
                "Server Not Configured",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
