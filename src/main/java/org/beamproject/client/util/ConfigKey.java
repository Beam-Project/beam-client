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
package org.beamproject.client.util;

import java.security.PublicKey;
import org.beamproject.client.model.MenuModel;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import org.beamproject.common.util.Config;

/**
 * The keys used to identify the different {@link Config} entries.
 */
public enum ConfigKey {

    /**
     * The user password to encrypt/decrypt the locally stored messages, the
     * private key and other sensible data.
     */
    PASSWORD,
    /**
     * The salt, used in combination with the password in order to derive an AES
     * key for local encryption.
     */
    SALT,
    /**
     * The username of the local user.
     */
    USERNAME,
    /**
     * The {@link PublicKey} of the local {@link User}, stored as bytes, X509
     * encoded.
     */
    USER_PUBLIC_KEY,
    /**
     * The {@link PrivateKey} of the local {@link User}, stored as bytes, PKCS8
     * encoded.
     */
    USER_PRIVATE_KEY,
    /**
     * The address of the {@link Server}, containing its public key and URL.
     */
    SERVER_ADDRESS,
    /**
     * Defines of what sources/senders messages will be accepted.<br />
     * Allowed values are the values of {@link MenuModel.AcceptedMessageSender}.
     */
    ACCEPTED_MESSAGE_SENDER
}
