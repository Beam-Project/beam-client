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

import static org.easymock.EasyMock.*;
import org.inchat.client.AppTest;
import org.inchat.client.ClientConfigKey;
import org.inchat.client.Controller;
import org.inchat.common.Config;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class InfoWindowTest {

    private final String CONFIG_FILE = "infoWindow.conf";
    private final String NAME = "infoName";
    private InfoWindow window;
    private Config config;
    private MainWindow mainWindow;
    private Controller controller;

    @Before
    public void setUp() {
        config = createMock(Config.class);
        mainWindow = createMock(MainWindow.class);
        controller = createMock(Controller.class);

        AppTest.setAppConfig(config);
        AppTest.setAppMainWindow(mainWindow);
        AppTest.setAppController(controller);
    }

    @Test
    public void testControllerOnLoadingName() {
        expect(config.getProperty(ClientConfigKey.participantName)).andReturn(NAME);
        replay(config);

        window = new InfoWindow();

        assertEquals(NAME, window.nameTextField.getText());
        verify(config);
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
