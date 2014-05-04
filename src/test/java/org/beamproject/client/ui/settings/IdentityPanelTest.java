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

import java.beans.PropertyChangeListener;
import org.beamproject.client.App;
import static org.easymock.EasyMock.*;
import org.beamproject.client.AppTest;
import org.beamproject.client.ConfigTest;
import org.beamproject.client.Controller;
import org.beamproject.client.Model;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class IdentityPanelTest {

    private final String USERNAME = "Mr/Mrs Garrison";
    private IdentityPanel panel;
    private Controller controller;
    private Model model;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        App.getConfig().setProperty("username", USERNAME);
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        model = new Model();
        model.setUser(User.generate());
        model.getUser().setServer(Server.generate());
        App.getConfig().setProperty("serverAddress", model.getUser().getServer().getAddress());
        AppTest.setAppModel(model);

        panel = new IdentityPanel();
    }

    @Test
    public void testLoadingFieldsWithServerAndUser() {
        assertEquals(USERNAME, panel.usernameTextField.getText());
        assertTrue(panel.serverAddressValueLabel.getText().startsWith("beam:"));
    }

    @Test
    public void testUsernameTextFieldPropertyChangeOnValueChange() {
        PropertyChangeListener listener = panel.usernameTextField.getPropertyChangeListeners()[2];
        String changedUsername = "Mrs Garrison";
        controller.setUsername(changedUsername);
        expectLastCall();
        replay(controller);

        listener.propertyChange(null);
        panel.usernameTextField.setText(USERNAME);
        listener.propertyChange(null);
        panel.usernameTextField.setText(changedUsername);
        listener.propertyChange(null);

        verify(controller);
    }

    @Test
    public void testUsernameTextFieldPropertyChange() {
        PropertyChangeListener listener = panel.usernameTextField.getPropertyChangeListeners()[2];
        String validUsername1 = "Mr Garrison";
        String validUsername2 = "Mrs Garrison";

        controller.setUsername(validUsername1);
        expectLastCall();
        controller.setUsername(validUsername2);
        expectLastCall();
        replay(controller);

        panel.usernameTextField.setText("   ");
        listener.propertyChange(null);

        panel.usernameTextField.setText("");
        listener.propertyChange(null);

        String changedUsername = "Mr Garrison";
        panel.usernameTextField.setText(changedUsername);
        listener.propertyChange(null);

        String changedUsernameToTrim = "  Mrs Garrison   ";
        panel.usernameTextField.setText(changedUsernameToTrim);
        listener.propertyChange(null);

        verify(controller);
    }

}
