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
import org.beamproject.client.ui.MainWindow;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.beamproject.common.crypto.EncryptedKeyPair;
import org.beamproject.common.crypto.KeyPairCryptor;
import org.beamproject.common.util.ConfigWriter;
import static org.easymock.EasyMock.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AppTest {

    private final String PASSWORD = "password";
    private ConfigWriter writer;

    @Before
    public void setUp() {
        writer = createMock(ConfigWriter.class);
        App.configWriter = writer;
    }

    @Test
    public void testStaticInits() {
        assertNotNull(App.configWriter);
        assertNotNull(App.config);
    }

    @Test
    public void testLoadControllerAndModel() {
        App.loadControllerAndModel();

        assertNotNull(App.config);
        assertNotNull(App.controller);
        assertNotNull(App.model);
    }

    @Test
    public void testLoadParticipantFromConfigFile() {
        App.loadControllerAndModel();
        KeyPair keyPair = EccKeyPairGenerator.generate();
        EncryptedKeyPair encryptedKeyPair = KeyPairCryptor.encrypt(PASSWORD, keyPair);

        App.config.setProperty("keyPairPassword", PASSWORD);
        App.config.setProperty("keyPairSalt", encryptedKeyPair.getSalt());
        App.config.setProperty("encryptedPublicKey", encryptedKeyPair.getEncryptedPublicKey());
        App.config.setProperty("encryptedPrivateKey", encryptedKeyPair.getEncryptedPrivateKey());

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

    @Test
    public void testStoreConfig() {
        writer.writeConfig(App.config, Config.FOLDER, Config.FILE);
        expectLastCall();
        replay(writer);

        App.storeConfig();

        verify(writer);
    }

    /**
     * Overwrites the existing {@link Config} in {@link App} for unit testing
     * purposes.
     *
     * @param config The new config.
     */
    public static void setAppConfig(Config config) {
        App.config = config;
    }

    /**
     * Overwrites the existing {@link ConfigWriter} in {@link App} for unit
     * testing purposes.
     *
     * @param configWriter The new config writer.
     */
    public static void setAppConfigWriter(ConfigWriter configWriter) {
        App.configWriter = configWriter;
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
    public static void setAppModel(Model model) {
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
