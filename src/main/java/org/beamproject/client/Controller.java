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

import java.util.ArrayList;
import java.util.List;
import org.beamproject.common.network.HttpConnector;
import org.beamproject.client.storage.Storage;
import org.beamproject.client.ui.ConversationWindow;
import org.beamproject.client.ui.Frames;
import org.beamproject.client.ui.InfoWindow;
import org.beamproject.client.ui.settings.SettingsWindow;
import org.beamproject.common.Config;
import org.beamproject.common.Contact;
import org.beamproject.common.Message;
import org.beamproject.common.MessageField;
import org.beamproject.common.crypto.CryptoPacker;
import org.beamproject.common.util.Exceptions;

/**
 * The controller manages the activities between the components, typically
 * triggered by the user interface.
 */
public class Controller {

    public final static String FORMAT_VERSION = "1.0";
    Config config;
    List<ConversationWindow> conversationWindows = new ArrayList<>();
    InfoWindow infoWindow;
    SettingsWindow settingsWindow;
    CryptoPacker cryptoPacker;

    public Controller(Config config) {
        Exceptions.verifyArgumentsNotNull(config);

        this.config = config;
    }

    public void changeName(String name) {
        config.setProperty(ClientConfigKey.participantName, name);
        App.getMainWindow().setUsername(name);
    }

    public void setServerUrl(String serverUrl) {
        config.setProperty(ClientConfigKey.serverUrl, serverUrl);
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

    public void sendMessage(Contact target, String content) {
        Exceptions.verifyArgumentsNotNull(target);
        Exceptions.verifyArgumentsNotEmpty(content);

        Message plaintext = assemblePlaintextMessage(target, content);
        byte[] ciphertext = getCryptoPacker().packAndEncrypt(plaintext, target.getServer());
        byte[] response = sendCiphertextToNextServer(ciphertext, config.getProperty(ClientConfigKey.serverUrl));
        System.out.println("response: " + new String(response));
    }

    private Message assemblePlaintextMessage(Contact target, String content) {
        Message plaintext = new Message();

        plaintext.setVersion(FORMAT_VERSION);
        plaintext.setParticipant(target.getServer());
        plaintext.appendContent(MessageField.CNT_MSG, content.getBytes());

        return plaintext;
    }

    private byte[] sendCiphertextToNextServer(byte[] ciphertext, String targetServerUrl) {
        HttpConnector http = new HttpConnector(targetServerUrl);
        return http.excutePost(ciphertext);
    }

    public void showInfoWindow() {
        getInfoWindow().setVisible(true);
    }

    public void closeInfoWindow() {
        getInfoWindow().dispose();
        infoWindow = null;
    }

    public void showSettingsWindow() {
        getSettingsWindow().setVisible(true);
    }

    public void showNameInSettingsWindow() {
        getSettingsWindow().openIdentityMenuWithFocusedName();
        getSettingsWindow().setVisible(true);
    }

    public void closeSettingsWindow() {
        getSettingsWindow().dispose();
        settingsWindow = null;
    }

    private InfoWindow getInfoWindow() {
        if (infoWindow == null) {
            infoWindow = new InfoWindow();
            Frames.setIcons(infoWindow);
        }

        return infoWindow;
    }

    private SettingsWindow getSettingsWindow() {
        if (settingsWindow == null) {
            settingsWindow = new SettingsWindow();
            Frames.setIcons(settingsWindow);
        }

        return settingsWindow;
    }

    private CryptoPacker getCryptoPacker() {
        if (cryptoPacker == null) {
            cryptoPacker = new CryptoPacker();
        }

        return cryptoPacker;
    }

}
