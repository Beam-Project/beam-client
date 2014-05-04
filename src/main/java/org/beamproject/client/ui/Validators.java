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
