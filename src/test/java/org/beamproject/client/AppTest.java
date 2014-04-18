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
package org.beamproject.client;

import org.beamproject.client.ui.MainWindow;
import org.beamproject.common.Participant;
import org.beamproject.common.util.ConfigWriter;
import static org.easymock.EasyMock.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void setUp() {
        ConfigTest.loadDefaultConfig();
    }

    @Test
    public void testStaticInits() {
        assertNotNull(App.configWriter);
        assertNotNull(App.config);
    }

    @Test
    public void testLoadControllerAndModel() {
        App.loadControllerAndModel();

        assertNotNull(App.config);
        assertNotNull(App.controller);
        assertNotNull(App.model);
    }

    @Test
    public void testIsFirstStart() {
        Model model = createMock(Model.class);
        App.model = model;
        expect(model.getUser()).andReturn(null);
        expect(model.getUser()).andReturn(createMock(Participant.class));
        replay(model);

        assertTrue(App.isFirstStart());
        assertFalse(App.isFirstStart());

        verify(model);
    }

    @Test
    public void testGetController() {
        assertSame(App.controller, App.getController());
    }

    @Test
    public void testGetModel() {
        assertSame(App.model, App.getModel());
    }

    @Test
    public void testGetMainWindow() {
        assertSame(App.mainWindow, App.getMainWindow());
    }

    @Test
    public void testGetConfig() {
        assertSame(App.config, App.getConfig());
    }

    @Test
    public void testStoreConfig() {
        ConfigWriter writer = createMock(ConfigWriter.class);
        App.configWriter = writer;
        writer.writeConfig(App.config, Config.FOLDER, Config.FILE);
        expectLastCall();
        replay(writer);

        App.storeConfig();

        verify(writer);
    }

    /**
     * Overwrites the existing {@link Config} in {@link App} for unit testing
     * purposes.
     *
     * @param config The new config.
     */
    public static void setAppConfig(Config config) {
        App.config = config;
    }

    /**
     * Overwrites the existing {@link ConfigWriter} in {@link App} for unit
     * testing purposes.
     *
     * @param configWriter The new config writer.
     */
    public static void setAppConfigWriter(ConfigWriter configWriter) {
        App.configWriter = configWriter;
    }

    /**
     * Overwrites the existing {@link Controller} in {@link App} for unit
     * testing purposes.
     *
     * @param controller The new controller.
     */
    public static void setAppController(Controller controller) {
        App.controller = controller;
    }

    /**
     * Overwrites the existing {@link Model} in {@link App} for unit testing
     * purposes.
     *
     * @param model The new model.
     */
    public static void setAppModel(Model model) {
        App.model = model;
    }

    /**
     * Overwrites the existing {@link MainWindow} in {@link App} for unit
     * testing purposes.
     *
     * @param mainWindow The new window.
     */
    public static void setAppMainWindow(MainWindow mainWindow) {
        App.mainWindow = mainWindow;
    }

}
