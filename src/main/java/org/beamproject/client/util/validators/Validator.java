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
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import lombok.Getter;

/**
 * This {@link Validator} allows to validate {@link JTextComponent}s against
 * given rules, implemented by its subclasses.
 *
 * @see RegexValidator
 * @see ServerAddressValidator
 * @see UserAddressValidator
 */
public abstract class Validator {

    public final static Color DEFAULT_BACKGROUND = UIManager.getColor("TextField.background");
    public final static Color ERROR_BACKGROUND = new Color(141, 24, 23);
    protected final JTextComponent component;
    @Getter
    private boolean isValid = false;
    @Getter
    private String validatedText;

    protected Validator(JTextComponent component) {
        this.component = component;
    }

    /**
     * Validates the configured {@link JTextComponent}. When the validation
     * fails, the background color will be set to red.
     * <p>
     * The result of the validation may be evaluated via {@link isValid()} and
     * the validated text via {@link getValidatedText()}.
     */
    public final void validate() {
        validatedText = component.getText().trim();

        isValid = isValid(validatedText);

        component.setText(validatedText);
        component.setBackground(isValid ? DEFAULT_BACKGROUND : ERROR_BACKGROUND);
    }

    protected abstract boolean isValid(String text);

}
