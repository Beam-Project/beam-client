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

import org.inchat.client.storage.Storage;
import org.inchat.common.Contact;
import org.inchat.common.Participant;
import org.inchat.common.util.Exceptions;

public class Model {

    Participant participant;
    Storage<ContactList> contactListStorage;
    ContactList contactList = new ContactList();

    /**
     * Sets the given {@link Participant} to this {@link Model}.
     *
     * @param participant This may not be null.
     * @throws IllegalArgumentException If the argument is null.
     */
    public void setParticipant(Participant participant) {
        Exceptions.verifyArgumentNotNull(participant);

        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void addContact(Contact contact) {
        Exceptions.verifyArgumentNotNull(contact);

        contactList.addElement(contact);
    }

    public ContactList getContactList() {
        return contactList;
    }

    /**
     * Sets the given storage to this {@link Model}.
     *
     * @param storage This may not be null.
     * @throws IllegalArgumentException If the argument is null.
     */
    public void setContactListStorage(Storage<ContactList> storage) {
        Exceptions.verifyArgumentNotNull(storage);

        this.contactListStorage = storage;
    }

    public Storage<ContactList> getContactListStorage() {
        return contactListStorage;
    }
}
