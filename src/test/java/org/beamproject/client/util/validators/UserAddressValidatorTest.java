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
import org.beamproject.common.User;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class UserAddressValidatorTest {

    private final static String VALID_USER_ADDRESS = "beam:z7dBYZ1SNpTr8URsQVxzFg1hWvG33coDFBpxztn575Fadkatsp1wWRJS51s6ggctnXQymxkGNj7Hzo9rUVQwjvAoAfLv6ooC4WULsYWkduyiLAscmJikbDLZUN2rQNN3XQoFTUQdtcA7kxskitimpJYxv5dfJSCKPkBHKQ6cLwansBsxM6bW7CbQR4JrH1HVkQrqJxR4qQdsKiJ7gxJ6dqF48hRwrVxH28LSVFKL.AsQKAYgYHWAMduAQ4pidTyfzDfrW6EYJ7rpxaSF16NMWFEfrBu7a5JiQZuTrzd67TxsPM6CUdfHJ4CsmwkFGf61xyWd5mdrHJtngyQzWGAW6aNaXWi17VTEC7SieK4hPTgDvtDuQP3oC1ta87cwjHEmTzkD7hv6n6XmueY9eWWGwtaz96pEPCunVoe";
    private JTextField textField;
    private UserAddressValidator validator;
    private User user;

    @Before
    public void setUp() {
        textField = new JTextField();
        validator = new UserAddressValidator(textField);
        user = User.generate();
        user.setServer(Server.generate());
    }

    @Test
    public void testValidate() {
        test("", false);
        test(null, false);
        test("beam:", false);
        test(user.getAddress().replaceAll("beam:", "Beam:"), false);
        test(user.getAddress().replaceAll("beam", ""), false);
        test(user.getAddress().replaceFirst("M", ""), false);
        test(user.getAddress().replaceFirst("X", "X "), false);
        test(user.getAddress(), true);
        test(VALID_USER_ADDRESS, true);
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
