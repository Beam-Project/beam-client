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
package org.inchat.client;

import static org.easymock.EasyMock.*;
import org.inchat.common.Contact;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ModelTest {

    private Model model;
    Contact contact;

    @Before
    public void setUp() {
        model = new Model();
        contact = createMock(Contact.class);
    }

    @Test
    public void testModelOnInitialization() {
        assertEquals(0, model.contactList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddContactOnNull() {
        model.addContact(null);
    }

    @Test
    public void testAddContact() {
        replay(contact);
        assertEquals(0, model.contactList.size());
        model.addContact(contact);
        assertEquals(1, model.contactList.size());
    }

    @Test
    public void testGetContactList() {
        assertEquals(model.contactList, model.getContactList());
    }

}
