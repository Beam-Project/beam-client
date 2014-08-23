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

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.beamproject.common.util.Exceptions;

/**
 * This class provides several methods to configure often used
 * {@link Components} settings.
 */
public class Components {

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
                Image img = ImageIO.read(Components.class.getResourceAsStream("/org/beamproject/client/view/" + size + ".png"));
                icons.add(img);
            } catch (IOException ex) {
                throw new IllegalStateException("Could not load the application icons: " + ex.getMessage());
            }
        }

        frame.setIconImages(icons);
    }

    /**
     * Calculates the concrete height of each of the given {@link JLabel}s in
     * order to prevent flickering. This can occur when the text of the label is
     * wrapped in HTML tags and breaks the line to display all text.
     * <p>
     * This may be invoked in the container that holds the labels, in a way like
     * this:
     * <p>
     * <code>public void setVisible(boolean visible) {<br />
     * &nbsp;&nbsp;&nbsp;&nbsp;Components.layoutHtmlLabels(label1, label2); <br
     * />
     * &nbsp;&nbsp;&nbsp;&nbsp;super.setVisible(visible);<br />
     * }</code>
     * <p>
     * The idea originally came from <a
     * href="http://stackoverflow.com/a/24004330">How to update a JComponent
     * with HTML without flickering?</a>.
     *
     * @param labels The labels to lay out before visualized.
     */
    public static void layoutHtmlLabels(JLabel... labels) {
        Exceptions.verifyArgumentsNotNull((Object) labels);

        if (labels.length == 0) {
            return;
        }

        final Container validateRoot = findValidateRoot(labels[0]);
        final Graphics graphics = validateRoot.getGraphics();

        if (graphics == null) {
            return;
        }

        validateRoot.validate();
        Color foreground;

        for (JLabel label : labels) {
            foreground = label.getForeground();
            label.setForeground(label.getBackground());
            label.paint(graphics);
            label.setForeground(foreground);
        }

        validateRoot.validate();
    }

    private static Container findValidateRoot(JLabel label) {
        Container validateRootCandidate = label;

        while (!validateRootCandidate.isValidateRoot()) {
            Container parent = validateRootCandidate.getParent();

            if (parent == null) {
                break;
            }

            validateRootCandidate = parent;
        }

        return validateRootCandidate;
    }

}
