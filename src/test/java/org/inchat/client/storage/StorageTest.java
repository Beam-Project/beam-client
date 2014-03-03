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

import java.io.File;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class StorageTest {

    private final String STOROGE_PATH = "testStorage.obj";
    private final String STOROGE_OBJECT = "my object to store";
    private String restored;
    private File storageFile;
    private Storage<String> storage;

    @Before
    public void setUp() {
        storageFile = new File(STOROGE_PATH);
        storage = new Storage<>(STOROGE_PATH);
    }

    @After
    public void cleanUp() {
        storageFile.delete();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOnNull() {
        storage = new Storage<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOnEmptyString() {
        storage = new Storage<>("");
    }

    @Test
    public void testConstructorOnAssignment() {
        storage = new Storage<>(STOROGE_PATH);
        assertEquals(STOROGE_PATH, storage.storagePath);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObjectToStoreOnNull() {
        storage.store(null);
    }

    @Test
    public void testObjectToStore() {
        assertFalse(storageFile.exists());
        storage.store(STOROGE_OBJECT);

        assertTrue(storageFile.exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObjectToRetoreOnNull() {
        restored = storage.restore(null);
    }

    @Test
    public void testObjectToRetore() {
        storage.store(STOROGE_OBJECT);
        restored = storage.restore(String.class);
        assertEquals(STOROGE_OBJECT, restored);
    }

    @Test
    public void testObjectToRetoreWithTwoStorages() {
        storage.store(STOROGE_OBJECT);

        Storage<String> restorer = new Storage<>(STOROGE_PATH);
        restored = restorer.restore(String.class);
        assertEquals(STOROGE_OBJECT, restored);
    }

    @Test(expected = StorageException.class)
    public void testObjectToRetoreOnNonExistingFile() {
        assertFalse(storageFile.exists());
        restored = storage.restore(String.class);
    }

    @Test(expected = StorageException.class)
    public void testObjectToRetoreOnNotCastable() {
        storage.store(STOROGE_OBJECT);

        Storage<Integer> restorer = new Storage<>(STOROGE_PATH);
        Integer restoredInt = restorer.restore(Integer.class);
    }

    @Test
    public void testIsStorageExisting() {
        Storage<String> readerStorage = new Storage<>(STOROGE_PATH);

        assertFalse(storageFile.exists());
        assertFalse(storage.isExisting());
        assertFalse(readerStorage.isExisting());

        storage.store(STOROGE_OBJECT);
        assertTrue(storage.isExisting());
        assertTrue(readerStorage.isExisting());
    }

}
