/*
 * Copyright (C) 2013, 2014 beamproject.org
 *
 * This file is part of beam-user.
 *
 * beam-user is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * beam-user is distributed in the hope that it will be useful,
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
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AddContactDialogTest {

    private AddContactDialog dialog;
    private Controller controller;
    private User user;
    private String userAddress;

    @Before
    public void setUp() {
        dialog = new AddContactDialog();
        controller = createMock(Controller.class);

        user = User.generate();
        user.setServer(Server.generate());
        userAddress = user.getAddress();
    }

    @Test
    public void testVerifyUrlTextAreaOnEmptyString() {
        dialog.verifyUrlTextArea();
        assertEquals(App.ERROR_BACKGROUND, dialog.urlTextArea.getBackground());
    }

    @Test
    public void testVerifyUrlTextAreaOnShortUrl() {
        dialog.urlTextArea.setText("beam:asdf");
        dialog.verifyUrlTextArea();
        assertEquals(App.ERROR_BACKGROUND, dialog.urlTextArea.getBackground());
    }

    @Test
    public void testAddButtonOnErrorInputs() {
        replay(controller);

        AppTest.setAppController(controller);
        dialog.addButton.doClick();

        verify(controller);
    }

    @Test
    public void testAddButtonOnErrorUrlTextArea() {
        replay(controller);

        AppTest.setAppController(controller);
        dialog.addButton.doClick();

        verify(controller);
    }

    @Test
    public void testAddButtonOnTrimmingWithWrongUrl() {
        replay(controller);

        AppTest.setAppController(controller);
        String untrimmed = "         text to trim    \n         ";
        String trimmed = "text to trim";
        dialog.urlTextArea.setText(untrimmed);
        dialog.addButton.doClick();
        assertEquals(trimmed, dialog.urlTextArea.getText());

        verify(controller);
    }

    @Test
    public void testAddButtonOnTrimmingWithCorrectUrl() {
        controller.addContact(anyObject(User.class));
        expectLastCall().once();
        replay(controller);

        AppTest.setAppController(controller);
        String untrimmed = "    " + userAddress + "    ";
        dialog.urlTextArea.setText(untrimmed);
        dialog.addButton.doClick();

        verify(controller);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddButton() {
        controller.addContact(anyObject(User.class));
        expectLastCall().andDelegateTo(new Controller() {
            @Override
            public void addContact(User user) {
                assertEquals(User.DEFAULT_USERNAME, user.getUsername());
            }
        });
        replay(controller);

        AppTest.setAppController(controller);
        dialog.urlTextArea.setText(user.getAddress());
        dialog.addButton.doClick();

        verify(controller);
    }
}
