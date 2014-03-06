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

import java.io.File;
import javax.swing.JPanel;
import static org.easymock.EasyMock.*;
import org.inchat.client.AppTest;
import org.inchat.client.Controller;
import org.inchat.client.ui.MainWindow;
import org.inchat.common.Config;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SettingsWindowTest {

    private final String CONFIG_FILE = "settingsWindow.conf";
    private SettingsWindow window;
    private MainWindow mainWindow;
    private Controller controller;

    @Before
    public void setUp() {
        mainWindow = createMock(MainWindow.class);
        controller = createMock(Controller.class);
        AppTest.setAppMainWindow(mainWindow);
        AppTest.setAppController(controller);

        Config.createDefaultConfig(CONFIG_FILE);
        Config.loadConfig(CONFIG_FILE);

        window = new SettingsWindow();
    }

    @After
    public void cleanUp() {
        File configFile = new File(CONFIG_FILE);
        configFile.delete();
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
        window.menuList.setSelectedIndex(SettingsWindow.GENERAL_MENU_INDEX);
        assertSame(window.getGeneralPanel(), window.contentPanel.getComponent(0));
        assertTrue(window.contentPanel.isValid());

        window.menuList.setSelectedIndex(SettingsWindow.IDENTITY_MENU_INDEX);
        assertSame(window.getIdentityPanel(), window.contentPanel.getComponent(0));
        assertTrue(window.contentPanel.isValid());

        window.menuList.setSelectedIndex(SettingsWindow.SECURITY_MENU_INDEX);
        assertSame(window.getSecurityPanel(), window.contentPanel.getComponent(0));
        assertTrue(window.contentPanel.isValid());

        window.menuList.setSelectedIndex(SettingsWindow.NETWORK_MENU_INDEX);
        assertSame(window.getNetworkPanel(), window.contentPanel.getComponent(0));
        assertTrue(window.contentPanel.isValid());
    }

    @Test
    public void testShowIdentityNameWithFocusedName() {
        String name = "my name";
        mainWindow.setUsername(name);
        expectLastCall().anyTimes();
        controller.changeName(name);
        expectLastCall().anyTimes();
        replay(mainWindow, controller);

        Config.setProperty(Config.Key.participantName, name);
        window.openIdentityMenuWithFocusedName();

        assertTrue(window.contentPanel.getComponent(0) instanceof IdentityPanel);
        IdentityPanel panel = (IdentityPanel) window.contentPanel.getComponent(0);

        String selectedText = panel.nameTextField.getSelectedText();
        assertEquals(name, selectedText);

        verify(mainWindow, controller);
    }

    @Test
    public void testCloseButtonOnDisposingWindow() {
        controller.closeSettingsWindow();
        expectLastCall();
        replay(controller);

        window.closeButton.doClick();

        verify(controller);
    }

    @Test
    public void testGetGeneralPanel() {
        assertNotNull(window.generalPanel);
        assertNotNull(window.getGeneralPanel());
        assertSame(window.generalPanel, window.getGeneralPanel());
    }

    @Test
    public void testGetSecurityPanel() {
        assertNull(window.securityPanel);
        assertNotNull(window.getSecurityPanel());
        assertSame(window.securityPanel, window.getSecurityPanel());
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

    /**
     * This method provides access to the content panel for testing purposes.
     *
     * @param settingsWindow A instance of {@link SettingsWindow} from which the
     * content panel will be retrieved.
     * @return The content panel of the given argument.
     */
    public static JPanel getContentPanel(SettingsWindow settingsWindow) {
        return settingsWindow.contentPanel;
    }

}
