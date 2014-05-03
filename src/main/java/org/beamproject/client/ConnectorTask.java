/*
 * Copyright (C) 2013, 2014 beamproject.org
 *
 * This file is part of beam-common.
 *
 * beam-common is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * beam-common is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beamproject.client;

import org.beamproject.common.network.MessageSender;
import java.net.MalformedURLException;
import java.net.URL;
import static org.beamproject.client.App.getConfig;
import static org.beamproject.client.App.getController;
import static org.beamproject.client.App.getModel;
import org.beamproject.client.ui.Dialogs;
import org.beamproject.client.ui.MainWindow;
import org.beamproject.common.Message;
import org.beamproject.common.Participant;
import org.beamproject.common.Session;
import org.beamproject.common.crypto.Handshake;
import org.beamproject.common.crypto.HandshakeChallenger;
import org.beamproject.common.util.Executor;
import org.beamproject.common.util.Task;

/**
 * Allows to establish or invalidate a connection within this and another
 * {@link Participant}.
 * <p>
 * If the connection is created, a new {@link Session} is negotiated. Otherwise,
 * an existing {@link Session} will be invalidated.
 *
 * @see Executor
 */
public class ConnectorTask implements Task {

    Controller controller;
    Model model;
    MainWindow window;
    URL serverUrl;
    String serverUrlAsString;
    Participant user, server;
    HandshakeChallenger challenger;
    MessageSender sender;
    Message challenge, response, success;
    boolean doConnect;

    /**
     * Creates a connector to establish a authenticated connection to a other
     * participant.
     *
     * @param doConnect If true, the connection should be established, otherwise
     * it should be terminated.
     */
    public ConnectorTask(boolean doConnect) {
        this.doConnect = doConnect;
    }

    @Override
    public void run() {
        prepareEnvironment();

        if (controller.isServerUrlAvailable(serverUrlAsString) && server != null) {
            prepareConnection();

            if (doConnect) {
                prepareHandshake();
                executeHandshake();
            } else {
                disconnect();
            }

            updateUserInterface();
        } else {
            Dialogs.showConfigurationIncomplete();
        }

        window.enableStatusButton();
    }

    void prepareEnvironment() {
        controller = getController();
        window = controller.getMainWindow();
        window.disableStatusButton();
        model = getModel();
        user = model.getUser();
        server = model.getServer();
        serverUrlAsString = getConfig().serverUrl();
    }

    void prepareConnection() {
        serverUrl = getServerUrl(serverUrlAsString);
        sender = new MessageSender(serverUrl, user);
    }

    void prepareHandshake() {
        challenger = new HandshakeChallenger(model.getUser());
    }

    URL getServerUrl(String serverUrl) {
        try {
            return new URL(serverUrl);
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("The given URL is not valid: " + ex.getMessage());
        }
    }

    void executeHandshake() {
        challenge = challenger.produceChallenge(server);
        response = sender.sendAndReceive(challenge);
        challenger.consumeResponse(response);
        success = challenger.produceSuccess();
        sender.send(success);
        controller.session = new Session(server, challenger.getSessionKey());
    }

    void disconnect() {
        Session session = controller.getSession();

        if (session != null) {
            Message invalidateMessage = Handshake.getInvalidate(server, session.getKey());
            sender.send(invalidateMessage);
            session.invalidateSession();
            controller.setSession(null);
        }
    }

    void updateUserInterface() {
        window.labelStatusButtonAsConnected(doConnect);
    }
}
