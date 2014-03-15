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
package org.inchat.client;

import java.io.File;
import java.security.KeyPair;
import org.inchat.client.ui.MainWindow;
import org.inchat.common.Config;
import org.inchat.common.crypto.EccKeyPairGenerator;
import org.inchat.common.crypto.EncryptedKeyPair;
import org.inchat.common.crypto.KeyPairCryptor;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AppTest {

    private final String PASSWORD = "password";
    private final String CONFIG_DIRECTORY = "./";
    private final String CONFIG_FILE = CONFIG_DIRECTORY + "client.conf";
    private File configFile;

    @Before
    public void setUp() {
        App.CONFIG_DIRECTORY = CONFIG_DIRECTORY;
        App.CONFIG_FILE = CONFIG_FILE;

        configFile = new File(CONFIG_FILE);
    }

    @After
    public void cleanUp() {
        configFile.delete();
    }

    @Test
    public void testLoadConfigControllerModel() {
        App.loadConfigControllerModel();

        assertNotNull(App.config);
        assertNotNull(App.controller);
        assertNotNull(App.model);
    }

    @Test
    public void testOnPahtInitialization() {
        assertNotNull(App.CONFIG_DIRECTORY);
        assertNotNull(App.CONFIG_FILE);
    }

    @Test
    public void testLoadConfigOnCreatingConfigFile() {
        App.loadConfigControllerModel();

        assertTrue(configFile.exists());
    }
    
    @Test
    public void testLoadParticipantFromConfigFile() {
        App.loadConfigControllerModel();
        KeyPair keyPair = EccKeyPairGenerator.generate();
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(PASSWORD, keyPair);
        
        App.config.setProperty(ClientConfigKey.keyPairPassword, PASSWORD);
        App.config.setProperty(ClientConfigKey.keyPairSalt, encryptedKeyPair.getSalt());
        App.config.setProperty(ClientConfigKey.encryptedPublicKey, encryptedKeyPair.getEncryptedPublicKey());
        App.config.setProperty(ClientConfigKey.encryptedPrivateKey, encryptedKeyPair.getEncryptedPrivateKey());
        
        App.loadParticipant();
        
        assertArrayEquals(keyPair.getPublic().getEncoded(),
                App.model.getParticipant().getPublicKeyAsBytes());
        assertArrayEquals(keyPair.getPrivate().getEncoded(),
                App.model.getParticipant().getPrivateKeyAsBytes());
    }

    @Test
    public void testGetController() {
        assertSame(App.controller, App.getController());
    }

    @Test
    public void testGetModel() {
        assertSame(App.model, App.getModel());
    }

    @Test
    public void testGetMainWindow() {
        assertSame(App.mainWindow, App.getMainWindow());
    }

    @Test
    public void testGetConfig() {
        assertSame(App.config, App.getConfig());
    }

    public static void setAppConfig(Config config) {
        App.config = config;
    }

    /**
     * Overwrites the existing {@link Controller} in {@link App} for unit
     * testing purposes.
     *
     * @param controller The new controller.
     */
    public static void setAppController(Controller controller) {
        App.controller = controller;
    }

    /**
     * Overwrites the existing {@link Model} in {@link App} for unit testing
     * purposes.
     *
     * @param model The new model.
     */
    public static void setAppMdoel(Model model) {
        App.model = model;
    }

    /**
     * Overwrites the existing {@link MainWindow} in {@link App} for unit
     * testing purposes.
     *
     * @param mainWindow The new window.
     */
    public static void setAppMainWindow(MainWindow mainWindow) {
        App.mainWindow = mainWindow;
    }

}
