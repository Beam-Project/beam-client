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
import java.util.List;
import javax.swing.JFrame;
import org.junit.Test;
import static org.junit.Assert.*;

public class FramesTest {

    @Test
    public void testSetIconsOnSizes() {
        assertNotNull(Frames.ICON_SIZES);
        assertTrue(Frames.ICON_SIZES.length > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIconsOnNull() {
        Frames.setIcons(null);
    }

    @Test
    public void testSetIcons() {
        JFrame frame = new JFrame();
        Frames.setIcons(frame);

        List<Image> images = frame.getIconImages();
        assertNotNull(images);
        assertEquals(Frames.ICON_SIZES.length, images.size());
    }

}
