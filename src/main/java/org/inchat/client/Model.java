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

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import org.inchat.common.Contact;
import org.inchat.common.util.Exceptions;

public class Model {

    DefaultListModel<Contact> contactList = new DefaultListModel<>();

    public void addContact(Contact contact) {
        Exceptions.verifyArgumentNotNull(contact);

        contactList.addElement(contact);
    }
    
    public ListModel<Contact> getContactList() {
        return contactList;
    }
}
