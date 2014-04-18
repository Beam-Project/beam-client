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

import static org.easymock.EasyMock.*;
import org.beamproject.client.App;
import org.beamproject.client.AppTest;
import org.beamproject.client.Controller;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SetUpDialogTest {

    private final String USERNAME = "MYUSER";
    private final String SERVER_URL = "http://beamproject.org/:80/my-server/";
    private SetUpDialogTester dialog;
    private Controller controller;

    @Before
    public void setUp() {
        dialog = new SetUpDialogTester();
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
    }

    @Test
    public void testVerifyUserameOnNull() {
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
        dialog.letsBeamButton.doClick();
        assertEquals(App.ERROR_BACKGROUND, dialog.usernameTextField.getBackground());
    }

    @Test
    public void testVerifyUsernameOnValidUser() {
        dialog.usernameTextField.setText(USERNAME);
        dialog.letsBeamButton.doClick();
    }

    @Test
    public void testVerifyServerUrlOnNull() {
        dialog.serverUrlTextField.setText(null);
        expectInvalidServerUrl();
    }

    @Test
    public void testVerifyServerUrlOnEmptyString() {
        dialog.serverUrlTextField.setText("");
        expectInvalidServerUrl();
    }

    @Test
    public void testVerifyServerUrlOnSpaces() {
        dialog.serverUrlTextField.setText("   ");
        expectInvalidServerUrl();
    }

    private void expectInvalidServerUrl() {
        dialog.letsBeamButton.doClick();
        assertEquals(App.ERROR_BACKGROUND, dialog.serverUrlTextField.getBackground());
    }

    @Test
    public void testVerifyServerUrlOnValidUrl() {
        dialog.serverUrlTextField.setText(SERVER_URL);
        dialog.letsBeamButton.doClick();
    }

    @Test
    public void testSkipForNowCheckBox() {
        replay(controller);

        assertTrue(dialog.serverUrlTextField.isEditable());
        dialog.serverUrlTextField.setText("invalid url");
        dialog.skipForNowCheckBox.doClick();
        assertFalse(dialog.serverUrlTextField.isEditable());
        assertTrue(dialog.serverUrlTextField.getText().isEmpty());

        verify(controller);
    }

    @Test
    public void testLetsBeamButtonOnInvocatingWithInvalidUsername() {
        controller.setServerUrl(SERVER_URL);
        expectLastCall();
        replay(controller);

        dialog.serverUrlTextField.setText(SERVER_URL);
        dialog.letsBeamButton.doClick();
        assertFalse(dialog.isDisposed);

        verify(controller);
    }

    @Test
    public void testLetsBeamButtonOnInvocatingWithInvaidUrl() {
        controller.setUsername(USERNAME);
        expectLastCall();
        replay(controller);

        dialog.usernameTextField.setText(USERNAME);
        dialog.serverUrlTextField.setText("invalid url");
        dialog.letsBeamButton.doClick();
        assertFalse(dialog.isDisposed);

        verify(controller);
    }

    @Test
    public void testLetsBeamButtonOnInvocatingWithSkippedUrl() {
        MainWindow mainWindow = createMock(MainWindow.class);
        AppTest.setAppMainWindow(mainWindow);
        mainWindow.requestFocus();
        expectLastCall();
        controller.setUsername(USERNAME);
        expectLastCall();
        replay(controller, mainWindow);

        dialog.usernameTextField.setText(USERNAME);
        dialog.serverUrlTextField.setText("invalid url");
        dialog.skipForNowCheckBox.setSelected(true);
        dialog.letsBeamButton.doClick();
        assertTrue(dialog.isDisposed);

        verify(controller, mainWindow);
    }

    @Test
    public void testLetsBeamButtonOnControllerInvocation() {
        MainWindow mainWindow = createMock(MainWindow.class);
        AppTest.setAppMainWindow(mainWindow);
        mainWindow.requestFocus();
        expectLastCall();
        String nameWithSpaces = USERNAME + "   ";
        controller.setUsername(USERNAME);
        expectLastCall().once();
        controller.setServerUrl(SERVER_URL);
        expectLastCall().once();
        replay(controller, mainWindow);

        dialog.usernameTextField.setText(nameWithSpaces);
        dialog.serverUrlTextField.setText(SERVER_URL);
        dialog.letsBeamButton.doClick();
        assertTrue(dialog.isDisposed);

        verify(controller, mainWindow);
    }

    private class SetUpDialogTester extends SetUpDialog {

        private static final long serialVersionUID = 1L;
        private boolean isDisposed = false;

        @Override
        public void dispose() {
            isDisposed = true;
        }
    }

}
