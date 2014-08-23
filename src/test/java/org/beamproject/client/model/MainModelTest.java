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

import java.security.Security;
import java.util.Properties;
import java.util.logging.Logger;
import org.beamproject.client.App;
import org.beamproject.client.BusFake;
import static org.beamproject.client.Event.*;
import org.beamproject.client.ExecutorFake;
import org.beamproject.common.util.Chars;
import org.beamproject.client.util.ConfigKey;
import org.beamproject.common.util.Config;
import static org.beamproject.client.util.ConfigKey.*;
import org.beamproject.common.crypto.EncryptedConfig;
import org.beamproject.common.util.Files;
import org.beamproject.common.Server;
import org.beamproject.common.User;
import static org.beamproject.common.crypto.BouncyCastleIntegrator.PROVIDER_NAME;
import org.beamproject.common.util.Executor;
import static org.easymock.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class MainModelTest {

    private final char[] PASSWORD_VALUE = "pass".toCharArray();
    private final byte[] SALT_VALUE = "salt".getBytes();
    private BusFake busFake;
    private MainModel model;
    private Config<ConfigKey> config;
    private EncryptedConfig<ConfigKey> encryptedConfig;
    private Files files;
    private Executor executor;

    @Before
    public void setUp() {
        busFake = new BusFake();
        config = new Config<>(new Properties());
        encryptedConfig = new EncryptedConfig<>(PASSWORD_VALUE, SALT_VALUE);
        files = createMock(Files.class);
        executor = new ExecutorFake();
        model = new MainModel(busFake.getBus(), config, files, executor);
        model.log = Logger.getGlobal();
    }

    @After
    public void verifyBus() {
        busFake.verify();
    }

    @Test
    public void testBootstrap() {
        Security.removeProvider(PROVIDER_NAME);
        model.bootstrap();
        assertEquals(SHOW_WIZARD_WELCOME_LAYER, busFake.getNextEvent());
        assertTrue(Security.getProvider(PROVIDER_NAME) != null);
    }

    @Test
    public void testIsPasswordRemembered() {
        assertFalse(model.isPasswordRemembered());

        config.set(PASSWORD, "");
        assertFalse(model.isPasswordRemembered());

        config.set(SALT, SALT_VALUE);
        assertFalse(model.isPasswordRemembered());

        config.set(PASSWORD, "mypass");
        config.set(SALT, "");
        assertFalse(model.isPasswordRemembered());

        config.set(PASSWORD, "mypass");
        config.set(SALT, SALT_VALUE);
        assertTrue(model.isPasswordRemembered());
    }

    @Test
    public void testApplyUserEnvironment() {
        model.applyUserEnvironment();
        assertEquals(ENCRYPTED_CONFIG_UNLOCKED, busFake.getNextEvent());
        assertEquals(SHOW_MAIN_WINDOW, busFake.getNextEvent());
    }

    @Test
    public void testUnlockDataOnWrongPassword() {
        config.set(SALT, "salt");
        expect(files.loadConfigIfAvailable(App.ENCRYPTED_CONFIG_PATH))
                .andReturn(getFilledEncryptedConfig());
        replay(files);

        model.unlockData("wrong pass".toCharArray());

        verify(files);
        assertEquals(UNLOCK_LAYER_WRONG_PASSWORD, busFake.getNextEvent());
    }

    @Test
    public void testUnlockData() {
        config.set(SALT, "salt");
        expect(files.loadConfigIfAvailable(App.ENCRYPTED_CONFIG_PATH))
                .andReturn(getFilledEncryptedConfig());
        replay(files);

        char[] password = "pass".toCharArray();
        model.unlockData(password);

        verify(files);
        assertNotNull(model.getUser());
        assertNotNull(model.getServer());
        assertEquals(ENCRYPTED_CONFIG_UNLOCKED, busFake.getNextEvent());
        assertEquals(SHOW_MAIN_WINDOW, busFake.getNextEvent());
    }

    private Properties getFilledEncryptedConfig() {
        Server server = Server.generate();
        User user = User.generate();
        encryptedConfig.set(SERVER_ADDRESS, server.getAddress());
        encryptedConfig.set(USERNAME, user.getUsername());
        encryptedConfig.set(USER_PUBLIC_KEY, user.getPublicKeyAsBytes());
        encryptedConfig.set(USER_PRIVATE_KEY, user.getPrivateKeyAsBytes());

        return encryptedConfig.copyToProperties();
    }

    @Test(expected = IllegalStateException.class)
    public void testStoreConfigsOnMissingEncryptedConfig() {
        model.setEncryptedConfig(null);
        model.storeConfigs();
    }

    @Test
    public void testStoreConfigs() {
        config.set(SALT, "mySalt");
        final Properties configs = config.copyToProperties();

        files.storeProperies(anyObject(Properties.class), anyString());

        expectLastCall().andDelegateTo(new Files() {
            @Override
            public void storeProperies(Properties p, String path) {
                assertEquals(configs.getProperty(SALT.toString()),
                        p.getProperty(SALT.toString()));
                assertEquals(App.CONFIG_PATH, path);
            }
        });

        encryptedConfig.set(PASSWORD, "myPassword");
        final Properties encryptedConfigs = encryptedConfig.copyToProperties();

        files.storeProperies(anyObject(Properties.class), anyString());

        expectLastCall().andDelegateTo(new Files() {
            @Override
            public void storeProperies(Properties p, String path) {
                assertEquals(encryptedConfigs.getProperty(PASSWORD.toString()),
                        p.getProperty(PASSWORD.toString()));
                assertEquals(App.ENCRYPTED_CONFIG_PATH, path);
            }
        });
        replay(files);

        model.setEncryptedConfig(encryptedConfig);
        model.storeConfigs();

        verify(files);
    }

    @Test
    public void testSetUsername() {
        User user = User.generate();
        model.setUser(user);
        model.setEncryptedConfig(encryptedConfig);
        String name = "Bond... James Bond";

        model.setUsername(name);

        assertEquals(name, user.getUsername());
        assertEquals(name, encryptedConfig.getAsString(USERNAME));
    }

    @Test
    public void testRememberPassword() {
        model.setEncryptedConfig(encryptedConfig);

        model.rememberPassword(false);
        assertEquals("", config.getAsString(PASSWORD));

        model.rememberPassword(true);
        assertArrayEquals(Chars.utfCharsToBytes(PASSWORD_VALUE), config.getAsBytes(PASSWORD));

        model.rememberPassword(false);
        assertEquals("", config.getAsString(PASSWORD));
    }

    @Test
    public void testRememberPasswordOnDestroyingOldOne() {
        model.setEncryptedConfig(encryptedConfig);
        assertFalse(config.contains(PASSWORD));

        byte[] oldPassword = "old pass".getBytes();
        config.set(PASSWORD, oldPassword);

        model.rememberPassword(false);

        assertTrue(areAllValuesZeros(oldPassword));
        assertEquals("", config.getAsString(PASSWORD));

        byte[] anotherOldPassword = "another old pass".getBytes();
        config.set(PASSWORD, anotherOldPassword);

        model.rememberPassword(true);

        assertTrue(areAllValuesZeros(oldPassword));
        assertArrayEquals(Chars.utfCharsToBytes(encryptedConfig.getPassword()), config.getAsBytes(PASSWORD));
    }

    @Test
    public void testShutdown() {
        model.setEncryptedConfig(encryptedConfig);
        files.storeProperies(anyObject(Properties.class), anyString());
        expectLastCall().times(2); // Indicates that the configs are being stored.
        replay(files);

        assertFalse(areAllValuesZeros(encryptedConfig.getPassword()));

        model.shutdown();

        assertEquals(DISPOSE, busFake.getNextEvent());
        assertTrue(areAllValuesZeros(encryptedConfig.getPassword()));
        verify(files);
    }

    private boolean areAllValuesZeros(char[] chars) {
        for (char character : chars) {
            if (character != (char) 0) {
                return false;
            }
        }

        return true;
    }

    private boolean areAllValuesZeros(byte[] bytes) {
        for (byte b : bytes) {
            if (b != (byte) 0) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void testShutdownWithoutConfigs() {
        model.shutdown();
        assertEquals(DISPOSE, busFake.getNextEvent());
    }

}
