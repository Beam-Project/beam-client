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
import javax.swing.JPanel;
import static org.easymock.EasyMock.*;
import org.inchat.client.storage.Storage;
import org.inchat.client.ui.MainWindow;
import org.inchat.client.ui.MainWindowTest;
import org.inchat.client.ui.settings.GeneralPanel;
import org.inchat.client.ui.settings.IdentityPanel;
import org.inchat.client.ui.settings.SettingsWindow;
import org.inchat.client.ui.settings.SettingsWindowTest;
import org.inchat.common.Config;
import org.inchat.common.Contact;
import org.inchat.common.Participant;
import org.inchat.common.crypto.EccKeyPairGenerator;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class ControllerTest {

    private final String CONFIG_FILE = "controller.conf";
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

        App.controller = controller;
    }

    @After
    public void cleanUp() {
        new File(CONFIG_FILE).delete();
        contactsStorageFile.delete();
        disposeSettingsWindow();
    }

    /**
     * Dispose the settings window manually if it's still available. Otherwise,
     * tests could fail because other tests would expect settingsWindow to be
     * newly instantiated.
     */
    private void disposeSettingsWindow() {
        if (controller.settingsWindow != null) {
            controller.settingsWindow.dispose();
        }
    }

    @Test
    public void testConstructorOnInstatiation() {
        assertTrue(controller.conversationWindows.isEmpty());
    }

    @Test
    public void testChangeName() {
        expect(model.getContactList()).andReturn(new ContactList());
        App.model = model;
        replay(contact, model);

        String name = "Timmeeee";

        Config.createDefaultConfig(CONFIG_FILE);
        Config.loadConfig(CONFIG_FILE);
        MainWindow mainWindow = new MainWindow();
        AppTest.setAppMainWindow(mainWindow);
        controller.changeName(name);

        testChangeNameOnWritingConfigProperty(name);
        testChangeNameOnUpdatingGui(name);

        verify(contact, model);
    }

    private void testChangeNameOnWritingConfigProperty(String name) {
        assertEquals(name, Config.getProperty(Config.Key.participantName));
    }

    private void testChangeNameOnUpdatingGui(String name) {
        assertEquals(name, MainWindowTest.getNameButton().getText());
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
    public void testSetServerUrl() {
        expect(model.getContactList()).andReturn(new ContactList());
        App.model = model;
        replay(contact, model);

        String serverUrl = "https://www.inchat.org:443/myserver/?name=user&password=verySecure";

        Config.createDefaultConfig(CONFIG_FILE);
        Config.loadConfig(CONFIG_FILE);
        MainWindow mainWindow = new MainWindow();
        AppTest.setAppMainWindow(mainWindow);

        controller.setServerUrl(serverUrl);
        assertEquals(serverUrl, Config.getProperty(Config.Key.serverUrl));

        verify(contact, model);
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

    @Test
    public void testSendMessage() {
        Participant server = new Participant(EccKeyPairGenerator.generate());
        Participant client = new Participant(EccKeyPairGenerator.generate());
        Contact contact = new Contact(server, client, "john");
        controller.sendMessage(contact, "hello");
    }

    @Test
    public void testShowSettingsWindow() {
        assertNull(controller.settingsWindow);

        controller.showSettingsWindow();

        JPanel activeContentPanel = SettingsWindowTest.getContentPanel(controller.settingsWindow);
        assertTrue(activeContentPanel.getComponent(0) instanceof GeneralPanel);
        assertTrue(controller.settingsWindow.isVisible());
    }

    @Test
    public void testShowSettingsWindowOnReusingExistingWindow() {
        SettingsWindow window = new SettingsWindow();
        controller.settingsWindow = window;
        assertFalse(window.isVisible());

        controller.showSettingsWindow();

        assertSame(window, controller.settingsWindow);
        assertTrue(controller.settingsWindow.isVisible());
    }

    @Test
    public void testShowIdentityNameInSettingsWindow() {
        assertNull(controller.settingsWindow);

        controller.showNameInSettingsWindow();

        JPanel activeContentPanel = SettingsWindowTest.getContentPanel(controller.settingsWindow);
        assertTrue(activeContentPanel.getComponent(0) instanceof IdentityPanel);
        assertTrue(controller.settingsWindow.isVisible());
    }

    @Test
    public void testShowIdentityNameInSettingsWindowOnReusingExistingWindow() {
        SettingsWindow window = new SettingsWindow();
        controller.settingsWindow = window;
        assertFalse(controller.settingsWindow.isVisible());

        controller.showNameInSettingsWindow();

        assertSame(window, controller.settingsWindow);
        assertTrue(controller.settingsWindow.isVisible());
    }

    @Test
    public void testCloseSettingsWindow() {
        SettingsWindow window = new SettingsWindow();
        controller.settingsWindow = window;
        controller.settingsWindow.setVisible(true);

        controller.closeSettingsWindow();

        assertFalse(window.isVisible());
        assertFalse(window.isDisplayable());
        assertNull(controller.settingsWindow);
    }

    /**
     * This static method can for testing purposes be used to
     * {@link SettingsWindow} reference of the controller of the given
     * controller.
     *
     * @param controller The controller to access.
     * @return The {@link SettingsWindow} reference of the given controller.
     */
    public static SettingsWindow getControllerSettingsWindow(Controller controller) {
        return controller.settingsWindow;
    }

}
