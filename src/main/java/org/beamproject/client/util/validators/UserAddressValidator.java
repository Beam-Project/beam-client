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
import org.beamproject.common.User;

/**
 * Validates the text of a {@link JTextComponent} against the rules of a
 * {@link User} address.
 */
public class UserAddressValidator extends Validator {

    /**
     * Creates a new instance of {@link UserAddressValidator} to validate the
     * given {@link JTextComponent} against the rules of a {@link User} address.
     *
     * @param component The component to validate.
     */
    public UserAddressValidator(JTextComponent component) {
        super(component);
    }

    @Override
    protected boolean isValid(String address) {
        try {
            User user = new User(address);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

}
