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
package org.beamproject.client;

import javax.swing.JPanel;
import static org.beamproject.client.App.getConfig;
import static org.easymock.EasyMock.*;
import org.beamproject.client.storage.Storage;
import org.beamproject.client.ui.InfoWindow;
import org.beamproject.client.ui.MainWindow;
import org.beamproject.client.ui.settings.GeneralPanel;
import org.beamproject.client.ui.settings.SettingsWindow;
import org.beamproject.client.ui.settings.SettingsWindowTest;
import org.beamproject.common.Contact;
import org.beamproject.common.Participant;
import org.beamproject.common.util.ConfigWriter;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

public class ControllerTest {

    private final String URL = "http://srv.beamproject.org";
    private Controller controller;
    private Model model;
    private Contact contact;
    private Storage<ContactList> storage;
    private ContactList contactList;
    private MainWindow mainWindow;
    private ConfigWriter writer;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        model = createMock(Model.class);
        contact = createMock(Contact.class);
        storage = createMock(Storage.class);
        mainWindow = createMock(MainWindow.class);
        writer = createMock(ConfigWriter.class);
        AppTest.setAppConfigWriter(writer);
        AppTest.setAppModel(model);
        contactList = new ContactList();
        controller = new Controller();

        App.controller = controller;
    }

    @After
    public void verifyMocks() {
        verify(writer, model, contact, storage, mainWindow);
    }

    private void replayMocks() {
        replay(writer, model, contact, storage, mainWindow);
    }

    @Test
    public void testConstructorOnInstatiation() {
        replayMocks();
        assertTrue(controller.conversationWindows.isEmpty());
    }

    @Test
    public void testStoreConfig() {
        writer.writeConfig(getConfig(), Config.FOLDER, Config.FILE);
        expectLastCall();
        replayMocks();

        controller.storeConfig();
    }

    @Test
    public void testSetUsername() {
        String username = "Timmeeee";
        writer.writeConfig(getConfig(), Config.FOLDER, Config.FILE);
        expectLastCall().anyTimes();
        expect(model.getContactList()).andReturn(contactList);
        replayMocks();

        controller.setUsername(username);

        assertEquals(username, getConfig().username());
    }

    @Test
    public void testAddContactOnInvocingModelOnNull() {
        model.addContact(anyObject(Contact.class));
        expectLastCall().once();
        expect(model.getContactListStorage()).andReturn(storage).anyTimes();
        expect(model.getContactList()).andReturn(contactList);
        storage.store(contactList);
        expectLastCall();
        replayMocks();

        controller.addContact(null); // should just invoke model
    }

    @Test
    public void testSetServer() {
        Participant server = Participant.generate();
        writer.writeConfig(getConfig(), Config.FOLDER, Config.FILE);
        expectLastCall().anyTimes();
        model.setServer(server);
        expectLastCall();
        replayMocks();

        getConfig().removeProperty("serverSalt");
        getConfig().removeProperty("encryptedServerPublicKey");

        controller.setServer(server);

        assertTrue(getConfig().serverSalt().length() > 20);
        assertTrue(getConfig().encryptedServerPublicKey().length() > 40);
    }

    @Test
    public void testSetServerUrl() {
        getConfig().setProperty("serverUrl", URL);
        writer.writeConfig(getConfig(), Config.FOLDER, Config.FILE);
        expectLastCall();
        replayMocks();

        controller.setServerUrl(URL);
    }

    @Test
    public void testAddContact() {
        model.addContact(anyObject(Contact.class));
        expectLastCall().once();
        expect(model.getContactListStorage()).andReturn(storage).anyTimes();
        expect(model.getContactList()).andReturn(contactList);
        storage.store(contactList);
        expectLastCall();
        replayMocks();

        controller.addContact(contact);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenConversationWindowOnNull() {
        replayMocks();
        controller.openConversationWindow(null);
    }

    @Test
    public void testOpenConversationWindow() {
        expect(contact.getName()).andReturn("myname");
        replayMocks();

        assertTrue(controller.conversationWindows.isEmpty());
        controller.openConversationWindow(contact);
        assertEquals(1, controller.conversationWindows.size());
        assertTrue(controller.conversationWindows.get(0).isVisible());
        assertSame(contact, controller.conversationWindows.get(0).getContact());
    }

    @Test(expected = IllegalStateException.class)
    public void testWriteContactStorageOnNotExistingStorage() {
        expect(model.getContactListStorage()).andReturn(null);
        replayMocks();

        controller.writeContactListStorage();
    }

    @Test
    public void testWriteContactStorage() {
        expect(model.getContactListStorage()).andReturn(storage).times(2);
        expect(model.getContactList()).andReturn(contactList);
        storage.store(contactList);
        expectLastCall();
        replayMocks();

        controller.writeContactListStorage();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnNulls() {
        replayMocks();
        controller.sendMessage(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnNullTarget() {
        replayMocks();
        controller.sendMessage(null, "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnEmptyMessage() {
        replayMocks();
        controller.sendMessage(contact, "");
    }

    @Ignore // Only wirks if the server is running
    @Test
    public void testSendMessage() {
        Participant server = Participant.generate();
        Participant user = Participant.generate();
        Contact messageContact = new Contact(server, user, "john");
        controller.sendMessage(messageContact, "hello");
    }

    @Test
    public void testIsFirstStart() {
        expect(model.getUser()).andReturn(null);
        expect(model.getUser()).andReturn(createMock(Participant.class));
        replayMocks();

        assertTrue(controller.isFirstStart());
        assertFalse(controller.isFirstStart());
    }

    @Test
    public void testShowInfoWindow() {
        expect(model.getUserUrl()).andReturn("url").times(2);
        replayMocks();
        assertNull(controller.infoWindow);
        controller.showInfoWindow();
        assertTrue(controller.infoWindow.isVisible());
    }

    @Test
    public void testShowInfoWindowOnReusingExistingWindow() {
        expect(model.getUserUrl()).andReturn("url").times(2);
        replayMocks();
        InfoWindow window = new InfoWindow();
        controller.infoWindow = window;
        assertFalse(window.isVisible());
        controller.showInfoWindow();

        assertSame(window, controller.infoWindow);
        assertTrue(controller.infoWindow.isVisible());
    }

    @Test
    public void testCloseInfoWindow() {
        expect(model.getUserUrl()).andReturn("url").times(2);
        replayMocks();
        InfoWindow window = new InfoWindow();
        controller.infoWindow = window;
        controller.infoWindow.setVisible(true);

        controller.closeInfoWindow();

        assertFalse(window.isVisible());
        assertFalse(window.isDisplayable());
        assertNull(controller.infoWindow);
    }

    @Test
    public void testShowSettingsWindow() {
        replayMocks();
        assertNull(controller.settingsWindow);

        controller.showSettingsWindow();

        JPanel activeContentPanel = SettingsWindowTest.getContentPanel(controller.settingsWindow);
        assertTrue(activeContentPanel.getComponent(0) instanceof GeneralPanel);
        assertTrue(controller.settingsWindow.isVisible());
    }

    @Test
    public void testShowSettingsWindowOnReusingExistingWindow() {
        replayMocks();
        SettingsWindow window = new SettingsWindow();
        controller.settingsWindow = window;
        assertFalse(window.isVisible());

        controller.showSettingsWindow();

        assertSame(window, controller.settingsWindow);
        assertTrue(controller.settingsWindow.isVisible());
    }

    @Test
    public void testShowIdentityNameInSettingsWindowOnReusingExistingWindow() {
        SettingsWindow settingsWindow = createMock(SettingsWindow.class);
        settingsWindow.openIdentityMenuWithFocusedUsername();
        expectLastCall();
        settingsWindow.setVisible(true);
        expectLastCall();
        replayMocks();
        replay(settingsWindow);

        controller.settingsWindow = settingsWindow;
        controller.showNameInSettingsWindow();

        assertSame(settingsWindow, controller.settingsWindow);
        verify(settingsWindow);
    }

    @Test
    public void testCloseSettingsWindow() {
        replayMocks();

        SettingsWindow window = new SettingsWindow();
        controller.settingsWindow = window;
        controller.settingsWindow.setVisible(true);
        controller.closeSettingsWindow();

        assertFalse(window.isVisible());
        assertFalse(window.isDisplayable());
        assertNull(controller.settingsWindow);
    }

    @Test
    public void testGetMainWindowOnNotExistingWindow() {
        expect(model.getContactList()).andReturn(contactList);
        replayMocks();

        assertNull(controller.mainWindow);
        MainWindow window = controller.getMainWindow();
        assertNotNull(window);
        assertSame(window, controller.mainWindow);
        assertSame(window, controller.getMainWindow());
    }

    /**
     * Overwrites the existing {@link MainWindow} in the given
     * {@link Controller} for unit testing purposes.
     *
     * @param mainWindow The new window.
     * @param controller The controller, in which the window should be placed.
     */
    public static void setAppMainWindow(MainWindow mainWindow, Controller controller) {
        controller.mainWindow = mainWindow;
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
