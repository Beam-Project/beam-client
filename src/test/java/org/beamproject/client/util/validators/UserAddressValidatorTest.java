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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class UserAddressValidatorTest {

    private final static String VALID_USER_ADDRESS = "beam:Vsr1hpddfeaDHWHawwg4V4PSngWtLuUX8PQHBKBRkrZEfapVAAhiNEg779573eHk9LsJNK27PzzDrbaDgwpM9mX9FKJ8HeNwUzNSQUJYwSAXYGUZ8oh1fMQaSCKMRSmGqqEW3vobf2H3y6ZYL9Yo8hRmfd73jmsMtvKRxmNLN36UjSMBFeXQ9WPxW7koBWTPsSgBF6carWgdyRUhes9ojsmVo78vJVbJYVWR99aktQSw9ik.kZTuB8qX5qiJ5sEUoscjx595YvVVQ6m8ZGvgJaQoQhP88ryZVY9SBMiRCLBx28sYi45dxXRGruUrwczzniiNLpbQgrBJGiWjxBznABcJtLts4HcsZ6r3spQ54SoFzM1qeJT8oZ4WqmA2xzAPZT6cz3qexzXSPypaXBVHESyaom415gbzPf5gVuAYcvV";
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
