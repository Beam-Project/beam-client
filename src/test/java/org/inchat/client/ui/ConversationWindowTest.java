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
import org.inchat.common.Contact;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ConversationWindowTest {

    private ConversationWindow window;
    private Contact contact;
    private final String NAME = "contactName";

    @Before
    public void setUp() {
        window = new ConversationWindow();
        contact = createMock(Contact.class);
    }

    @Test
    public void testGetContacts() {
        window.contact = null;
        assertNull(window.getContact());

        window.contact = contact;
        assertSame(contact, window.getContact());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetContactsOnNull() {
        window.setContact(null);
    }

    @Test
    public void testSetContactsOnAssignment() {
        expect(contact.getName()).andReturn(NAME);
        replay(contact);

        window.setContact(contact);
        assertEquals(contact, window.contact);
    }

    @Test
    public void testSetContactUpdatingNames() {
        expect(contact.getName()).andReturn(NAME);
        replay(contact);

        window.setContact(contact);

        assertEquals(NAME + " and you", window.namesLabel.getText());
        verify(contact);
    }

}
