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
package org.inchat.client.ui.settings;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SettingsWindowTest {

    private SettingsWindow window;

    @Before
    public void setUp() {
        window = new SettingsWindow();
    }

    @Test
    public void testConstructorOnLoadingMenuCellRenderer() {
        assertTrue(window.menuList.getCellRenderer() instanceof MenuCellRenderer);
    }

    @Test
    public void testConstructorOnLoadingGeneralPanel() {
        assertSame(window.generalPanel, window.contentPanel.getComponent(0));
    }

    @Test
    public void testMenuListOnDefaultPanel() {
        window.menuList.clearSelection();
        assertSame(window.getGeneralPanel(), window.contentPanel.getComponent(0));
    }

    @Test
    public void testMenuListOnSelectionChange() {
        window.menuList.setSelectedIndex(0);
        assertSame(window.getGeneralPanel(), window.contentPanel.getComponent(0));
        assertTrue(window.contentPanel.isValid());

        window.menuList.setSelectedIndex(1);
        assertSame(window.getIdentityPanel(), window.contentPanel.getComponent(0));
        assertTrue(window.contentPanel.isValid());

        window.menuList.setSelectedIndex(2);
        assertSame(window.getNetworkPanel(), window.contentPanel.getComponent(0));
        assertTrue(window.contentPanel.isValid());
    }

    @Test
    public void testCloseButtonOnDisposingWindow() {
        assertTrue(window.isDisplayable());
        window.closeButton.doClick();
        assertFalse(window.isDisplayable());
    }

    @Test
    public void testGetGeneralPanel() {
        assertNotNull(window.generalPanel);
        assertNotNull(window.getGeneralPanel());
        assertSame(window.generalPanel, window.getGeneralPanel());
    }

    @Test
    public void testGetIdentityPanel() {
        assertNull(window.identityPanel);
        assertNotNull(window.getIdentityPanel());
        assertSame(window.identityPanel, window.getIdentityPanel());
    }

    @Test
    public void testGetNetworkPanel() {
        assertNull(window.networkPanel);
        assertNotNull(window.getNetworkPanel());
        assertSame(window.networkPanel, window.getNetworkPanel());
    }

}
