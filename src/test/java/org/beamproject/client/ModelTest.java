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

import static org.easymock.EasyMock.*;
import org.beamproject.client.storage.Storage;
import org.beamproject.common.Contact;
import org.beamproject.common.Participant;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.beamproject.common.crypto.EncryptedKeyPair;
import org.beamproject.common.crypto.KeyPairCryptor;
import org.beamproject.common.util.Base58;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ModelTest {

    private Model model;
    private Contact contact;

    @Before
    public void setUp() {
        model = new Model();
        contact = createMock(Contact.class);
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
        Participant user = createMock(Participant.class);
        model.setUser(user);
        assertSame(user, model.user);
    }

    @Test
    public void testGetUserWhenNotExisting() {
        App.getConfig().removeProperty("encryptedPublicKey");
        App.getConfig().removeProperty("encryptedPrivateKey");
        assertNull(model.user);
        assertNull(model.getUser());
        assertNull(model.user);
    }

    @Test
    public void testGetUserWhenExistingInConfig() {
        Participant user = Participant.generate();
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(App.getConfig().keyPairPassword(), user.getKeyPair());
        App.getConfig().setProperty("keyPairSalt", encryptedKeyPair.getSalt());
        App.getConfig().setProperty("encryptedPublicKey", encryptedKeyPair.getEncryptedPublicKey());
        App.getConfig().setProperty("encryptedPrivateKey", encryptedKeyPair.getEncryptedPrivateKey());

        assertNull(model.user);
        assertEquals(user, model.getUser());
        assertEquals(user, model.user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetServerOnNull() {
        model.setServer(null);
    }

    @Test
    public void testSetServer() {
        Participant server = createMock(Participant.class);
        model.setServer(server);
        assertSame(server, model.server);
    }

    @Test
    public void testGetServerWhenNotExisting() {
        App.getConfig().removeProperty("encryptedServerPublicKey");
        assertNull(model.server);
        assertNull(model.getServer());
        assertNull(model.server);
    }

    @Test
    public void testGetServerWhenExistingInConfig() {
        Participant server = Participant.generate();
        Participant serverWithPublicKey = new Participant(EccKeyPairGenerator.fromPublicKey(server.getPublicKeyAsBytes()));
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(App.getConfig().keyPairPassword(), serverWithPublicKey.getKeyPair());
        App.getConfig().setProperty("serverSalt", encryptedKeyPair.getSalt());
        App.getConfig().setProperty("encryptedServerPublicKey", encryptedKeyPair.getEncryptedPublicKey());

        assertNull(model.server);
        assertEquals(serverWithPublicKey, model.getServer());
        assertEquals(serverWithPublicKey, model.server);
    }

    @Test
    public void testGetUserUrl() {
        String username = "mr spock";
        App.getConfig().setProperty("username", username);
        App.getConfig().removeProperty("encryptedServerPublicKey");
        String usernameAsBase58 = Base58.encode(username.getBytes());
        String userPart = "-user-";
        String serverPart = "-server-";

        assertTrue(model.getUserUrl().isEmpty());
        model.setUser(createMock(Participant.class));
        assertTrue(model.getUserUrl().isEmpty());

        model.setServer(createMock(Participant.class));
        expect(model.user.getPublicKeyAsBase58()).andReturn(userPart);
        expect(model.server.getPublicKeyAsBase58()).andReturn(serverPart);
        replay(model.user, model.server);

        String url = model.getUserUrl();

        assertEquals("beam:" + serverPart + "." + userPart + "?name=" + usernameAsBase58, url);
        verify(model.user, model.server);
    }

    public class AccessException extends RuntimeException {

        private static final long serialVersionUID = 1L;
    };

    @Test(expected = AccessException.class)
    public void testGetUserUrlOnUsingGetUser() {
        new Model() {
            @Override
            public Participant getUser() {
                throw new AccessException();
            }

            @Override
            public Participant getServer() {
                return Participant.generate();
            }
        }.getUserUrl();
    }

    @Test(expected = AccessException.class)
    public void testGetUserUrlOnUsingGetServer() {
        new Model() {
            @Override
            public Participant getUser() {
                return Participant.generate();
            }

            @Override
            public Participant getServer() {
                throw new AccessException();
            }
        }.getUserUrl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddContactOnNull() {
        model.addContact(null);
    }

    @Test
    public void testAddContact() {
        replay(contact);
        assertEquals(0, model.contactList.size());
        model.addContact(contact);
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

    /**
     * Sets the given user to the given {@link Model}. This can be used for unit
     * testing purposes.
     *
     * @param user The user to set. This can be null.
     * @param model The model on that should be set.
     */
    public static void setUser(Participant user, Model model) {
        model.user = user;
    }

    /**
     * Sets the given server to the given {@link Model}. This can be used for
     * unit testing purposes.
     *
     * @param server The server to set. This can be null.
     * @param model The model on that should be set.
     */
    public static void setServer(Participant server, Model model) {
        model.server = server;
    }

}
