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
import java.io.File;
import static org.easymock.EasyMock.*;
import org.inchat.client.AppTest;
import org.inchat.client.Controller;
import org.inchat.common.Config;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class IdentityPanelTest {

    private final String CONFIG_FILE = "identity.conf";
    private final String NAME = "Mr/Mrs Garrison";
    private IdentityPanel panel;
    private Controller controller;

    @Before
    public void setUp() {
        Config.createDefaultConfig(CONFIG_FILE);
        Config.loadConfig(CONFIG_FILE);
        Config.setProperty(Config.Key.participantName, NAME);

        controller = createMock(Controller.class);
        AppTest.setAppController(controller);

        panel = new IdentityPanel();
    }

    @After
    public void cleanUp() {
        File config = new File(CONFIG_FILE);
        config.delete();
    }

    @Test
    public void testConstructorOnLoadingName() {
        assertEquals(NAME, panel.nameTextField.getText());
    }

    @Test
    public void testIsValidName() {
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

        controller.changeUsername(validName1);
        expectLastCall();
        controller.changeUsername(validName2);
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

}
