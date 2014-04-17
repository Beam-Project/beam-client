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

    private final String NAME = "MYUSER";
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
    public void testVerifyNameOnNull() {
        dialog.nameTextField.setText(null);
        expectInvalidName();
    }

    @Test
    public void testVerifyNameOnEmptyString() {
        dialog.nameTextField.setText("");
        expectInvalidName();
    }

    @Test
    public void testVerifyNameOnSpaces() {
        dialog.nameTextField.setText("   ");
        expectInvalidName();
    }

    private void expectInvalidName() {
        dialog.isNameValid();
        assertEquals(App.ERROR_BACKGROUND, dialog.nameTextField.getBackground());

    }

    @Test
    public void testVerifyNameOnValidUser() {
        dialog.nameTextField.setText(NAME);
        dialog.isNameValid();
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
        dialog.isServerUrlValid();
        assertEquals(App.ERROR_BACKGROUND, dialog.serverUrlTextField.getBackground());
    }

    @Test
    public void testVerifyServerUrlOnValidUrl() {
        dialog.serverUrlTextField.setText(SERVER_URL);
        dialog.isServerUrlValid();
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
    public void testLetsBeamButtonOnInvocatingWithInvalidName() {
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
        controller.changeName(NAME);
        expectLastCall();
        replay(controller);

        dialog.nameTextField.setText(NAME);
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
        controller.changeName(NAME);
        expectLastCall();
        replay(controller, mainWindow);

        dialog.nameTextField.setText(NAME);
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
        String nameWithSpaces = NAME + "   ";
        controller.changeName(NAME);
        expectLastCall().once();
        controller.setServerUrl(SERVER_URL);
        expectLastCall().once();
        replay(controller, mainWindow);

        dialog.nameTextField.setText(nameWithSpaces);
        dialog.serverUrlTextField.setText(SERVER_URL);
        dialog.letsBeamButton.doClick();
        assertTrue(dialog.isDisposed);

        verify(controller, mainWindow);
    }

    private class SetUpDialogTester extends SetUpDialog {

        public SetUpDialogTester() {
        }
        private static final long serialVersionUID = 1L;
        private boolean isDisposed = false;

        @Override
        public void dispose() {
            isDisposed = true;
        }
    }

}
