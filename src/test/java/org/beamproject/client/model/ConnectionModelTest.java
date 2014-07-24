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

import java.util.HashSet;
import org.beamproject.client.BusFake;
import static org.beamproject.client.Event.UPDATE_CONNECTION_STATUS;
import org.beamproject.client.ExecutorFake;
import static org.beamproject.client.model.ConnectionModel.*;
import org.beamproject.client.util.ConfigKey;
import static org.beamproject.client.util.ConfigKey.*;
import org.beamproject.common.Message;
import static org.beamproject.common.MessageField.ContentField.TypeValue.HANDSHAKE;
import org.beamproject.common.Participant;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.beamproject.common.carrier.ClientCarrier;
import org.beamproject.common.carrier.MqttConnectionPoolFactory;
import org.beamproject.common.crypto.CryptoPackerPool;
import org.beamproject.common.crypto.CryptoPackerPoolFactory;
import org.beamproject.common.crypto.EncryptedConfig;
import org.beamproject.common.crypto.HandshakeChallenger;
import static org.easymock.EasyMock.*;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ConnectionModelTest {

    private final String HOST = "myLocalhost";
    private final String PORT = "2345";
    private final Server SERVER = Server.generate();
    private final User USER = User.generate();
    private MainModel mainModel;
    private BusFake busFake;
    private EncryptedConfig<ConfigKey> config;
    private ConnectionModel model;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        config = createMock(EncryptedConfig.class);
        mainModel = createMock(MainModel.class);
        busFake = new BusFake();
        expect(mainModel.getEncryptedConfig()).andReturn(config).anyTimes();
    }

    private CryptoPackerPool getPackerPool() {
        CryptoPackerPoolFactory factory = new CryptoPackerPoolFactory();
        return new CryptoPackerPool(factory);
    }

    @After
    public void verifyBus() {
        busFake.verify();
    }

    @Test
    public void testConstructor() {
        expect(config.getAsString(SERVER_MQTT_HOST)).andReturn(HOST);
        expect(config.getAsString(SERVER_MQTT_PORT)).andReturn(PORT);
        expect(mainModel.getServer()).andReturn(SERVER);
        replay(mainModel, config);

        model = new ConnectionModel(mainModel, busFake.getBus(), new ExecutorFake(), getPackerPool());

        verify(mainModel, config);
        MqttConnectionPoolFactory factory = (MqttConnectionPoolFactory) model.connectionPool.getFactory();
        assertEquals(HOST, factory.getHost());
        assertEquals(PORT, factory.getPort());
        assertNotNull(model.carrier);
    }

    @Test
    public void testGenerateRandomMqttUsername() {
        HashSet<String> alreadyGeneratedUsernames = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            String username = ConnectionModel.generateRandomMqttUsername();

            assertEquals(MQTT_USERNAME_LENGTH, username.length());
            assertFalse(alreadyGeneratedUsernames.contains(username));

            alreadyGeneratedUsernames.add(username);
        }
    }

    @Test
    public void testStartHandshake() {
        instantiate();
        mockCarrier();

        model.carrier.deliverMessage(anyObject(byte[].class), anyObject(Participant.class));
        expectLastCall();
        assertNull(model.challenger);
        replay(model.carrier);

        model.startHandshake();

        verify(mainModel, model.carrier);
        assertNotNull(model.challenger);
    }

    @Test
    public void testHandleHandshakeResponse() {
        Message response = new Message(HANDSHAKE, SERVER);
        Message success = new Message(HANDSHAKE, SERVER);
        instantiate();
        mockCarrier();
        mockChallenger();

        model.challenger.consumeResponse(response);
        expectLastCall();
        expect(model.challenger.produceSuccess()).andReturn(success);
        model.carrier.deliverMessage(anyObject(byte[].class), anyObject(Participant.class));
        expectLastCall();
        
        replay(model.carrier, model.challenger);

        model.handleHandshakeResponse(response);

        verify(mainModel, model.carrier);
        assertEquals(UPDATE_CONNECTION_STATUS, busFake.getNextEvent());
    }

    private void instantiate() {
        expect(config.getAsString(SERVER_MQTT_HOST)).andReturn(HOST);
        expect(config.getAsString(SERVER_MQTT_PORT)).andReturn(PORT);
        expect(mainModel.getServer()).andReturn(SERVER).anyTimes();
        expect(mainModel.getUser()).andReturn(USER).anyTimes();
        replay(config, mainModel);

        model = new ConnectionModel(mainModel, busFake.getBus(), new ExecutorFake(), getPackerPool());
    }

    private void mockCarrier() {
        model.carrier = createMock(ClientCarrier.class);
    }

    private void mockChallenger() {
        model.challenger = createMock(HandshakeChallenger.class);
    }

}
