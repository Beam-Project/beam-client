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
package org.beamproject.client.model;

import org.beamproject.client.BusFake;
import static org.beamproject.client.Event.ENABLE_WIZARD_ADDRESS_GENERATED_ADDRESS;
import static org.beamproject.client.Event.SHOW_WIZARD_ADDRESS_LAYER;
import static org.beamproject.client.Event.SHOW_WIZARD_PASSWORD_LAYER;
import org.beamproject.client.ExecutorFake;
import static org.beamproject.client.model.MainModel.AcceptedSender.CONTACTS;
import org.beamproject.client.util.ConfigKey;
import static org.beamproject.client.util.ConfigKey.ACCEPTED_MESSAGE_SENDER;
import static org.beamproject.client.util.ConfigKey.CONNECT_TO_SERVER;
import static org.beamproject.client.util.ConfigKey.SALT;
import static org.beamproject.client.util.ConfigKey.SERVER_ADDRESS;
import static org.beamproject.client.util.ConfigKey.USERNAME;
import static org.beamproject.client.util.ConfigKey.USER_PRIVATE_KEY;
import static org.beamproject.client.util.ConfigKey.USER_PUBLIC_KEY;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.beamproject.common.crypto.EncryptedConfig;
import org.beamproject.common.util.Config;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class WizardModelTest {

    private final String NAME = "name";
    private final String TEST_SERVER_ADDRESS = Server.generate().getAddress();
    private MainModel mainModel;
    private BusFake busFake;
    private Config<ConfigKey> config;
    private ExecutorFake executorFake;
    private WizardModel model;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        mainModel = createMock(MainModel.class);
        busFake = new BusFake();
        config = createMock(Config.class);
        executorFake = new ExecutorFake();
        model = new WizardModel(mainModel, busFake.getBus(), config, executorFake);
    }

    @After
    public void verifyBus() {
        busFake.verify();
    }

    @Test
    public void testProcessWelcomeLayer() {
        model.username = NAME;
        model.serverAddress = TEST_SERVER_ADDRESS;

        mainModel.setServer(anyObject(Server.class));
        expectLastCall();
        mainModel.setUser(anyObject(User.class));
        expectLastCall();
        replay(mainModel);

        model.processWelcomeLayer(NAME, TEST_SERVER_ADDRESS);

        assertEquals(SHOW_WIZARD_ADDRESS_LAYER, busFake.getNextEvent());
        assertEquals(ENABLE_WIZARD_ADDRESS_GENERATED_ADDRESS, busFake.getNextEvent());
        verify(mainModel);
    }

    @Test
    public void testProcessAddressLayer() {
        model.processAddressLayer();

        assertEquals(SHOW_WIZARD_PASSWORD_LAYER, busFake.getNextEvent());
    }

    @Test
    public void testProcessPasswordLayerWithRememberingPassword() {
        testProcessPasswordLayer(true);
    }

    @Test
    public void testProcessPasswordLayerWithoutRememberingPassword() {
        testProcessPasswordLayer(false);
    }

    @SuppressWarnings("unchecked")
    private void testProcessPasswordLayer(final boolean rememberPassword) {
        model.server = Server.generate();
        model.user = new User(NAME, EccKeyPairGenerator.generate(), model.server);
        final char[] password = "mypass".toCharArray();

        mainModel.applyUserEnvironment();
        expectLastCall();
        mainModel.setEncryptedConfig(anyObject(EncryptedConfig.class));
        expectLastCall();
        mainModel.rememberPassword(rememberPassword);
        expectLastCall();
        mainModel.storeConfigs();
        expectLastCall();
        config.set(anyObject(SALT.getClass()), anyObject(byte[].class));
        expectLastCall();

        replay(mainModel, config);

        model.processPasswordLayer(password, rememberPassword);
        assertTrue(model.encryptedConfig.contains(USERNAME));
        assertTrue(model.encryptedConfig.contains(USER_PUBLIC_KEY));
        assertTrue(model.encryptedConfig.contains(USER_PRIVATE_KEY));
        assertTrue(model.encryptedConfig.contains(SERVER_ADDRESS));
        assertTrue(model.encryptedConfig.contains(CONNECT_TO_SERVER));
        assertTrue(model.encryptedConfig.contains(ACCEPTED_MESSAGE_SENDER));
        assertEquals(CONTACTS.toString(), model.encryptedConfig.getAsString(ACCEPTED_MESSAGE_SENDER));

        verify(mainModel, config);
    }

}
