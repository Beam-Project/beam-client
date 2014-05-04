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

import static org.beamproject.client.App.getConfig;
import org.beamproject.client.ui.MainWindow;
import org.beamproject.common.Message;
import org.beamproject.common.Server;
import org.beamproject.common.Session;
import org.beamproject.common.User;
import org.beamproject.common.crypto.HandshakeChallenger;
import org.beamproject.common.network.MessageSender;
import org.beamproject.common.util.Executor;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ConnectorTaskTest {

    private ConnectorTask task;
    private Controller controller;
    private Model model;
    private MainWindow window;
    private Server server;
    private User user;
    private Executor executor;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        executor = createMock(Executor.class);
        AppTest.setAppExecutor(executor);
        server = Server.generate();
        user = User.generate();
        user.setServer(server);

        controller = new Controller();
        model = new Model();
        model.user = user;
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
        getConfig().setProperty("serverAddress", server.getAddress());

        assertNull(task.controller);
        assertNull(task.window);
        assertNull(task.model);
        assertNull(task.user);

        task.prepareEnvironment();

        assertSame(controller, task.controller);
        assertSame(window, task.window);
        assertSame(model, task.model);
        assertSame(user, task.user);
    }

    @Test
    public void testPrepareConnection() {
        task.user = user;
        task.prepareConnection();
        assertNotNull(task.sender);
    }

    @Test
    public void testPrepareHandshake() {
        task.model = model;
        assertNull(task.challenger);
        task.prepareHandshake();
        assertNotNull(task.challenger);
    }

    @Test
    public void testExecuteHandshake() {
        task.controller = controller;
        task.user = user;

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
    public void testActivateHeartbeatOnExistingHeartbeat() {
        prepareHeartbeatTest();

        HeartbeatTask existing = new HeartbeatTask();
        assertTrue(existing.doWork);
        model.heartbeatTask = existing;

        task.activateHeartbeat();
        assertFalse(existing.doWork);
        assertThat(model.heartbeatTask, not(equalTo(existing)));
        assertTrue(model.heartbeatTask.doWork);

        verify(executor);
    }

    @Test
    public void testActivateHeartbeatOnNewHeartbeat() {
        prepareHeartbeatTest();

        task.activateHeartbeat();
        assertTrue(model.heartbeatTask.doWork);

        verify(executor);
    }

    private void prepareHeartbeatTest() {
        getConfig().setProperty("serverAddress", server.getAddress());
        controller.session = new Session(server, "key".getBytes());
        executor.runAsync(anyObject(HeartbeatTask.class));
        expectLastCall();
        replay(executor);
    }

    @Test
    public void testDeactivateHeartbeat() {
        prepareHeartbeatTest();

        HeartbeatTask existing = new HeartbeatTask();
        assertTrue(existing.doWork);
        model.heartbeatTask = existing;

        task.deactivateHeartbeat();
        assertFalse(existing.doWork);
        assertNull(model.heartbeatTask);
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
        task.user = user;

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
