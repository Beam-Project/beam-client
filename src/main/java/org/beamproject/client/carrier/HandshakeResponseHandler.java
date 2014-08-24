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
import org.beamproject.common.Session;
import org.beamproject.common.crypto.Handshake;
import org.beamproject.common.crypto.HandshakeChallenger;
import org.beamproject.common.message.ContentFieldValidator;
import static org.beamproject.common.message.Field.Cnt.NONCE;
import static org.beamproject.common.message.Field.Cnt.PUBLIC_KEY;
import static org.beamproject.common.message.Field.Cnt.SIGNATURE;
import static org.beamproject.common.message.Field.Cnt.TYP;
import org.beamproject.common.message.HandshakeNonceValidator;
import org.beamproject.common.message.HandshakePublicKeyValidator;
import org.beamproject.common.message.HandshakeSignatureValidator;
import org.beamproject.common.message.Message;
import org.beamproject.common.message.MessageHandler;

/**
 * This {@link MessageHandler} is part of the {@link Handshake} procedure.
 * <p>
 * Handles messages of type: {@link TypeValue#HS_RESPONSE}
 */
public class HandshakeResponseHandler extends MessageHandler {

    private final ConnectionModel connectionModel;
    private HandshakeChallenger challenger;

    public HandshakeResponseHandler(ConnectionModel connectionModel) {
        super(new ContentFieldValidator(TYP, PUBLIC_KEY, NONCE, SIGNATURE),
                new HandshakePublicKeyValidator(),
                new HandshakeNonceValidator(),
                new HandshakeSignatureValidator());
        this.connectionModel = connectionModel;
    }

    @Override
    protected Message handleValidMessage() {
        challenger = connectionModel.getChallenger();
        challenger.consumeResponse(message);

        Message success = challenger.produceSuccess();
        Session session = new Session(challenger.getRemoteParticipant(), challenger.getSessionKey());
        connectionModel.setSession(session);

        return success;
    }

}
