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

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.logging.Logger;
import org.beamproject.client.BusFake;
import static org.beamproject.client.Event.UPDATE_CONNECTION_STATUS;
import org.beamproject.client.ExecutorFake;
import static org.beamproject.client.model.ConnectionModel.MQTT_USERNAME_LENGTH;
import org.beamproject.client.util.ConfigKey;
import org.beamproject.common.Participant;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.beamproject.common.carrier.ClientCarrier;
import org.beamproject.common.carrier.MqttConnectionPoolFactory;
import org.beamproject.common.crypto.CryptoPacker;
import org.beamproject.common.crypto.CryptoPackerPool;
import org.beamproject.common.crypto.CryptoPackerPoolFactory;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.beamproject.common.crypto.EncryptedConfig;
import org.beamproject.common.crypto.HandshakeChallenger;
import org.beamproject.common.crypto.HandshakeResponder;
import org.beamproject.common.message.Message;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.easymock.IAnswer;
import org.junit.After;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;

public class ConnectionModelTest {

    private final String HOST = "myLocalhost";
    private final int PORT = 2345;
    private final User USER = User.generate();
    private Server server;
    private MainModel mainModel;
    private BusFake busFake;
    private EncryptedConfig<ConfigKey> config;
    private ConnectionModel model;
    private HandshakeChallenger challenger;
    private HandshakeResponder responder;
    private Message challenge, response;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws MalformedURLException {
        server = new Server(new InetSocketAddress(HOST, PORT),
                new URL("http://" + HOST),
                EccKeyPairGenerator.generate());

        config = createMock(EncryptedConfig.class);
        mainModel = createMock(MainModel.class);
        busFake = new BusFake();
        model = new ConnectionModel(mainModel, busFake.getBus(), new ExecutorFake(), getPackerPool());
        model.log = Logger.getGlobal();
        challenger = new HandshakeChallenger(USER);
        responder = new HandshakeResponder(server);
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
    public void testSetUpConnectionPoolAndCarrier() {
        expect(mainModel.getServer()).andReturn(server).times(3);
        replay(mainModel);

        model.setUpConnectionPoolAndCarrier();

        verify(mainModel);
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
        assertNull(model.getChallenger());
        replay(model.carrier);

        model.startHandshake();

        verify(mainModel, model.carrier);
        assertNotNull(model.getChallenger());
    }

    @Test
    public void testHandleHandshakeResponse() {
        instantiate();
        mockCarrier();
        prepareHanshake();
        model.carrier.deliverMessage(anyObject(byte[].class), anyObject(Participant.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                byte[] ciphertext = (byte[]) getCurrentArguments()[0];
                Message plaintext = decrypt(ciphertext);
                Participant recipient = (Participant) getCurrentArguments()[1];

                responder.consumeSuccess(plaintext); // expect no exception
                assertArrayEquals(server.getPublicKeyAsBytes(), recipient.getPublicKeyAsBytes());

                return null;
            }
        });
        replay(model.carrier);

        model.consumeMessage(encrypt(response));

        verify(mainModel, model.carrier);
        assertSame(challenger.getSessionKey(), model.getSession().getKey());
        assertSame(server, model.getSession().getRemoteParticipant());
        assertEquals(UPDATE_CONNECTION_STATUS, busFake.getNextEvent());
    }

    private void instantiate() {
        expect(mainModel.getServer()).andReturn(server).anyTimes();
        expect(mainModel.getUser()).andReturn(USER).anyTimes();
        replay(config, mainModel);

        model = new ConnectionModel(mainModel, busFake.getBus(), new ExecutorFake(), getPackerPool());
        model.log = Logger.getGlobal();
    }

    private void mockCarrier() {
        model.carrier = createMock(ClientCarrier.class);
    }

    private void prepareHanshake() {
        challenge = challenger.produceChallenge(server);
        responder.consumeChallenge(challenge);
        response = responder.produceResponse();

        model.setChallenger(challenger);
    }

    private byte[] encrypt(Message message) {
        CryptoPacker packer = new CryptoPacker();
        return packer.packAndEncrypt(message);
    }

    private Message decrypt(byte[] ciphertext) {
        CryptoPacker packer = new CryptoPacker();
        return packer.decryptAndUnpack(ciphertext, server);
    }

}
