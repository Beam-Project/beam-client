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

import static org.beamproject.client.util.PasswordStrenghtMeter.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordStrenghtMeterTest {

    private int score;

    @Test
    public void testCheckStrengthOnCappingScore() {
        score = checkStrength("a325a@#aswE$231$!r242341@#%^2e4wr22533t".toCharArray());
        assertEquals(4, score);
    }

    @Test
    public void testCheckStrength() {
        score = checkStrength("a".toCharArray());
        assertEquals(0, score);

        score = checkStrength("a?1".toCharArray());
        assertEquals(1, score);

        score = checkStrength("1234".toCharArray());
        assertEquals(1, score);

        score = checkStrength("d2ea3cb".toCharArray());
        assertEquals(2, score);

        score = checkStrength("asdfAs?9".toCharArray());
        assertEquals(3, score);

        score = checkStrength("ad#acAsld".toCharArray());
        assertEquals(3, score);

        score = checkStrength("asdfAs?9a3".toCharArray());
        assertEquals(4, score);
    }

    @Test
    public void testRewardStrengthOnNumberOfCharachters() {
        score = rewardNumberOfCharacters("".toCharArray());
        assertEquals(0, score);

        score = rewardNumberOfCharacters("a".toCharArray());
        assertEquals(4, score);

        score = rewardNumberOfCharacters("abc".toCharArray());
        assertEquals(12, score);
    }

    @Test
    public void testRewardUpperCaseCharachters() {
        score = rewardUpperCaseCharachters("aaaaaa".toCharArray());
        assertEquals(0, score);
        
        score = rewardUpperCaseCharachters("".toCharArray());
        assertEquals(0, score);

        score = rewardUpperCaseCharachters("A".toCharArray());
        assertEquals(0, score);

        score = rewardUpperCaseCharachters("aA".toCharArray());
        assertEquals(2, score);

        score = rewardUpperCaseCharachters("aAa".toCharArray());
        assertEquals(4, score);
    }

    @Test
    public void testRewardLowerCaseCharachters() {
        score = rewardLowerCaseCharachters("ASDFASDF".toCharArray());
        assertEquals(0, score);
        
        score = rewardLowerCaseCharachters("".toCharArray());
        assertEquals(0, score);

        score = rewardLowerCaseCharachters("a".toCharArray());
        assertEquals(0, score);

        score = rewardLowerCaseCharachters("Aa".toCharArray());
        assertEquals(2, score);

        score = rewardLowerCaseCharachters("AaA".toCharArray());
        assertEquals(4, score);
    }

    @Test
    public void testRewardDigits() {
        score = rewardDigits("".toCharArray());
        assertEquals(0, score);

        score = rewardDigits("a".toCharArray());
        assertEquals(0, score);

        score = rewardDigits("a1".toCharArray());
        assertEquals(4, score);

        score = rewardDigits("a11".toCharArray());
        assertEquals(8, score);
    }

    @Test
    public void testRewardSymbols() {
        score = rewardSymbols("".toCharArray());
        assertEquals(0, score);

        score = rewardSymbols("?".toCharArray());
        assertEquals(6, score);

        score = rewardSymbols("?a".toCharArray());
        assertEquals(6, score);

        score = rewardSymbols("?*a".toCharArray());
        assertEquals(12, score);
    }

    @Test
    public void testRewardMiddleNumbersOrSymbols() {
        score = rewardMiddleNumbersOrSymbols("".toCharArray());
        assertEquals(0, score);

        score = rewardMiddleNumbersOrSymbols("a?".toCharArray());
        assertEquals(0, score);

        score = rewardMiddleNumbersOrSymbols("a?a".toCharArray());
        assertEquals(2, score);

        score = rewardMiddleNumbersOrSymbols("a?a*a".toCharArray());
        assertEquals(4, score);
    }

    @Test
    public void testRewardRequirements() {
        score = rewardRequirements("a".toCharArray());
        assertEquals(0, score);

        score = rewardRequirements("a?".toCharArray());
        assertEquals(0, score);

        score = rewardRequirements("a?A".toCharArray());
        assertEquals(0, score);

        score = rewardRequirements("a?a1aaaa".toCharArray());
        assertEquals(8, score);

        score = rewardRequirements("a?a1aaaaA".toCharArray());
        assertEquals(10, score);
    }

    @Test
    public void testPunishForLettersOnly() {
        score = punishForLettersOnly("11651".toCharArray());
        assertEquals(0, score);

        score = punishForLettersOnly("a?".toCharArray());
        assertEquals(0, score);

        score = punishForLettersOnly("a?A".toCharArray());
        assertEquals(0, score);

        score = punishForLettersOnly("aaaa".toCharArray());
        assertEquals(-4, score);

        score = punishForLettersOnly("aaaAa".toCharArray());
        assertEquals(-5, score);
    }

    @Test
    public void testPunishForDigitsOnly() {
        score = punishForDigitsOnly("asdfasdf".toCharArray());
        assertEquals(0, score);

        score = punishForDigitsOnly("a?".toCharArray());
        assertEquals(0, score);

        score = punishForDigitsOnly("a?1".toCharArray());
        assertEquals(0, score);

        score = punishForDigitsOnly("1234".toCharArray());
        assertEquals(-4, score);

        score = punishForDigitsOnly("11111".toCharArray());
        assertEquals(-5, score);
    }

}
