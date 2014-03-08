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

import java.io.File;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.inchat.client.AppTest;
import org.inchat.client.Controller;
import org.inchat.common.Config;
import org.inchat.common.crypto.KeyPairStore;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class InfoWindowTest {

    private final String CONFIG_FILE = "infoWindow.conf";
    private final String NAME = "infoName";
    private InfoWindow window;
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
        Config.loadOrCreateParticipant();

        window = new InfoWindow();
    }

    @After
    public void cleanUp() {
        new File(CONFIG_FILE).delete();
        
        new File("keypair" + KeyPairStore.PRIVATE_KEY_FILE_EXTENSION).delete();
        new File("keypair" + KeyPairStore.PUBILC_KEY_FILE_EXTENSION).delete();
        new File("keypair" + KeyPairStore.SALT_FILE_EXTENSION).delete();
    }

    @Test
    public void testControllerOnLoadingName() {
        Config.setProperty(Config.Key.participantName, NAME);

        window = new InfoWindow();
        assertEquals(NAME, window.nameTextField.getText());
    }


    @Test
    public void testCloseButtonOnDisposingWindow() {
        controller.closeInfoWindow();
        expectLastCall();
        replay(controller);

        window.closeButton.doClick();

        verify(controller);
    }

}
