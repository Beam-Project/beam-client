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
package org.inchat.client.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.inchat.common.util.Exceptions;

/**
 * This Storage can be used to store {@link Object}s as simple files on the file
 * system and read them again.
 *
 * @param <T> A generic type of the {@link Storage} to store only object of the
 * type T.
 */
public class Storage<T extends Serializable> {

    String storagePath;

    /**
     * Creates a new {@link  Storage} object that reads and writes the file at
     * the given {@code storagePath}.
     *
     * @param storagePath The path of the target file of this {@link Storage}.
     * @throws IllegalArgumentException If the argument is null or empty.
     */
    public Storage(String storagePath) {
        Exceptions.verifyArgumentNotEmpty(storagePath);

        this.storagePath = storagePath;
    }

    /**
     * Serializes and stores the given argument.
     *
     * @param objectToStore The object to store.
     * @throws IllegalArgumentException If the argument is null.
     * @throws StorageException If the argument could not be serialized or
     * written.
     */
    public void store(T objectToStore) {
        Exceptions.verifyArgumentNotNull(objectToStore);

        try {
            serialize(objectToStore);
        } catch (IOException ex) {
            throw new StorageException("Could not serialize and store the given object: " + ex.getMessage());
        }
    }

    private void serialize(T objectToStore) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(storagePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(objectToStore);
    }

    /**
     * Restores the object from the storage path.
     *
     * @param classType The {@link Class} of the configured type. For example,
     * if you stored a String, you would invoke this method with
     * {@code String.class} as argument.
     * @return The restored object of the type {@code T}.
     * @throws IllegalArgumentException If the argument is null.
     * @throws StorageException If anything goes wrong while restoring.
     */
    public T restore(Class<T> classType) {
        Exceptions.verifyArgumentNotNull(classType);

        try {
            return deserialize(classType);
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            throw new StorageException("Could not restore the requested object: " + ex.getMessage());
        }
    }

    private T deserialize(Class<T> classType) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(storagePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object restoredObject = objectInputStream.readObject();
        return classType.cast(restoredObject);
    }

}
