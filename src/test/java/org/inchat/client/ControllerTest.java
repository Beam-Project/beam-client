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

import java.io.File;
import static org.easymock.EasyMock.*;
import org.inchat.client.storage.Storage;
import org.inchat.client.ui.MainWindow;
import org.inchat.client.ui.MainWindowTest;
import org.inchat.common.Config;
import org.inchat.common.Contact;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class ControllerTest {

    private final String CONTACTS_STORAGE_FILE = "contacts.storage";
    private Controller controller;
    private Model model;
    private Contact contact;
    private File contactsStorageFile;

    @Before
    public void setUp() {
        App.CONTACTS_STORAGE_FILE = CONTACTS_STORAGE_FILE;
        contactsStorageFile = new File(CONTACTS_STORAGE_FILE);
        controller = new Controller();
        model = createMock(Model.class);
        contact = createMock(Contact.class);
    }

    @After
    public void cleanUp() {
        contactsStorageFile.delete();
    }

    @Test
    public void testConstructorOnInstatiation() {
        assertTrue(controller.conversationWindows.isEmpty());
    }

    @Test
    public void testChangeUsername() {
        expect(model.getContactList()).andReturn(new ContactList());
        App.model = model;
        replay(contact, model);
        
        String username = "Timmeeee";
        String filename = "username.conf";
        File configFile = new File(filename);
        configFile.delete();

        Config.createDefaultConfig(filename);
        Config.loadConfig(filename);
        MainWindow mainWindow = new MainWindow();
        AppTest.setAppMainWindow(mainWindow);
        controller.changeUsername(username);

        testChangeUsernameOnWritingConfigProperty(username);
        testChangeUsernameOnUpdatingGui(username);

        configFile.delete();

        verify(contact, model);
    }

    private void testChangeUsernameOnWritingConfigProperty(String username) {
        assertEquals(username, Config.getProperty(Config.Key.participantName));
    }

    private void testChangeUsernameOnUpdatingGui(String username) {
        assertEquals(username, MainWindowTest.getUsernameButton().getText());
    }

    @Test
    public void testAddContactOnInvocingModelOnNull() {
        model.addContact(anyObject(Contact.class));
        expectLastCall().once();
        expect(model.getContactListStorage()).andReturn(new Storage<ContactList>(CONTACTS_STORAGE_FILE)).anyTimes();
        expect(model.getContactList()).andReturn(new ContactList());
        replay(model);

        AppTest.setAppMdoel(model);
        controller.addContact(null); // should just invoke model

        verify(model);
    }

    @Test
    public void testAddContact() {
        model.addContact(anyObject(Contact.class));
        expectLastCall().once();
        expect(model.getContactListStorage()).andReturn(new Storage<ContactList>(CONTACTS_STORAGE_FILE)).anyTimes();
        expect(model.getContactList()).andReturn(new ContactList());
        replay(model, contact);

        assertFalse(contactsStorageFile.exists());
        AppTest.setAppMdoel(model);
        controller.addContact(contact);
        assertTrue(contactsStorageFile.exists());

        verify(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenConversationWindowOnNull() {
        controller.openConversationWindow(null);
    }

    @Test
    public void testOpenConversationWindow() {
        expect(contact.getName()).andReturn("myname");
        replay(contact);

        assertTrue(controller.conversationWindows.isEmpty());
        controller.openConversationWindow(contact);
        assertEquals(1, controller.conversationWindows.size());
        assertTrue(controller.conversationWindows.get(0).isVisible());
        assertSame(contact, controller.conversationWindows.get(0).getContact());

        verify(contact);
    }

    @Test
    public void testLoadContactStorageOnNewFile() {
        App.model = new Model();
        assertNull(App.model.contactListStorage);
        assertFalse(contactsStorageFile.exists());

        controller.readContactListStorage();
        assertNotNull(App.model.contactListStorage);
    }

    @Test(expected = IllegalStateException.class)
    public void testWriteContactStorageOnNotExistingStorage() {
        App.model = new Model();
        controller.writeContactListStorage();
    }

    @Test
    public void testWriteContactStorage() {
        assertFalse(contactsStorageFile.exists());
        testLoadContactStorageOnNewFile();

        controller.writeContactListStorage();
        assertTrue(contactsStorageFile.exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnNulls() {
        controller.sendMessage(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnNullTarget() {
        controller.sendMessage(null, "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnEmptyMessage() {
        controller.sendMessage(contact, "");
    }

}
