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

import org.aeonbits.owner.ConfigFactory;
import org.beamproject.client.App;
import static org.easymock.EasyMock.*;
import org.beamproject.client.AppTest;
import org.beamproject.client.Config;
import org.beamproject.client.ConfigTest;
import org.beamproject.client.Controller;
import org.beamproject.client.Model;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class InfoWindowTest {

    private final String NAME = "infoName";
    private InfoWindow window;
    private MainWindow mainWindow;
    private Controller controller;
    private Model model;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        mainWindow = createMock(MainWindow.class);
        controller = createMock(Controller.class);
        model = createMock(Model.class);

        AppTest.setAppMainWindow(mainWindow);
        AppTest.setAppController(controller);
        AppTest.setAppModel(model);
    }

    @Test
    public void testConstructorOnLoadingName() {
        App.getConfig().setProperty("participantName", NAME);
        window = new InfoWindow();
        assertEquals(NAME, window.nameTextField.getText());
    }

    @Test
    public void testConstructorOnLoadingUrl() {
        String url = "beam:server.user?name=mrbeam";
        expect(model.getParticipantUrl()).andReturn(url);
        replay(model);

        window = new InfoWindow();
        assertEquals(url, window.urlTextField.getText());

        verify(model);
    }

    @Test
    public void testCloseButtonOnDisposingWindow() {
        controller.closeInfoWindow();
        expectLastCall();
        replay(controller);

        window = new InfoWindow();
        window.closeButton.doClick();

        verify(controller);
    }

}
