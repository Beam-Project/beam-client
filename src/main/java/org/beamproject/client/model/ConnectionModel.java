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

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import java.security.SecureRandom;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import static org.beamproject.client.Event.UPDATE_CONNECTION_STATUS;
import org.beamproject.client.carrier.HandshakeResponseHandler;
import org.beamproject.common.Session;
import org.beamproject.common.carrier.ClientCarrier;
import org.beamproject.common.carrier.ClientCarrierImpl;
import org.beamproject.common.carrier.ClientCarrierModel;
import org.beamproject.common.carrier.MqttConnectionPool;
import org.beamproject.common.carrier.MqttConnectionPoolFactory;
import org.beamproject.common.crypto.CryptoPacker;
import org.beamproject.common.crypto.CryptoPackerPool;
import org.beamproject.common.crypto.HandshakeChallenger;
import org.beamproject.common.message.Message;
import org.beamproject.common.util.Executor;
import org.beamproject.common.util.Task;

public class ConnectionModel implements ClientCarrierModel {

    @Inject
    Logger log;
    final static int MQTT_USERNAME_LENGTH = 12;
    private final static String MQTT_USERNAME_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static String MQTT_SUBSCRIBER_TOPIC_PREFIX = "out/";
    private final static String MQTT_PUBLISHER_TOPIC_PREFIX = "in/";
    private final MainModel model;
    private final EventBus bus;
    private final Executor executor;
    private final CryptoPackerPool packerPool;
    @Delegate
    ClientCarrier carrier;
    MqttConnectionPool connectionPool;
    String mqttUsername;
    @Getter
    @Setter
    private HandshakeChallenger challenger;
    @Getter
    @Setter
    private Session session;

    @Inject
    public ConnectionModel(MainModel model, EventBus bus, Executor executor, CryptoPackerPool packerPool) {
        this.model = model;
        this.bus = bus;
        this.executor = executor;
        this.packerPool = packerPool;
    }

    public void setUpConnectionPoolAndCarrier() {
        String host = model.getServer().getMqttAddress().getHostString();
        int port = model.getServer().getMqttAddress().getPort();
        mqttUsername = generateRandomMqttUsername();
        String subscriberTopic = MQTT_SUBSCRIBER_TOPIC_PREFIX + mqttUsername;
        String publisherTopic = MQTT_PUBLISHER_TOPIC_PREFIX + mqttUsername;

        log.log(INFO, "Connecting: {0}:{1} with username ''{2}'' in topic ''{3}''", new Object[]{host, port, mqttUsername, subscriberTopic});
        MqttConnectionPoolFactory factory = new MqttConnectionPoolFactory(host, port, mqttUsername, subscriberTopic);

        connectionPool = new MqttConnectionPool(factory);
        carrier = new ClientCarrierImpl(this, executor, connectionPool);
        carrier.bindParticipantToTopic(model.getServer(), publisherTopic);
    }

    static String generateRandomMqttUsername() {
        final int alphabetLength = MQTT_USERNAME_ALPHABET.length();
        SecureRandom random = new SecureRandom();
        StringBuilder randomName = new StringBuilder();

        for (int i = 0; i < MQTT_USERNAME_LENGTH; i++) {
            char nextSymbol = MQTT_USERNAME_ALPHABET.charAt(random.nextInt(alphabetLength));
            randomName.append(nextSymbol);
        }

        return randomName.toString();
    }

    public void startAsyncReceiving() {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                startReceiving();
            }
        });
    }

    public void startHandshake() {
        challenger = new HandshakeChallenger(model.getUser());
        encryptAndSend(challenger.produceChallenge(model.getServer()), mqttUsername);
    }

    @Override
    public void consumeMessage(final byte[] ciphertext, final String username) {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                CryptoPacker packer = null;

                try {
                    packer = packerPool.borrowObject();
                    Message message = packer.decryptAndUnpack(ciphertext, model.getUser());
                    Message response = produceResponse(message);

                    if (response != null) {
                        encryptAndSend(message, mqttUsername);
                    }
                } catch (Exception ex) {
                    log.log(WARNING, "Could not handle incoming message: {0}", ex.getMessage());
                } finally {
                    packerPool.returnObject(packer);
                }
            }
        });
    }

    private Message produceResponse(Message message) {

        switch (message.getType()) {

            case HS_RESPONSE:
                HandshakeResponseHandler responseHandler = new HandshakeResponseHandler(this);
                bus.post(UPDATE_CONNECTION_STATUS);
                return responseHandler.handle(message);

            default:
                log.log(INFO, "Received message of unknown type; ignore it.");
        }

        return null;
    }

    @Override
    public void encryptAndSend(final Message message, final String topic) {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                CryptoPacker packer = null;

                try {
                    packer = packerPool.borrowObject();
                    byte[] ciphertext = packer.packAndEncrypt(message);
                    carrier.deliverMessage(ciphertext, topic);
                } catch (Exception ex) {
                    log.log(WARNING, "Could not send message: {0}", ex.getMessage());
                } finally {
                    packerPool.returnObject(packer);
                }
            }
        });
    }

    public boolean isConnected() {
        return carrier != null
                && session != null
                && session.getKey() != null;
    }

}
