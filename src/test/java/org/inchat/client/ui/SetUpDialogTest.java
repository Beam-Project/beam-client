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
import org.inchat.client.App;
import org.inchat.client.AppTest;
import org.inchat.client.Controller;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

public class SetUpDialogTest {

    private final String USERNAME = "MYUSER";
    private SetUpDialog dialog;
    private Controller controller;

    @Before
    public void setUp() {
        dialog = new SetUpDialog();
        controller = createMock(Controller.class);
    }

    @Test
    public void testVerifyUsernameOnNull() {
        dialog.usernameTextField.setText(null);
        expectInvalidUsername();
    }

    @Test
    public void testVerifyUsernameOnEmptyString() {
        dialog.usernameTextField.setText("");
        expectInvalidUsername();
    }

    @Test
    public void testVerifyUsernameOnSpaces() {
        dialog.usernameTextField.setText("   ");
        expectInvalidUsername();
    }

    private void expectInvalidUsername() {
        dialog.verifyUsername();
        assertEquals(App.ERROR_BACKGROUND, dialog.usernameTextField.getBackground());
        assertFalse(dialog.isUsernameValid);

    }

    @Ignore
    @Test
    public void testVerifyUsernameOnValidUser() {
        dialog.usernameTextField.setText(USERNAME);
        dialog.verifyUsername();
        assertFalse(App.DEFAULT_BACKGROUND.equals(dialog.usernameTextField.getBackground()));
        assertTrue(dialog.isUsernameValid);
    }

    @Test
    public void testDoneButtonOnControllerNotInvocating() {
        replay(controller);
        AppTest.setAppController(controller);

        dialog.doneButton.doClick();

        verify(controller);
    }

    @Test
    public void testDoneButtonOnControllerInvocation() {
        String usernameWithSpaces = USERNAME + "   ";
        controller.changeUsername(USERNAME);
        expectLastCall().once();
        replay(controller);
        AppTest.setAppController(controller);

        dialog.usernameTextField.setText(usernameWithSpaces);
        dialog.doneButton.doClick();

        verify(controller);
    }

}
