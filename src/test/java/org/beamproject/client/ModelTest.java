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
    Contact contact;

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
    public void testSetParticipantOnNull() {
        model.setParticipant(null);
    }

    @Test
    public void testSetParticipant() {
        Participant participant = createMock(Participant.class);
        model.setParticipant(participant);
        assertSame(participant, model.participant);
    }

    @Test
    public void testGetParticipantWhenNotExisting() {
        App.getConfig().removeProperty("encryptedPublicKey");
        App.getConfig().removeProperty("encryptedPrivateKey");
        assertNull(model.participant);
        assertNull(model.getParticipant());
        assertNull(model.participant);
    }

    @Test
    public void testGetParticipantWhenExistingInConfig() {
        Participant participant = new Participant(EccKeyPairGenerator.generate());
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(App.getConfig().keyPairPassword(), participant.getKeyPair());
        App.getConfig().setProperty("keyPairSalt", encryptedKeyPair.getSalt());
        App.getConfig().setProperty("encryptedPublicKey", encryptedKeyPair.getEncryptedPublicKey());
        App.getConfig().setProperty("encryptedPrivateKey", encryptedKeyPair.getEncryptedPrivateKey());

        assertNull(model.participant);
        assertEquals(participant, model.getParticipant());
        assertEquals(participant, model.participant);
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
        Participant server = new Participant(EccKeyPairGenerator.generate());
        Participant serverWithPublicKey = new Participant(EccKeyPairGenerator.fromPublicKey(server.getPublicKeyAsBytes()));

        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(App.getConfig().keyPairPassword(), serverWithPublicKey.getKeyPair());
        App.getConfig().setProperty("serverSalt", encryptedKeyPair.getSalt());
        App.getConfig().setProperty("encryptedServerPublicKey", encryptedKeyPair.getEncryptedPublicKey());

        assertNull(model.server);
        assertEquals(serverWithPublicKey, model.getServer());
        assertEquals(serverWithPublicKey, model.server);
    }

    @Test
    public void testGetParticipantUrlOnMissingParts() {
        String username = "mr spock";
        App.getConfig().setProperty("participantName", username);
        String usernameAsBase58 = Base58.encode(username.getBytes());
        String userPart = "-user-";
        String serverPart = "-server-";

        assertTrue(model.getParticipantUrl().isEmpty());
        model.setParticipant(createMock(Participant.class));
        assertTrue(model.getParticipantUrl().isEmpty());

        model.setServer(createMock(Participant.class));
        expect(model.participant.getPublicKeyAsBase58()).andReturn(userPart);
        expect(model.server.getPublicKeyAsBase58()).andReturn(serverPart);
        replay(model.participant, model.server);

        String url = model.getParticipantUrl();

        assertEquals("beam:" + serverPart + "." + userPart + "?name=" + usernameAsBase58, url);
        verify(model.participant, model.server);
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

}
