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
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.experimental.Delegate;
import static org.beamproject.client.Event.*;
import static org.beamproject.client.util.ConfigKey.*;
import org.beamproject.common.Message;
import org.beamproject.common.carrier.ClientCarrier;
import org.beamproject.common.carrier.ClientCarrierImpl;
import org.beamproject.common.carrier.ClientCarrierModel;
import org.beamproject.common.carrier.MqttConnectionPool;
import org.beamproject.common.carrier.MqttConnectionPoolFactory;
import org.beamproject.common.crypto.CryptoPacker;
import org.beamproject.common.crypto.CryptoPackerPool;
import org.beamproject.common.crypto.HandshakeChallenger;
import org.beamproject.common.util.Executor;
import org.beamproject.common.util.Task;

public class ConnectionModel implements ClientCarrierModel {

    final static int MQTT_USERNAME_LENGTH = 12;
    private final static String MQTT_USERNAME_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static String MQTT_SUBSCRIBER_TOPIC_PREFIX = "out/";
    private final static String MQTT_PUBLISHER_TOPIC = "in";
    private final MainModel model;
    private final EventBus bus;
    private final Executor executor;
    private final CryptoPackerPool packerPool;
    @Delegate
    ClientCarrier carrier;
    MqttConnectionPool connectionPool;
    HandshakeChallenger challenger;

    @Inject
    public ConnectionModel(MainModel model,
            EventBus bus,
            Executor executor,
            CryptoPackerPool packerPool) {
        this.model = model;
        this.bus = bus;
        this.executor = executor;
        this.packerPool = packerPool;

        setUpConnectionPoolAndCarrier();
    }

    private void setUpConnectionPoolAndCarrier() {
        String host = model.getEncryptedConfig().getAsString(SERVER_MQTT_HOST);
        String port = model.getEncryptedConfig().getAsString(SERVER_MQTT_PORT);
        String mqttUsername = generateRandomMqttUsername();
        String subscriberTopoic = MQTT_SUBSCRIBER_TOPIC_PREFIX + mqttUsername;

        MqttConnectionPoolFactory factory = new MqttConnectionPoolFactory(host, port, mqttUsername, subscriberTopoic);

        connectionPool = new MqttConnectionPool(factory);
        carrier = new ClientCarrierImpl(this, executor, connectionPool);
        carrier.bindParticipantToTopic(model.getServer(), MQTT_PUBLISHER_TOPIC);
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

    public void startHandshake() {
        challenger = new HandshakeChallenger(model.getUser());
        sendMessage(challenger.produceChallenge(model.getServer()));
    }

    void handleHandshakeResponse(Message response) {
        challenger.consumeResponse(response);
        sendMessage(challenger.produceSuccess());

        bus.post(UPDATE_CONNECTION_STATUS);
    }

    @Override
    public void consumeMessage(final byte[] ciphertext) {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                CryptoPacker packer = null;

                try {
                    packer = packerPool.borrowObject();
                    Message message = packer.decryptAndUnpack(ciphertext, model.getUser());
                    routeMessage(message);
                } catch (Exception ex) {
                    Logger.getLogger(ConnectionModel.class.getName()).log(Level.WARNING,
                            "Could not handle an incoming message: {0}", ex.getMessage());
                } finally {
                    packerPool.returnObject(packer);
                }
            }
        });
    }

    private void routeMessage(Message message) {
        switch (message.getType()) {
            case HANDSHAKE:
                handleHandshakeResponse(message);
                break;
            default:
                Logger.getLogger(ConnectionModel.class.getName()).log(Level.INFO,
                        "Received a message of unknown type - ignore it.");
        }
    }

    private void sendMessage(final Message message) {
        executor.runAsync(new Task() {
            @Override
            public void run() {
                CryptoPacker packer = null;

                try {
                    packer = packerPool.borrowObject();
                    byte[] ciphertext = packer.packAndEncrypt(message);
                    carrier.deliverMessage(ciphertext, message.getRecipient());
                } catch (Exception ex) {
                    Logger.getLogger(ConnectionModel.class.getName()).log(Level.WARNING,
                            "Could not send a message: {0}", ex.getMessage());
                } finally {
                    packerPool.returnObject(packer);
                }
            }
        });
    }
}
