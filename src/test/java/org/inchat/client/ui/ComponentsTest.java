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
package org.inchat.client.ui;

import java.awt.TextField;
import org.inchat.client.App;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

public class ComponentsTest {

    private TextField textField;

    @Before
    public void setUp() {
        textField = new TextField();
    }

    @Test
    public void testColorInitalizing() {
        assertNull(App.DEFAULT_BACKGROUND);
        assertNotNull(App.ERROR_BACKGROUND);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDefalutBackgroundOnNull() {
        Components.setDefalutBackground(null);
    }

    @Test
    public void testSetDefaultBackground() {
        Components.setDefalutBackground(textField);
        assertEquals(textField.getBackground(), App.DEFAULT_BACKGROUND);

        textField.setBackground(App.ERROR_BACKGROUND);
        Components.setDefalutBackground(textField);
        assertEquals(textField.getBackground(), App.DEFAULT_BACKGROUND);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetErrorBackgroundOnNull() {
        Components.setErrorBackground(null);
    }

    @Test
    public void testSetErrorBackground() {
        Components.setErrorBackground(textField);
        assertEquals(textField.getBackground(), App.ERROR_BACKGROUND);
    }

}
