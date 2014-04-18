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
import static org.easymock.EasyMock.*;
import org.beamproject.client.storage.Storage;
import org.beamproject.client.ui.InfoWindow;
import org.beamproject.client.ui.MainWindow;
import org.beamproject.client.ui.settings.GeneralPanel;
import org.beamproject.client.ui.settings.SettingsWindow;
import org.beamproject.client.ui.settings.SettingsWindowTest;
import org.beamproject.common.Contact;
import org.beamproject.common.Participant;
import org.beamproject.common.crypto.EccKeyPairGenerator;
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
        AppTest.setAppMainWindow(mainWindow);
        contactList = new ContactList();
        controller = new Controller();

        App.controller = controller;
    }

    @After
    public void verifyMocks() {
        verify(writer, model, contact, storage, mainWindow);
    }

    private void setMocksInReplayMode() {
        replay(writer, model, contact, storage, mainWindow);
    }

    @Test
    public void testConstructorOnInstatiation() {
        setMocksInReplayMode();
        assertTrue(controller.conversationWindows.isEmpty());
    }

    @Test
    public void testSetUsername() {
        String username = "Timmeeee";
        App.getConfig().setProperty("username", username);
        mainWindow.setUsername(username);
        expectLastCall();
        writer.writeConfig(App.config, Config.FOLDER, Config.FILE);
        expectLastCall();
        setMocksInReplayMode();

        controller.setUsername(username);
    }

    @Test
    public void testAddContactOnInvocingModelOnNull() {
        model.addContact(anyObject(Contact.class));
        expectLastCall().once();
        expect(model.getContactListStorage()).andReturn(storage).anyTimes();
        expect(model.getContactList()).andReturn(contactList);
        storage.store(contactList);
        expectLastCall();
        setMocksInReplayMode();

        controller.addContact(null); // should just invoke model
    }

    @Test
    public void testSetServer() {
        Participant server = new Participant(EccKeyPairGenerator.generate());
        writer.writeConfig(App.config, Config.FOLDER, Config.FILE);
        expectLastCall();
        model.setServer(server);
        expectLastCall();
        setMocksInReplayMode();

        App.getConfig().removeProperty("serverSalt");
        App.getConfig().removeProperty("encryptedServerPublicKey");

        controller.setServer(server);

        assertTrue(App.getConfig().serverSalt().length() > 20);
        assertTrue(App.getConfig().encryptedServerPublicKey().length() > 40);
    }

    @Test
    public void testSetServerUrl() {
        App.getConfig().setProperty("serverUrl", URL);
        writer.writeConfig(App.config, Config.FOLDER, Config.FILE);
        expectLastCall();
        setMocksInReplayMode();

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
        setMocksInReplayMode();

        controller.addContact(contact);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenConversationWindowOnNull() {
        setMocksInReplayMode();
        controller.openConversationWindow(null);
    }

    @Test
    public void testOpenConversationWindow() {
        expect(contact.getName()).andReturn("myname");
        setMocksInReplayMode();

        assertTrue(controller.conversationWindows.isEmpty());
        controller.openConversationWindow(contact);
        assertEquals(1, controller.conversationWindows.size());
        assertTrue(controller.conversationWindows.get(0).isVisible());
        assertSame(contact, controller.conversationWindows.get(0).getContact());
    }

    @Test(expected = IllegalStateException.class)
    public void testWriteContactStorageOnNotExistingStorage() {
        expect(model.getContactListStorage()).andReturn(null);
        setMocksInReplayMode();

        controller.writeContactListStorage();
    }

    @Test
    public void testWriteContactStorage() {
        expect(model.getContactListStorage()).andReturn(storage).times(2);
        expect(model.getContactList()).andReturn(contactList);
        storage.store(contactList);
        expectLastCall();
        setMocksInReplayMode();

        controller.writeContactListStorage();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnNulls() {
        setMocksInReplayMode();
        controller.sendMessage(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnNullTarget() {
        setMocksInReplayMode();
        controller.sendMessage(null, "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageOnEmptyMessage() {
        setMocksInReplayMode();
        controller.sendMessage(contact, "");
    }

    @Ignore // Only wirks if the server is running
    @Test
    public void testSendMessage() {
        Participant server = new Participant(EccKeyPairGenerator.generate());
        Participant user = new Participant(EccKeyPairGenerator.generate());
        Contact messageContact = new Contact(server, user, "john");
        controller.sendMessage(messageContact, "hello");
    }

    @Test
    public void testShowInfoWindow() {
        expect(model.getUserUrl()).andReturn("url");
        setMocksInReplayMode();
        assertNull(controller.infoWindow);
        controller.showInfoWindow();
        assertTrue(controller.infoWindow.isVisible());
    }

    @Test
    public void testShowInfoWindowOnReusingExistingWindow() {
        expect(model.getUserUrl()).andReturn("url");
        setMocksInReplayMode();
        InfoWindow window = new InfoWindow();
        controller.infoWindow = window;
        assertFalse(window.isVisible());
        controller.showInfoWindow();

        assertSame(window, controller.infoWindow);
        assertTrue(controller.infoWindow.isVisible());
    }

    @Test
    public void testCloseInfoWindow() {
        expect(model.getUserUrl()).andReturn("url");
        setMocksInReplayMode();
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
        setMocksInReplayMode();
        assertNull(controller.settingsWindow);

        controller.showSettingsWindow();

        JPanel activeContentPanel = SettingsWindowTest.getContentPanel(controller.settingsWindow);
        assertTrue(activeContentPanel.getComponent(0) instanceof GeneralPanel);
        assertTrue(controller.settingsWindow.isVisible());
    }

    @Test
    public void testShowSettingsWindowOnReusingExistingWindow() {
        setMocksInReplayMode();
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
        setMocksInReplayMode();
        replay(settingsWindow);

        controller.settingsWindow = settingsWindow;
        controller.showNameInSettingsWindow();

        assertSame(settingsWindow, controller.settingsWindow);
        verify(settingsWindow);
    }

    @Test
    public void testCloseSettingsWindow() {
        setMocksInReplayMode();

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
