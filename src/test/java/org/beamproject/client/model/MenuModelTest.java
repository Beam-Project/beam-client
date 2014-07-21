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
import static org.beamproject.client.Event.*;
import org.beamproject.client.ExecutorFake;
import org.beamproject.client.util.ConfigKey;
import org.beamproject.common.util.Config;
import static org.beamproject.client.util.ConfigKey.SERVER_ADDRESS;
import org.beamproject.common.crypto.EncryptedConfig;
import org.beamproject.common.Server;
import static org.easymock.EasyMock.*;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MenuModelTest {

    private MainModel mainModel;
    private BusFake busFake;
    private Config<ConfigKey> config;
    private ExecutorFake executorFake;
    private MenuModel model;
    private EncryptedConfig<ConfigKey> encryptedConfig;

    @Before
    public void setUp() {
        mainModel = createMock(MainModel.class);
        busFake = new BusFake();
        model = new MenuModel(mainModel, busFake.getBus());
    }

    @After
    public void verifyBus() {
        busFake.verify();
    }

    @Test
    public void testShowPasswordChangeLayer() {
        model.showPasswordChangeLayer();
        assertEquals(SHOW_PASSWORD_CHANGE_LAYER, busFake.getNextEvent());
    }

    @Test
    public void testProcessPasswordChangeOnWrongOldPassword() {
        char[] oldPassword = "old password".toCharArray();
        char[] enteredOldPassword = "entered old password".toCharArray();
        char[] newPassword = "new password".toCharArray();
        setUpPasswordChangeMocks(oldPassword, newPassword, false);

        model.processPasswordChange(enteredOldPassword, newPassword);

        verify(mainModel, encryptedConfig);
        assertEquals(PASSWORD_CHANGE_WRONG_PASSWORD, busFake.getNextEvent());
    }

    @Test
    public void testProcessPasswordChange() {
        char[] oldPassword = "old password".toCharArray();
        char[] newPassword = "new password".toCharArray();
        setUpPasswordChangeMocks(oldPassword, newPassword, true);

        model.processPasswordChange(oldPassword, newPassword);

        verify(mainModel, encryptedConfig);
        assertEquals(SHOW_MAIN_WINDOW, busFake.getNextEvent());
    }

    @SuppressWarnings("unchecked")
    private void setUpPasswordChangeMocks(char[] oldPassword, char[] newPassword, boolean isPassswordCorrect) {
        encryptedConfig = createMock(EncryptedConfig.class);
        expect(mainModel.getEncryptedConfig()).andReturn(encryptedConfig);
        expect(encryptedConfig.getPassword()).andReturn(oldPassword);

        if (isPassswordCorrect) {
            expect(mainModel.getEncryptedConfig()).andReturn(encryptedConfig);
            encryptedConfig.changePassword(newPassword);
            expectLastCall();
            mainModel.storeConfigs();
            expectLastCall();
        }

        replay(mainModel, encryptedConfig);
    }

    @Test
    public void testAbortPasswordChange() {
        model.abortPasswordChange();
        assertEquals(SHOW_MAIN_WINDOW, busFake.getNextEvent());
    }

    @Test
    public void testShowServerChangeLayer() {
        model.showServerChangeLayer();
        assertEquals(SHOW_SERVER_CHANGE_LAYER, busFake.getNextEvent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProcessServerChange() {
        final Server server = Server.generate();

        encryptedConfig = createMock(EncryptedConfig.class);
        mainModel.setServer(anyObject(Server.class));
        expectLastCall();
        expect(mainModel.getEncryptedConfig()).andReturn(encryptedConfig);
        encryptedConfig.set(SERVER_ADDRESS, server.getAddress());
        expectLastCall();
        mainModel.restart();
        expectLastCall();
        replay(mainModel, encryptedConfig);

        model.processServerChange(server.getAddress());

        verify(mainModel, encryptedConfig);
    }

    @Test
    public void testAbortServerChange() {
        model.abortServerChange();
        assertEquals(SHOW_MAIN_WINDOW, busFake.getNextEvent());
    }
}
