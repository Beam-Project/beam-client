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
import org.easymock.IAnswer;
import org.inchat.client.App;
import org.inchat.client.AppTest;
import org.inchat.client.Controller;
import org.inchat.common.Contact;
import org.inchat.common.Participant;
import org.inchat.common.crypto.EccKeyPairGenerator;
import org.inchat.common.transfer.UrlAssembler;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AddContactDialogTest {

    private AddContactDialog dialog;
    private Controller controller;

    @Before
    public void setUp() {
        dialog = new AddContactDialog();
        controller = createMock(Controller.class);
    }

    @Test
    public void testVerifyUrlTextAreaOnEmptyString() {
        dialog.verifyUrlTextArea();
        assertEquals(App.ERROR_BACKGROUND, dialog.urlTextArea.getBackground());
    }

    @Test
    public void testVerifyUrlTextAreaOnShortUrl() {
        dialog.urlTextArea.setText("inchat:asdf");
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
        Participant participant = new Participant(EccKeyPairGenerator.generate());
        String url = UrlAssembler.toUrlByServerAndClient(participant, participant, "myname");

        controller.addContact(anyObject(Contact.class));
        expectLastCall().once();
        replay(controller);

        AppTest.setAppController(controller);
        String untrimmed = "    " + url + "    ";
        dialog.urlTextArea.setText(untrimmed);
        dialog.addButton.doClick();

        verify(controller);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddButton() {
        Participant participant = new Participant(EccKeyPairGenerator.generate());
        controller.addContact(anyObject(Contact.class));
        expectLastCall().andAnswer(new IAnswer() {
            @Override
            public Object answer() {
                Contact argument = (Contact) getCurrentArguments()[0];
                assertEquals("name", argument.getName());
                return null;
            }
        });
        replay(controller);

        AppTest.setAppController(controller);
        dialog.urlTextArea.setText(UrlAssembler.toUrlByServerAndClient(participant, participant, "name"));
        dialog.addButton.doClick();

        verify(controller);
    }

}
