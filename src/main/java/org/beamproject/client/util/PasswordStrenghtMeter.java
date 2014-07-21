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
package org.beamproject.client.util;

/**
 * Measures the strength of a given password. The implemented approach was
 * inspired by <a href="http://www.passwordmeter.com/">
 * http://www.passwordmeter.com/</a>, although not all deductions were adopted.
 */
public class PasswordStrenghtMeter {

    public static final char[] SYMOBOLS = ")!@#$%^&*()?".toCharArray();
    public static final int MINIMAL_LENGTH = 8;
    private static final int MINIMAL_NUMBER_OF_DIFFERENT_ITEMS = 3;

    /**
     * Checks the strength of the given {@code password}. 
     * @param password The password to check.
     * @return An index between 0 and 4 that represents the strength of the password:
     * <ul>
     * <li>0: very weak</li>
     * <li>1: weak</li>
     * <li>2: good</li>
     * <li>3: strong</li>
     * <li>4: very strong</li>
     * </ul>
     */
    public static int checkStrength(char[] password) {
        float score = 0;
        score += rewardNumberOfCharacters(password);
        score += rewardUpperCaseCharachters(password);
        score += rewardLowerCaseCharachters(password);
        score += rewardDigits(password);
        score += rewardSymbols(password);
        score += rewardMiddleNumbersOrSymbols(password);
        score += rewardRequirements(password);
        score += punishForLettersOnly(password);
        score += punishForDigitsOnly(password);

        score = score < 0 ? 0 : score;
        score = score > 100 ? 100 : score;

        return (int) Math.round(score / 25.0);
    }

    static int rewardNumberOfCharacters(char[] password) {
        return password.length * 4;
    }

    static int rewardUpperCaseCharachters(char[] password) {
        int occurrences = 0;

        for (int i = 0; i < password.length; i++) {
            if (Character.isUpperCase(password[i])) {
                occurrences++;
            }
        }

        return occurrences > 0
                ? (password.length - occurrences) * 2
                : 0;
    }

    static int rewardLowerCaseCharachters(char[] password) {
        int occurrences = 0;

        for (int i = 0; i < password.length; i++) {
            if (Character.isLowerCase(password[i])) {
                occurrences++;
            }
        }

        return occurrences > 0
                ? (password.length - occurrences) * 2
                : 0;
    }

    static int rewardDigits(char[] password) {
        int occurrences = 0;

        for (int i = 0; i < password.length; i++) {
            if (Character.isDigit(password[i])) {
                occurrences++;
            }
        }

        return occurrences * 4;
    }

    static int rewardSymbols(char[] password) {
        int occurrences = 0;

        for (int i = 0; i < password.length; i++) {
            for (char symbol : SYMOBOLS) {
                if (password[i] == symbol) {
                    occurrences++;
                    break;
                }
            }
        }

        return occurrences * 6;
    }

    static int rewardMiddleNumbersOrSymbols(char[] password) {
        int occurrences = 0;

        for (int i = 1; i < password.length - 1; i++) {
            for (char symbol : SYMOBOLS) {
                if (password[i - 1] != symbol
                        && password[i] == symbol
                        && password[i + 1] != symbol) {
                    occurrences++;
                    break;
                }
            }
        }

        return occurrences * 2;
    }

    static int rewardRequirements(char[] password) {
        boolean hasMinimalLength = password.length >= MINIMAL_LENGTH;
        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundDigits = false;
        boolean foundSymbols = false;

        int numberOfDiffrentItems = hasMinimalLength ? 1 : 0;

        for (int i = 0; i < password.length; i++) {
            if (!foundUpperCase && Character.isUpperCase(password[i])) {
                numberOfDiffrentItems++;
                foundUpperCase = true;
                continue;
            }

            if (!foundLowerCase && Character.isLowerCase(password[i])) {
                numberOfDiffrentItems++;
                foundLowerCase = true;
                continue;
            }

            if (!foundDigits && Character.isDigit(password[i])) {
                numberOfDiffrentItems++;
                foundDigits = true;
                continue;
            }

            if (!foundSymbols) {
                for (char symbol : SYMOBOLS) {
                    if (password[i] == symbol) {
                        numberOfDiffrentItems++;
                        foundSymbols = true;
                        break;
                    }
                }
            }
        }

        boolean hasMinimalNumberOfDifferentItems = numberOfDiffrentItems >= MINIMAL_NUMBER_OF_DIFFERENT_ITEMS;

        return hasMinimalLength && hasMinimalNumberOfDifferentItems
                ? numberOfDiffrentItems * 2
                : 0;
    }

    static int punishForLettersOnly(char[] password) {
        int numberOfLetters = 0;

        for (char character : password) {
            if (Character.isLetter(character)) {
                numberOfLetters++;
            }
        }

        return password.length == numberOfLetters
                ? -numberOfLetters
                : 0;
    }

    static int punishForDigitsOnly(char[] password) {
        int numberOfDigits = 0;

        for (char character : password) {
            if (Character.isDigit(character)) {
                numberOfDigits++;
            }
        }

        return password.length == numberOfDigits
                ? -numberOfDigits
                : 0;
    }

}
