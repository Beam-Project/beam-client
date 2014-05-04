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

import org.beamproject.client.App;
import static org.easymock.EasyMock.*;
import org.beamproject.client.AppTest;
import org.beamproject.client.ConfigTest;
import org.beamproject.client.Controller;
import org.beamproject.client.Model;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class InfoWindowTest {

    private final String USERNAME = "infoName";
    private InfoWindow window;
    private Controller controller;
    private Model model;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        controller = new Controller();
        model = new Model();
        model.setUser(User.generate());
        model.getUser().setServer(Server.generate());

        AppTest.setAppController(controller);
        AppTest.setAppModel(model);
    }

    @Test
    public void testConstructorOnLoadingUsername() {
        App.getConfig().setProperty("username", USERNAME);
        window = new InfoWindow();
        assertEquals(USERNAME, window.usernameLabel.getText());
    }

    @Test
    public void testConstructorOnLoadingAddress() {
        window = new InfoWindow();
        assertEquals(model.getUser().getAddress(), window.addressLabel.getText());
    }

    @Test
    public void testConstructorOnLoadingAddressWithoutServer() {
        model.setUser(User.generate());
        window = new InfoWindow();
        assertEquals(InfoWindow.USER_ADDRESS_PLACEHOLDER, window.addressLabel.getText());
        assertFalse(window.copyAddressButton.isVisible());
    }

    @Test
    public void testConstructorOnLoadingQrCode() {
        window = new InfoWindow();
        assertTrue(window.qrCodeLabel.getText().isEmpty());
        assertEquals(InfoWindow.QR_CODE_DIMENSION_IN_PX, window.qrCodeLabel.getIcon().getIconHeight());
        assertEquals(InfoWindow.QR_CODE_DIMENSION_IN_PX, window.qrCodeLabel.getIcon().getIconWidth());
    }

    @Test
    public void testConstructorOnLoadingQrCodeWhenMissingAddress() {
        model.setUser(User.generate());

        window = new InfoWindow();
        assertFalse(window.qrCodePanel.isVisible());
    }

    @Test
    public void testCloseButtonOnDisposingWindow() {
        window = new InfoWindow();

        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        controller.closeInfoWindow();
        expectLastCall();
        replay(controller);

        window.closeButton.doClick();

        verify(controller);
    }
}
