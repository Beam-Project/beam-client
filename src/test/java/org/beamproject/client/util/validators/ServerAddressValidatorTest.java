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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ServerAddressValidatorTest {

    private final static String VALID_SERVER_ADDRESS = "beam:z7dBYZ1SNpTr8URsQVxzFg1hWvG33coDFBpxztn575Fadkatsp1wWRJS51s6ggctnXQymxkGNj7Hzo9rUVQwjvAoAfLv6ooC4WULsYWkduyiLAscmJikbDLZUN2rQNN3XQoFTUQdtcA7kxskitimpJYxv5dfJSCKPkBHKQ6cLwansBsxM6bW7CbQR4JrH1HVkQrqJxR4qQdsKiJ7gxJ6dqF48hRwrVxH28LSVFKL";
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
        test(server.getAddress().replaceFirst("M", ""), false);
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
