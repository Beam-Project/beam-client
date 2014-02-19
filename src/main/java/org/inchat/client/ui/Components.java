/*
 * Copyright (C) 2013, 2014 inchat.org
 *
 * This file is part of inchat-client.
 *
 * inchat-client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * inchat-client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.inchat.client.ui;

import java.awt.Component;
import org.inchat.client.App;
import org.inchat.common.util.Exceptions;

/**
 * This class is used to modify {@link Component}s.
 */
public class Components {

    private Components() {
        // Static access only.
    }

    /**
     * Sets the background color of the given {@link Component} to the default
     * color.
     *
     * @param component The component, which should have the default background
     * color.
     * @throws IllegalArgumentException If the argument is null.
     */
    public static void setDefalutBackground(Component component) {
        Exceptions.verifyArgumentNotNull(component);

        component.setBackground(App.DEFAULT_BACKGROUND);
    }

    /**
     * Sets the background color of the given {@link Component} to the error
     * color (red).
     *
     * @param component The component, which should have the red background
     * color.
     * @throws IllegalArgumentException If the argument is null.
     */
    public static void setErrorBackground(Component component) {
        Exceptions.verifyArgumentNotNull(component);

        component.setBackground(App.ERROR_BACKGROUND);
    }

}
