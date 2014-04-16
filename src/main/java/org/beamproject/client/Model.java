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
package org.beamproject.client;

import org.beamproject.client.storage.Storage;
import org.beamproject.common.Contact;
import org.beamproject.common.Participant;
import org.beamproject.common.util.Exceptions;

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
        Exceptions.verifyArgumentsNotNull(participant);

        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void addContact(Contact contact) {
        Exceptions.verifyArgumentsNotNull(contact);

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
        Exceptions.verifyArgumentsNotNull(storage);

        this.contactListStorage = storage;
    }

    public Storage<ContactList> getContactListStorage() {
        return contactListStorage;
    }
}
