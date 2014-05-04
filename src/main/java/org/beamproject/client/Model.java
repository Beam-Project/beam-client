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

import java.security.KeyPair;
import static org.beamproject.client.App.getConfig;
import org.beamproject.client.storage.Storage;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.beamproject.common.crypto.EncryptedKeyPair;
import org.beamproject.common.crypto.KeyPairCryptor;
import org.beamproject.common.util.Base58;
import org.beamproject.common.util.Exceptions;

public class Model {

    User user;
    Storage<ContactList> contactListStorage;
    ContactList contactList = new ContactList();
    HeartbeatTask heartbeatTask;

    /**
     * Sets the given user to this {@link Model}.
     *
     * @param user This may not be null.
     * @throws IllegalArgumentException If the argument is null.
     */
    public void setUser(User user) {
        Exceptions.verifyArgumentsNotNull(user);

        this.user = user;
    }

    /**
     * Reads the encrypted public and private key, and the username from the
     * config file and restores the user with that. If the user is already
     * loaded, the reference will be returned.
     *
     * @return The user or, if it has not been loaded so far and if it does not
     * exist in the config file, null will be returned.
     */
    public User getUser() {
        if (user == null
                && getConfig().encryptedPublicKey() != null
                && getConfig().encryptedPrivateKey() != null) {
            byte[] publicKey = Base58.decode(getConfig().encryptedPublicKey());
            byte[] privateKey = Base58.decode(getConfig().encryptedPrivateKey());
            byte[] salt = Base58.decode(getConfig().keyPairSalt());

            EncryptedKeyPair encryptedKeyPair = new EncryptedKeyPair(publicKey, privateKey, salt);
            KeyPair keyPair = KeyPairCryptor.decrypt(getConfig().keyPairPassword(), encryptedKeyPair);

            try {
                Server server = new Server(getConfig().serverAddress());
                user = new User(getConfig().username(), keyPair, server);
            } catch (IllegalArgumentException ex) {
                user = new User(getConfig().username(), keyPair);
            }
        }

        return user;
    }

    public void addContact(User user) {
        Exceptions.verifyArgumentsNotNull(user);

        contactList.addElement(user);
    }

    public ContactList getContactList() {
        return contactList;
    }

    /**
     * Sets the given storage to this {@link Model}.
     *
     * @param storage This may not be null.
     * @throws IllegalArgumentException If the argument is null.
     */
    public void setContactListStorage(Storage<ContactList> storage) {
        Exceptions.verifyArgumentsNotNull(storage);

        this.contactListStorage = storage;
    }

    public Storage<ContactList> getContactListStorage() {
        return contactListStorage;
    }

    /**
     * Sets the given {@link HeartbeatTask} to this model.
     *
     * @param task The task to set.
     * @throws IllegalArgumentException If the argument is null.
     */
    public void setHeartbeatTask(HeartbeatTask task) {
        Exceptions.verifyArgumentsNotNull(task);

        this.heartbeatTask = task;
    }

    public HeartbeatTask getHeartbeatTask() {
        return heartbeatTask;
    }

    /**
     * Sets the reference to the local {@link HeartbeatTask} reference to null.
     * This has no effect if the reference already points to null.
     */
    public void removeHeartbeatTask() {
        heartbeatTask = null;
    }

}
