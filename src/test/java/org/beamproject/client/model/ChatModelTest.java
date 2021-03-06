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
package org.beamproject.client.model;

import org.beamproject.client.BusFake;
import static org.beamproject.client.Event.SHOW_ADD_CONTACT_LAYER;
import static org.beamproject.client.Event.SHOW_MAIN_WINDOW;
import org.beamproject.client.ExecutorFake;
import org.beamproject.client.util.ConfigKey;
import org.beamproject.common.crypto.EncryptedConfig;
import org.beamproject.common.util.Config;
import static org.easymock.EasyMock.createMock;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ChatModelTest {

    private MainModel mainModel;
    private BusFake busFake;
    private Config<ConfigKey> config;
    private ExecutorFake executorFake;
    private ChatModel model;
    private EncryptedConfig<ConfigKey> encryptedConfig;

    @Before
    public void setUp() {
        mainModel = createMock(MainModel.class);
        busFake = new BusFake();
        model = new ChatModel(mainModel, busFake.getBus());
    }

    @After
    public void verifyBus() {
        busFake.verify();
    }

    @Test
    public void testShowAddContactChangeLayer() {
        model.showAddContactLayer();
        assertEquals(SHOW_ADD_CONTACT_LAYER, busFake.getNextEvent());
    }

    @Test
    public void testProcessAddContact() {
//todo
    }

    @Test
    public void testAbortAddContact() {
        model.abortAddContact();
        assertEquals(SHOW_MAIN_WINDOW, busFake.getNextEvent());
    }

}
