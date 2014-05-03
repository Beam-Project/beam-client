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

import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.net.URL;
import static org.beamproject.client.App.getConfig;
import static org.beamproject.client.App.getController;
import org.beamproject.common.Message;
import static org.beamproject.common.MessageField.ContentField.*;
import static org.beamproject.common.MessageField.ContentField.TypeValue.*;
import org.beamproject.common.Session;
import org.beamproject.common.network.MessageSender;
import org.beamproject.common.util.ExecutorException;
import org.beamproject.common.util.Task;

/**
 * This task can is used to ensure the connection and session to the keep alive.
 */
public class HeartbeatTask implements Task {

    public final static long SLEEP_TIME_BETWEEN_HEARTBEATS_IN_MILLISECONDS = 2000;
    MessageSender sender;
    Message heartbeat;
    Session session;
    boolean doWork = true;

    public HeartbeatTask() {
        loadSession();
        createSender();
    }

    void loadSession() {
        session = getController().getSession();
    }

    void createSender() {
        try {
            URL serverUrl = new URL(getConfig().serverUrl());
            sender = new MessageSender(serverUrl, session.getRemoteParticipant());
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("The server address is not valid: " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        while (doWork) {
            try {
                heartbeat = new Message(HEARTBEAT, session.getRemoteParticipant());
                heartbeat.putContent(HSPUBKEY, session.getKey());
                sender.send(heartbeat);
                sleep(SLEEP_TIME_BETWEEN_HEARTBEATS_IN_MILLISECONDS);
            } catch (InterruptedException ex) {
                throw new ExecutorException("The heartbeat thread was interrupted during sleep: " + ex.getMessage());
            }
        }
    }

    /**
     * Terminates this {@link HeartbeatTask}. In detail, this thread will not
     * start another keep-alive transmission, but a currently running
     * transmission will not be interrupted.
     */
    public void shutdown() {
        doWork = false;
    }

}
