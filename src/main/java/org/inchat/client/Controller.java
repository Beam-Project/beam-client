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

import java.util.ArrayList;
import java.util.List;
import org.inchat.client.storage.Storage;
import org.inchat.client.ui.ConversationWindow;
import org.inchat.client.ui.Frames;
import org.inchat.common.Config;
import org.inchat.common.Contact;
import org.inchat.common.util.Exceptions;

/**
 * The controller manages the activities between the components, typically
 * triggered by the user interface.
 */
public class Controller {

    List<ConversationWindow> conversationWindows = new ArrayList<>();

    public void changeName(String name) {
        Config.setProperty(Config.Key.participantName, name);
        App.getMainWindow().setUsername(name);
    }

    public void addContact(Contact contact) {
        App.getModel().addContact(contact);
        writeContactListStorage();
    }

    public void openConversationWindow(Contact contact) {
        ConversationWindow window = new ConversationWindow();
        Frames.setIcons(window);
        window.setContact(contact);
        conversationWindows.add(window);
        window.setVisible(true);
    }

    public void readContactListStorage() {
        Storage<ContactList> storage = new Storage<>(App.CONTACTS_STORAGE_FILE);
        App.getModel().setContactListStorage(storage);

        if (!storage.isExisting()) {
            return;
        }

        ContactList restoredContactList = storage.restore(ContactList.class);
        ContactList existingContactList = App.getModel().getContactList();

        for (int i = 0; i < restoredContactList.getSize(); i++) {
            existingContactList.addElement(restoredContactList.elementAt(i));
        }
    }

    public void writeContactListStorage() {
        if (App.getModel().getContactListStorage() == null) {
            throw new IllegalStateException("The contact storage has to be loaded first.");
        }
        
        ContactList list = App.getModel().getContactList();
        App.getModel().getContactListStorage().store(list);
    }
    
    public void sendMessage(Contact target, String message) {
        Exceptions.verifyArgumentNotNull(target);
        Exceptions.verifyArgumentNotEmpty(message);
        
    }

}
