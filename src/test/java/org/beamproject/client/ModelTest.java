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

import java.security.KeyPair;
import static org.beamproject.client.App.getConfig;
import static org.easymock.EasyMock.*;
import org.beamproject.client.storage.Storage;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.beamproject.common.crypto.EncryptedKeyPair;
import org.beamproject.common.crypto.KeyPairCryptor;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ModelTest {

    private final String USERNAME = "Hanna";
    private final KeyPair KEY_PAIR = EccKeyPairGenerator.generate();
    private User user;
    private Model model;

    @Before
    public void setUp() {
        user = new User(USERNAME, KEY_PAIR);
        model = new Model();
    }

    @Test
    public void testModelOnInitialization() {
        assertEquals(0, model.contactList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUserOnNull() {
        model.setUser(null);
    }

    @Test
    public void testSetUser() {
        user = createMock(User.class);
        model.setUser(user);
        assertSame(user, model.user);
    }

    @Test
    public void testGetUserWhenNotExisting() {
        getConfig().removeProperty("encryptedPublicKey");
        getConfig().removeProperty("encryptedPrivateKey");
        assertNull(model.user);
        assertNull(model.getUser());
        assertNull(model.user);
    }

    @Test
    public void testGetUserWhenExisting() {
        putUserInConfig();

        assertNull(model.user);
        assertEquals(user, model.getUser());
        assertEquals(user, model.user);
    }

    @Test
    public void testGetUserWhenExistingWithServer() {
        putUserInConfig();
        Server server = Server.generate();

    }

    private void putUserInConfig() {
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(getConfig().keyPairPassword(), user.getKeyPair());
        getConfig().setProperty("username", USERNAME);
        getConfig().setProperty("keyPairSalt", encryptedKeyPair.getSalt());
        getConfig().setProperty("encryptedPublicKey", encryptedKeyPair.getEncryptedPublicKey());
        getConfig().setProperty("encryptedPrivateKey", encryptedKeyPair.getEncryptedPrivateKey());

    }

    @Test
    public void testGetServerWhenExistingInConfig() {
        Server server = Server.generate();
        getConfig().setProperty("serverAddress", server.getAddress());

        assertTrue(model.getUser().isServerSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddContactOnNull() {
        model.addContact(null);
    }

    @Test
    public void testAddContact() {
        assertEquals(0, model.contactList.size());
        model.addContact(user);
        assertEquals(1, model.contactList.size());
    }

    @Test
    public void testGetContactList() {
        assertEquals(model.contactList, model.getContactList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetContactListStorageOnNull() {
        model.setContactListStorage(null);
    }

    @Test
    public void testSetAndGetContactListStorageOnAssignment() {
        Storage<ContactList> storage = new Storage<>("hello");
        model.setContactListStorage(storage);
        assertSame(storage, model.contactListStorage);

        assertSame(storage, model.getContactListStorage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHeartbeatTaskOnNull() {
        model.setHeartbeatTask(null);
    }

    @Test
    public void testSetHeartbeatTask() {
        HeartbeatTask task = createMock(HeartbeatTask.class);
        model.setHeartbeatTask(task);
        assertSame(task, model.heartbeatTask);
    }

    @Test
    public void testGetHeartbeatTask() {
        assertNull(model.getHeartbeatTask());
        model.heartbeatTask = createMock(HeartbeatTask.class);
        assertSame(model.heartbeatTask, model.getHeartbeatTask());
    }

    @Test
    public void testTerminateHeartbeatTask() {
        HeartbeatTask task = createMock(HeartbeatTask.class);
        model.heartbeatTask = task;
        model.removeHeartbeatTask();
        assertNull(model.heartbeatTask);
    }

    /**
     * Sets the given user to the given {@link Model}. This can be used for unit
     * testing purposes.
     *
     * @param user The user to set. This can be null.
     * @param model The model on that should be set.
     */
    public static void setUser(User user, Model model) {
        model.user = user;
    }

}
