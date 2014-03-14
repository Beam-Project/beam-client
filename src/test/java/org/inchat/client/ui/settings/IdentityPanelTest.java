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

import java.beans.PropertyChangeListener;
import static org.easymock.EasyMock.*;
import org.inchat.client.AppTest;
import org.inchat.client.ClientConfigKey;
import org.inchat.client.Controller;
import org.inchat.common.Config;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class IdentityPanelTest {

    private final String NAME = "Mr/Mrs Garrison";
    private final String SERVER_URL = "http://my-server.inchat.org:1234";
    private IdentityPanel panel;
    private Config config;
    private Controller controller;

    @Before
    public void setUp() {
        config = createMock(Config.class);
        controller = createMock(Controller.class);
        AppTest.setAppConfig(config);
        AppTest.setAppController(controller);

        expect(config.getProperty(ClientConfigKey.participantName)).andReturn(NAME);
        expect(config.getProperty(ClientConfigKey.serverUrl)).andReturn(SERVER_URL);
        replay(config);

        panel = new IdentityPanel();
    }

    @After
    public void verifyMocks() {
        verify(config);
    }

    @Test
    public void testConstructorOnLoadingNameAndServerUrl() {
        assertEquals(NAME, panel.nameTextField.getText());
        assertEquals(SERVER_URL, panel.serverUrlTextField.getText());
    }

    @Test
    public void testIsNameValid() {
        assertFalse(panel.isNameValid(null));
        assertFalse(panel.isNameValid(""));
        assertTrue(panel.isNameValid("a"));
        assertTrue(panel.isNameValid("a sdlfkj sdlfja d"));
        assertFalse(panel.isNameValid(" a;klf spfa9difasd;flk23lkjsdf "));
        assertTrue(panel.isNameValid("@sldkfj!#Ralskgjsal'"));
    }

    @Test
    public void testNameTextFieldPropertyChange() {
        PropertyChangeListener listener = panel.nameTextField.getPropertyChangeListeners()[2];
        String validName1 = "Mr Garrison";
        String validName2 = "Mrs Garrison";

        controller.changeName(validName1);
        expectLastCall();
        controller.changeName(validName2);
        expectLastCall();
        replay(controller);

        panel.nameTextField.setText("   ");
        listener.propertyChange(null);

        panel.nameTextField.setText("");
        listener.propertyChange(null);

        String changedName = "Mr Garrison";
        panel.nameTextField.setText(changedName);
        listener.propertyChange(null);

        String changedNameToTrim = "  Mrs Garrison   ";
        panel.nameTextField.setText(changedNameToTrim);
        listener.propertyChange(null);

        verify(controller);
    }

    @Test
    public void testIsServerUrlValid() {
        assertFalse(panel.isServerUrlValid(null));
        assertFalse(panel.isServerUrlValid(""));
        assertFalse(panel.isServerUrlValid("z "));
        assertTrue(panel.isServerUrlValid("http://org"));
        assertTrue(panel.isServerUrlValid(SERVER_URL));
        assertTrue(panel.isServerUrlValid("    \t   " + SERVER_URL + "    \t   "));
    }

    @Test
    public void testServerUrlTextFieldPropertyChange() {
        PropertyChangeListener listener = panel.serverUrlTextField.getPropertyChangeListeners()[2];
        String validUrl1 = "http://inchtat.org/myserver/is/very/cool";
        String validUrl2 = "http://192.168.0.123";

        controller.setServerUrl(validUrl1);
        expectLastCall();
        controller.setServerUrl(validUrl2);
        expectLastCall();
        replay(controller);

        panel.serverUrlTextField.setText("   ");
        listener.propertyChange(null);

        panel.serverUrlTextField.setText("");
        listener.propertyChange(null);

        panel.serverUrlTextField.setText(validUrl1);
        listener.propertyChange(null);

        panel.serverUrlTextField.setText("    " + validUrl2 + "     ");
        listener.propertyChange(null);

        verify(controller);
    }

}
