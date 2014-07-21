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

import java.awt.Image;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComponentsTest {

    @Test
    public void testSetIconsOnSizes() {
        assertNotNull(Components.ICON_SIZES);
        assertTrue(Components.ICON_SIZES.length > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIconsOnNull() {
        Components.setIcons(null);
    }

    @Test
    public void testSetIcons() {
        JFrame frame = new JFrame();
        Components.setIcons(frame);

        List<Image> images = frame.getIconImages();
        assertNotNull(images);
        assertEquals(Components.ICON_SIZES.length, images.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLayoutHtmlLabelsOnNull() {
        Components.layoutHtmlLabels(null);
    }

    @Test
    public void testLayoutHtmlLabelsOnEmptyArray() {
        Components.layoutHtmlLabels(new JLabel[0]);
    }

    @Test
    public void testLayoutHtmlLabelsOnParentlessLabel() {
        Components.layoutHtmlLabels(new JLabel());
    }

}
