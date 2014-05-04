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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.beamproject.client.App.configWriter;
import static org.beamproject.client.App.getConfig;
import static org.beamproject.client.App.getExecutor;
import static org.beamproject.client.App.getModel;
import org.beamproject.client.storage.Storage;
import org.beamproject.client.ui.ConversationWindow;
import org.beamproject.client.ui.Frames;
import org.beamproject.client.ui.InfoWindow;
import org.beamproject.client.ui.MainWindow;
import org.beamproject.client.ui.SetUpDialog;
import org.beamproject.client.ui.settings.SettingsWindow;
import org.beamproject.common.Contact;
import org.beamproject.common.Participant;
import org.beamproject.common.Session;
import org.beamproject.common.crypto.EncryptedKeyPair;
import org.beamproject.common.crypto.KeyPairCryptor;
import org.beamproject.common.util.Task;

/**
 * The controller manages the activities between the components, typically
 * triggered by the user interface.
 */
public class Controller {

    final String CONTACTS_STORAGE_FILE = Config.FOLDER + "contacts.storage";
    MainWindow mainWindow;
    List<ConversationWindow> conversationWindows = new ArrayList<>();
    InfoWindow infoWindow;
    SettingsWindow settingsWindow;
    Session session;

    public void loadUser() {
        if (isFirstStart()) {
            showSetUpDialog();
            generateUser();
            storeConfig();
        }
    }

    private void generateUser() {
        Participant user = Participant.generate();
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(getConfig().keyPairPassword(), user.getKeyPair());
        getConfig().setProperty("keyPairSalt", encryptedKeyPair.getSalt());
        getConfig().setProperty("encryptedPublicKey", encryptedKeyPair.getEncryptedPublicKey());
        getConfig().setProperty("encryptedPrivateKey", encryptedKeyPair.getEncryptedPrivateKey());
        getModel().setUser(user);
    }

    public void storeConfig() {
        configWriter.writeConfig(getConfig(), Config.FOLDER, Config.FILE);
    }

    public void setUsername(String name) {
        getConfig().setProperty("username", name);
        getMainWindow().setUsername(name);
        storeConfig();
    }

    public void setServer(Participant server) {
        getModel().setServer(server);
        EncryptedKeyPair encryptedPublicKey = KeyPairCryptor.encrypt(getConfig().keyPairPassword(), server.getKeyPair());
        getConfig().setProperty("serverSalt", encryptedPublicKey.getSalt());
        getConfig().setProperty("encryptedServerPublicKey", encryptedPublicKey.getEncryptedPublicKey());
        storeConfig();
    }

    public void setServerUrl(String serverUrl) {
        getConfig().setProperty("serverUrl", serverUrl);
        storeConfig();
    }

    public void addContact(Contact contact) {
        getModel().addContact(contact);
        writeContactListStorage();
    }

    public void toggleConnectionStatus() {
        boolean doConnect = !isConnectedToServer();
        getExecutor().runAsync(new ConnectorTask(doConnect));
    }

    boolean isConnectedToServer() {
        return session != null;
    }

    boolean isServerUrlAvailable(String serverUrl) {
        try {
            URL candidate = new URL(serverUrl);
            return "http".equalsIgnoreCase(candidate.getProtocol());
        } catch (MalformedURLException ex) {
            return false;
        }
    }

    public void openConversationWindow(Contact contact) {
        ConversationWindow window = new ConversationWindow();
        Frames.setIcons(window);
        window.setContact(contact);
        conversationWindows.add(window);
        window.setVisible(true);
    }

    private void readContactListStorage() {
        Storage<ContactList> storage = new Storage<>(CONTACTS_STORAGE_FILE);
        getModel().setContactListStorage(storage);

        if (!storage.isExisting()) {
            return;
        }

        ContactList restoredContactList = storage.restore(ContactList.class);
        ContactList existingContactList = getModel().getContactList();

        for (int i = 0; i < restoredContactList.getSize(); i++) {
            existingContactList.addElement(restoredContactList.elementAt(i));
        }
    }

    void writeContactListStorage() {
        if (getModel().getContactListStorage() == null) {
            throw new IllegalStateException("The contact storage has to be loaded first.");
        }

        ContactList list = getModel().getContactList();
        getModel().getContactListStorage().store(list);
    }

    public void showMainWindow() {
        getExecutor().runAsync(new Task() {
            @Override
            public void run() {
                mainWindow = getMainWindow();
                mainWindow.setAutoRequestFocus(isFirstStart());
                mainWindow.setVisible(true);
            }
        });
    }

    boolean isFirstStart() {
        return getModel().getUser() == null;
    }

    private void showSetUpDialog() {
        SetUpDialog dialog = new SetUpDialog();
        Frames.setIcons(dialog);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
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
        getSettingsWindow().openIdentityMenuWithFocusedUsername();
        getSettingsWindow().setVisible(true);
    }

    public void closeSettingsWindow() {
        getSettingsWindow().dispose();
        settingsWindow = null;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public MainWindow getMainWindow() {
        if (mainWindow == null) {
            mainWindow = new MainWindow();
            Frames.setIcons(mainWindow);
            getExecutor().runAsync(new Task() {
                @Override
                public void run() {
                    readContactListStorage();
                }
            });
        }

        return mainWindow;
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

}
