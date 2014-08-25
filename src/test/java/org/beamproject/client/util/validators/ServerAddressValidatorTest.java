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
package org.beamproject.client.util.validators;

import javax.swing.JTextField;
import org.beamproject.common.Server;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ServerAddressValidatorTest {

    private final static String VALID_SERVER_ADDRESS = "beam:Vsr1hpddfeaDHWHawwg4V4PSngWtLuUX8PQHBKBRkrYQvpTPLFZqQWgcWfFKbV1GJTPjRwPMS22kxCxnpvXHLEmNRXQGvSLMLZjYLXXA37wJEVRDHAGL1D1Ri7VmMq5H6LoSK941zshMfyUhSUrAEc7miyDaXLtMstNuvhPyGVWWFmp6qcdrvYhP9vrHDXKjY2XeJaCCph1zEe6yBvwjhNAyvsTaVK7UJrAeeyYU7GfaDMN";
    private JTextField textField;
    private ServerAddressValidator validator;
    private Server server;

    @Before
    public void setUp() {
        textField = new JTextField();
        validator = new ServerAddressValidator(textField);
        server = Server.generate();
    }

    @Test
    public void testValidate() {
        test("", false);
        test(null, false);
        test("beam:", false);
        test(server.getAddress().replaceAll("beam:", "Beam:"), false);
        test(server.getAddress().replaceAll("beam", ""), false);
        test(server.getAddress().replaceFirst("M", "mmm"), false);
        test(server.getAddress().replaceFirst("X", "X "), false);
        test(server.getAddress(), true);
        test(VALID_SERVER_ADDRESS, true);
    }

    private void test(String text, boolean assertTrue) {
        textField.setText(text);
        validator.validate();

        if (assertTrue) {
            assertTrue(validator.isValid());
        } else {
            assertFalse(validator.isValid());
        }
    }

}
