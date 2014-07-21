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

import javax.swing.text.JTextComponent;

/**
 * Validates the text of a {@link JTextComponent} against a given regular
 * expression.
 */
public class RegexValidator extends Validator {

    private final String regex;

    /**
     * Creates a new instance of {@link RegexValidator} to validate the given
     * {@link JTextComponent} against the given {@code regex}.
     *
     * @param component The component to validate.
     * @param regex The regular expression against which has to be validated.
     */
    public RegexValidator(JTextComponent component, String regex) {
        super(component);
        this.regex = regex;
    }

    @Override
    protected boolean isValid(String text) {
        return text.matches(regex);
    }

}
