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
package org.beamproject.client.carrier;

import org.beamproject.client.model.ConnectionModel;
import org.beamproject.common.Participant;
import org.beamproject.common.Session;
import org.beamproject.common.carrier.MessageException;
import org.beamproject.common.crypto.HandshakeChallenger;
import org.beamproject.common.crypto.HandshakeResponder;
import static org.beamproject.common.message.Field.Cnt.HS_NONCE;
import static org.beamproject.common.message.Field.Cnt.HS_SIG;
import static org.beamproject.common.message.Field.Cnt.TYP;
import org.beamproject.common.message.Message;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.easymock.IAnswer;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class HandshakeResponseHandlerTest {

    private HandshakeResponseHandler handler;
    private ConnectionModel model;
    private Participant user, server;
    private HandshakeChallenger challenger;
    private HandshakeResponder responder;
    private Message challenge, response, success;

    @Before
    public void setUp() {
        model = createMock(ConnectionModel.class);
        server = Participant.generate();
        user = Participant.generate();
        handler = new HandshakeResponseHandler(model);

        setUpHandshakes();
    }

    private void setUpHandshakes() {
        challenger = new HandshakeChallenger(user);
        responder = new HandshakeResponder(server);
        challenge = challenger.produceChallenge(server);
        responder.consumeChallenge(challenge);
        response = responder.produceResponse();
    }

    @Test(expected = MessageException.class)
    public void testHandleOnMissingType() {
        response.getContent().remove(TYP.toString());
        success = handler.handle(response);
    }

    @Test(expected = MessageException.class)
    public void testHandleOnMissingNonce() {
        response.getContent().remove(HS_NONCE.toString());
        success = handler.handle(response);
    }

    @Test(expected = MessageException.class)
    public void testHandleOnMissingSignature() {
        response.getContent().remove(HS_SIG.toString());
        success = handler.handle(response);
    }

    @Test
    public void testHandleOnCreatingSession() {
        expect(model.getChallenger()).andReturn(challenger);
        model.setSession(anyObject(Session.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                Session session = (Session) getCurrentArguments()[0];
                assertArrayEquals(challenger.getSessionKey(), session.getKey());
                return null;
            }
        });
        replay(model);

        success = handler.handle(response);

        verify(model);
        responder.consumeSuccess(success);// expect no exception to be thrown    
    }

}
