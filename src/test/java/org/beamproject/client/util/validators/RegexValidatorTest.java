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

import java.awt.Color;
import javax.swing.JTextField;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class RegexValidatorTest {

    private RegexValidator validator;
    private JTextField textfield;

    @Before
    public void setUp() {
        textfield = new JTextField(" abc ");
        validator = new RegexValidator(textfield, "abc");
    }

    @Test
    public void testValidatorOnEmptyValue() {
        textfield.setText("");
        validator.validate();
        assertFalse(validator.isValid());
    }

    @Test
    public void testValidatorOnTrimming() {
        validator.validate();
        assertTrue(validator.isValid());
        assertEquals("abc", textfield.getText());
    }

    @Test
    public void testValidatorOnBackgrond() {
        Color defaultColor = textfield.getBackground();

        validator.validate();
        assertEquals(defaultColor, textfield.getBackground());

        textfield.setText("not matching text");
        validator.validate();
        assertEquals(Validator.ERROR_BACKGROUND, textfield.getBackground());

        textfield.setText("abc");
        validator.validate();
        assertEquals(defaultColor, textfield.getBackground());
    }

}
