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

import org.junit.Test;
import org.junit.Before;

/**
 * The tests of {@link ClipboardAccess} are very limited since in JUnit the
 * clipboard is not available or at least not correctly working.
 */
public class ClipboardAccessTest {

    ClipboardAccess access;

    @Before
    public void setUp() {
        access = new ClipboardAccess();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyTextToClipboardOnNull() {
        access.copyTextToClipboard(null);
    }

}
