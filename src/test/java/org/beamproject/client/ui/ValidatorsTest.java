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

import org.beamproject.common.Participant;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidatorsTest {

    @Test(expected = TrimException.class)
    public void testIsUsernameValidOnTrimmableArgument() {
        Validators.isUsernameValid("  ");
    }

    @Test
    public void testIsUsernameValid() {
        assertFalse(Validators.isUsernameValid(null));
        assertFalse(Validators.isUsernameValid(""));
        assertTrue(Validators.isUsernameValid("Joe Average"));
    }

    @Test(expected = TrimException.class)
    public void testIsServerUrlValidOnTrimmableArgument() {
        assertTrue(Validators.isServerHttpUrlValid(" http://org"));
    }

    @Test
    public void testIsServerHttpUrlValid() {
        assertFalse(Validators.isServerHttpUrlValid(null));
        assertFalse(Validators.isServerHttpUrlValid(""));
        assertFalse(Validators.isServerHttpUrlValid("Joe Average"));
        assertTrue(Validators.isServerHttpUrlValid("http://org"));
        assertTrue(Validators.isServerHttpUrlValid("http://www.com"));
        assertTrue(Validators.isServerHttpUrlValid("http://localhost"));
    }

    @Test(expected = TrimException.class)
    public void testIsServerPublicKeyValidOnTrimmableArgument1() {
        assertFalse(Validators.isServerPublicKeyValid(" "));
    }

    @Test(expected = TrimException.class)
    public void testIsServerPublicKeyValidOnTrimmableArgument2() {
        Participant server = Participant.generate();
        String validPublicKey = server.getPublicKeyAsBase58();
        assertTrue(Validators.isServerPublicKeyValid(" " + validPublicKey));
    }

    @Test
    public void testIsServerPublicKeyValid() {
        Participant server = Participant.generate();
        String validPublicKey = server.getPublicKeyAsBase58();
        String invalidPublicKey1 = validPublicKey.replace('a', 'b');
        String invalidPublicKey2 = validPublicKey.replace('a', '=');
        String invalidPublicKey3 = validPublicKey + "hello";
        String invalidPublicKey4 = "beam:" + validPublicKey;

        assertFalse(Validators.isServerPublicKeyValid(null));
        assertFalse(Validators.isServerPublicKeyValid(""));
        assertFalse(Validators.isServerPublicKeyValid("z"));
        assertFalse(Validators.isServerPublicKeyValid(invalidPublicKey1));
        assertFalse(Validators.isServerPublicKeyValid(invalidPublicKey2));
        assertFalse(Validators.isServerPublicKeyValid(invalidPublicKey3));
        assertFalse(Validators.isServerPublicKeyValid(invalidPublicKey4));
        assertTrue(Validators.isServerPublicKeyValid(validPublicKey));
    }

}
