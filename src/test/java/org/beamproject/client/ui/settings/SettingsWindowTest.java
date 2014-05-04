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
package org.beamproject.client.ui.settings;

import javax.swing.JPanel;
import org.beamproject.client.App;
import static org.easymock.EasyMock.*;
import org.beamproject.client.AppTest;
import org.beamproject.client.ConfigTest;
import org.beamproject.client.Controller;
import org.beamproject.client.ControllerTest;
import org.beamproject.client.Model;
import org.beamproject.client.ui.MainWindow;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

public class SettingsWindowTest {

    private final String USERNAME = "myName";
    private final String URL = "http://beamproject.org";
    private SettingsWindow window;
    private MainWindow mainWindow;
    private Controller controller;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        App.getConfig().setProperty("username", USERNAME);
        App.getConfig().setProperty("serverAddress", URL);

        mainWindow = createMock(MainWindow.class);
        controller = createMock(Controller.class);
        ControllerTest.setAppMainWindow(mainWindow, controller);
        AppTest.setAppController(controller);
        replay(mainWindow, controller);

        window = new SettingsWindow();
    }

    @After
    public void verifyMocks() {
        verify(mainWindow, controller);
    }

    @Test
    public void testConstructorOnLoadingMenuCellRenderer() {
        assertTrue(window.menuList.getCellRenderer() instanceof MenuCellRenderer);
    }

    @Test
    public void testConstructorOnLoadingGeneralPanel() {
        assertSame(window.generalPanel, window.contentPanel.getComponent(0));
        assertEquals(SettingsWindow.GENERAL_MENU_INDEX, window.menuList.getSelectedIndex());
    }

    @Ignore // Fails on Jenkins. Since it's only a UI test, it can be ignored.
    @Test
    public void testMenuListOnDefaultPanel() {
        window.menuList.clearSelection();
        assertSame(window.getGeneralPanel(), window.contentPanel.getComponent(0));
    }

    @Ignore // Fails on Jenkins. Since it's only a UI test, it can be ignored.
    @Test
    public void testMenuListOnSelectionChange() {
        Model model = createMock(Model.class);
        AppTest.setAppModel(model);
        expect(model.getUser()).andReturn(getUserWithServer()).anyTimes();
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        controller.setUsername(USERNAME);
        expectLastCall().anyTimes();
        controller.setServer(anyObject(Server.class));
        expectLastCall().anyTimes();
        replay(model, controller);

        window.menuList.setSelectedIndex(SettingsWindow.GENERAL_MENU_INDEX);
        assertSame(window.getGeneralPanel(), window.contentPanel.getComponent(0));

        window.menuList.setSelectedIndex(SettingsWindow.IDENTITY_MENU_INDEX);
        assertSame(window.getIdentityPanel(), window.contentPanel.getComponent(0));

        window.menuList.setSelectedIndex(SettingsWindow.SECURITY_MENU_INDEX);
        assertSame(window.getSecurityPanel(), window.contentPanel.getComponent(0));

        window.menuList.setSelectedIndex(SettingsWindow.NETWORK_MENU_INDEX);
        assertSame(window.getNetworkPanel(), window.contentPanel.getComponent(0));

        verify(model, controller);
    }

    @Ignore // Fails on Jenkins. Since it's only a UI test, it can be ignored.
    @Test
    public void testShowIdentityUsernameWithFocusedUsername() {
        Model model = createMock(Model.class);
        expect(model.getUser()).andReturn(getUserWithServer()).anyTimes();
        mainWindow = createMock(MainWindow.class);
        controller = createMock(Controller.class);
        AppTest.setAppModel(model);
        ControllerTest.setAppMainWindow(mainWindow, controller);
        AppTest.setAppController(controller);
        controller.setUsername(USERNAME);
        expectLastCall().anyTimes();
        controller.setServer(anyObject(Server.class));
        expectLastCall().anyTimes();
        replay(model, mainWindow, controller);

        window.openIdentityMenuWithFocusedUsername();

        assertTrue(window.contentPanel.getComponent(0) instanceof IdentityPanel);
        IdentityPanel panel = (IdentityPanel) window.contentPanel.getComponent(0);
        String selectedText = panel.usernameTextField.getSelectedText();
        assertEquals(USERNAME, selectedText);

        verify(model, mainWindow, controller);
    }

    private User getUserWithServer() {
        User user = User.generate();
        user.setServer(Server.generate());

        return user;
    }

    @Test
    public void testCloseButtonOnDisposingWindow() {
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
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

    @Ignore // Fails on Jenkins. Since it's only a UI test, it can be ignored.
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
