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

import java.awt.Image;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.beamproject.common.util.Exceptions;

/**
 * This class provides several methods to configure often used {@link JFrame}
 * settings.
 */
public class Frames {

    final static String[] ICON_SIZES = new String[]{"16x16", "24x24", "32x32", "48x48", "64x64"};

    /**
     * Sets the icons to the given {@link JFrame}.
     *
     * @param frame The frame on which the icons should be set.
     * @throws IllegalArgumentException If the argument is null.
     */
    public static void setIcons(JFrame frame) {
        Exceptions.verifyArgumentsNotNull(frame);

        List<Image> icons = new LinkedList<>();

        for (String size : ICON_SIZES) {
            try {
                Image img = ImageIO.read(Frames.class.getResourceAsStream("/org/beamproject/client/ui/" + size + ".png"));
                icons.add(img);
            } catch (IOException ex) {
                throw new IllegalStateException("Could not load the application icons: " + ex.getMessage());
            }
        }

        frame.setIconImages(icons);
    }

}
