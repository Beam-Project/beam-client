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

import java.net.URL;
import static org.beamproject.client.App.getConfig;
import org.beamproject.client.ui.MainWindow;
import org.beamproject.common.Message;
import org.beamproject.common.Participant;
import org.beamproject.common.Session;
import org.beamproject.common.crypto.HandshakeChallenger;
import org.beamproject.common.network.MessageSender;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ConnectorTaskTest {

    private final String SERVER_URL_AS_STRING = "http://srv.beamproject.org";
    private ConnectorTask task;
    private Controller controller;
    private Model model;
    private MainWindow window;
    private Participant user, server;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        user = Participant.generate();
        server = Participant.generate();

        controller = new Controller();
        model = new Model();
        model.user = user;
        model.server = server;
        window = createMock(MainWindow.class);
        controller.mainWindow = window;
        AppTest.setAppController(controller);
        AppTest.setAppModel(model);

        task = new ConnectorTask(true);
    }

    @Test
    public void testConstructor() {
        assertTrue(task.doConnect);
        task = new ConnectorTask(false);
        assertFalse(task.doConnect);
    }

    @Test
    public void testPrepareEnvironment() {
        getConfig().setProperty("serverUrl", SERVER_URL_AS_STRING);

        assertNull(task.controller);
        assertNull(task.window);
        assertNull(task.model);
        assertNull(task.server);
        assertNull(task.serverUrlAsString);

        task.prepareEnvironment();

        assertSame(controller, task.controller);
        assertSame(window, task.window);
        assertSame(model, task.model);
        assertSame(server, task.server);
        assertEquals(SERVER_URL_AS_STRING, task.serverUrlAsString);
    }

    @Test
    public void testPrepareConnection() {
        task.user = user;
        task.serverUrlAsString = SERVER_URL_AS_STRING;
        task.prepareConnection();
        assertEquals(task.serverUrl.toString(), SERVER_URL_AS_STRING);
        assertNotNull(task.sender);
    }

    @Test
    public void testPrepareHandshake() {
        task.model = model;
        assertNull(task.challenger);
        task.prepareHandshake();
        assertNotNull(task.challenger);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetServerUrlOnNull() {
        task.getServerUrl(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetServerUrlOnInvalidString() {
        task.getServerUrl("hallo");
    }

    @Test
    public void testGetServerUrl() {
        URL url = task.getServerUrl(SERVER_URL_AS_STRING);
        assertEquals(SERVER_URL_AS_STRING, url.toString());
    }

    @Test
    public void testExecuteHandshake() {
        task.controller = controller;
        task.server = server;

        Message challenge = createMock(Message.class);
        Message response = createMock(Message.class);
        Message success = createMock(Message.class);

        task.challenger = createMock(HandshakeChallenger.class);
        task.sender = createMock(MessageSender.class);

        expect(task.challenger.produceChallenge(server)).andReturn(challenge);
        expect(task.sender.sendAndReceive(challenge)).andReturn(response);
        task.challenger.consumeResponse(response);
        expectLastCall();
        expect(task.challenger.produceSuccess()).andReturn(success);
        task.sender.send(success);
        expectLastCall();
        expect(task.challenger.getSessionKey()).andReturn("key".getBytes());
        replay(task.challenger, task.sender);

        task.executeHandshake();

        assertArrayEquals("key".getBytes(), controller.getSession().getKey());
        assertSame(server, controller.getSession().getRemoteParticipant());
        verify(task.challenger, task.sender);
    }

    @Test
    public void testDisconnectOnNull() {
        task.controller = controller;
        task.disconnect(); // Nothing should happen when the session is null.
    }

    @Test
    public void testDisconnect() {
        Session session = new Session(server, "key".getBytes());
        controller.session = session;
        task.controller = controller;
        task.server = server;

        task.sender = createMock(MessageSender.class);
        task.sender.send(EasyMock.anyObject(Message.class));
        expectLastCall();
        replay(task.sender);

        task.disconnect();

        assertNull(controller.session);
        assertNull(session.getRemoteParticipant());

        for (byte b : session.getKey()) {
            assertEquals((byte) 0, b);
        }

        verify(task.sender);
    }

    @Test
    public void testUpdateUserInterface() {
        task.window = window;
        window.labelStatusButtonAsConnected(true);
        expectLastCall();
        window.labelStatusButtonAsConnected(false);
        expectLastCall();
        replay(window);

        task.doConnect = true;
        task.updateUserInterface();

        task.doConnect = false;
        task.updateUserInterface();

        verify(window);
    }
}
