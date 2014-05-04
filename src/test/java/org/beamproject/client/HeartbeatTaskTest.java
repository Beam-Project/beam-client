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

import java.net.MalformedURLException;
import java.net.URL;
import org.beamproject.common.Message;
import static org.beamproject.common.MessageField.ContentField.*;
import static org.beamproject.common.MessageField.ContentField.TypeValue.*;
import org.beamproject.common.Server;
import org.beamproject.common.Session;
import org.beamproject.common.User;
import org.beamproject.common.network.MessageSender;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class HeartbeatTaskTest {

    private final byte[] KEY = "key".getBytes();
    private HeartbeatTask task;
    private Controller controller;
    private Model model;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();

        model = new Model();
        model.user = User.generate();
        model.user.setServer(Server.generate());
        AppTest.setAppModel(model);
        controller = new Controller();
        controller.session = new Session(model.getUser().getServer(), KEY);
        AppTest.setAppController(controller);

        task = new HeartbeatTask();
    }

    @Test
    public void testConstructor() {
        assertSame(controller.session, task.session);
        assertTrue(task.doWork);
        assertNotNull(task.sender);
    }

    @Test
    public void testloadSender() {
        task.session = null;
        task.loadSession();
        assertSame(controller.session, task.session);
    }

    @Test
    public void testCreateSender() {
        task.createSender();
        assertNotNull(task.sender);
    }

    @Test
    public void testRun() throws MalformedURLException {
        task.sender = createMock(MessageSender.class);
        task.sender.send(anyObject(Message.class));
        expectLastCall().andDelegateTo(new MessageSender(new URL("http://beamproject.org"), model.getUser().getServer()) {

            @Override
            public void send(Message message) {
                assertSame(task.heartbeat, message);
                assertEquals(HEARTBEAT, message.getType());
                assertArrayEquals(KEY, message.getContent(HBKEY));
                assertEquals(24, new String(message.getContent(HBTS)).length());
                task.doWork = false;
            }
        });
        replay(task.sender);

        task.run();

        verify(task.sender);
    }

    @Test
    public void testShutdown() {
        task.shutdown();
        assertFalse(task.doWork);
    }

}
