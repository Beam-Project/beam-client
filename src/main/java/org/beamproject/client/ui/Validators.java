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
package org.beamproject.client.ui;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.beamproject.common.Participant;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.beamproject.common.util.Base58;

/**
 * This class contains several static validators.
 */
public abstract class Validators {

    /**
     * Checks if a given username valid is or not. Make sure to trim the
     * {@code username} <b>before</b> using it with this method. If a String can
     * be trimmed, an exception is thrown (reason: if this method returns true,
     * the argument without changes should be a valid username).
     *
     * @param username username to check.
     * @return True, if the given username is valid, otherwise false.
     * @throws TrimException If the argument can be trimmed.
     */
    public static boolean isUsernameValid(String username) {
        return !isNullOrTrimmable(username) && !username.isEmpty();
    }

    /**
     * Checks if a given URL valid is or not for HTTP. Make sure to trim the
     * {@code url} <b>before</b> using it with this method. If a String can be
     * trimmed, an exception is thrown (reason: if this method returns true, the
     * argument without changes should be a valid URL).
     *
     * @param url URL to check.
     * @return True, if the given URL is valid, otherwise false.
     * @throws TrimException If the argument can be trimmed.
     */
    public static boolean isServerHttpUrlValid(String url) {
        if (isNullOrTrimmable(url)) {
            return false;
        }

        try {
            URL validUrl = new URL(url);
            URI validUri = validUrl.toURI(); // Needed to check if the URI is valid.

            return "http".equalsIgnoreCase(validUri.getScheme());
        } catch (MalformedURLException | URISyntaxException ex) {
            return false;
        }
    }

    /**
     * Checks if a given server public key is valid or not. Make sure to trim
     * the {@code serverPublicKey} <b>before</b> using it with this method. If a
     * String can be trimmed, an exception is thrown (reason: if this method
     * returns true, the argument without changes should be a valid public key).
     *
     * @param serverPublicKey String to check.
     * @return True, if the given public key is valid, otherwise false.
     * @throws TrimException If the argument can be trimmed.
     */
    public static boolean isServerPublicKeyValid(String serverPublicKey) {
        if (isNullOrTrimmable(serverPublicKey)) {
            return false;
        }

        try {
            byte[] publicKeyBytes = Base58.decode(serverPublicKey);
            Participant server = new Participant(EccKeyPairGenerator.fromPublicKey(publicKeyBytes));
            return server.getPublicKey().getEncoded() != null;
        } catch (IllegalStateException | IllegalArgumentException | NullPointerException ex) {
            return false;
        }
    }

    private static boolean isNullOrTrimmable(String tester) {
        if (tester == null) {
            return true;
        }

        String trimmed = tester.trim();

        if (!trimmed.equals(tester)) {
            throw new TrimException("The argument has to be trimmed first!");
        }

        return false;
    }
}
