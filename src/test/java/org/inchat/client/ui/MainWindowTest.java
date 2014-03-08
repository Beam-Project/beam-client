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

import java.io.File;
import javax.swing.JButton;
import static org.easymock.EasyMock.*;
import org.inchat.client.App;
import org.inchat.client.AppTest;
import org.inchat.client.Controller;
import org.inchat.client.Model;
import org.inchat.common.Config;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MainWindowTest {

    private final String CONFIG_FILE = "MainWindow.conf";
    private MainWindow window;
    private Controller controller;

    @Before
    public void setUp() {
        cleanUp();

        Config.createDefaultConfig(CONFIG_FILE);
        Config.loadConfig(CONFIG_FILE);

        controller = createMock(Controller.class);
        AppTest.setAppController(controller);

        window = new MainWindow();
    }

    /**
     * This method deletes the config file. In this very test class, @After and
     * config.delete() does not seem to work properly, no idea why. It could be
     * a bug somewhere. To avoid this problem, the cleanUp() method is manually
     * invoked after each test.
     */
    @After
    public void cleanUp() {
        File config = new File(CONFIG_FILE);

        // A simple config.delete() does not the job here - no idea why.
        // With this workaround, the config file gets deleted after all.
        while (!config.delete() && config.exists()) {
            System.out.println("Could not delete the config file - try again...");
        }
    }

    @Test
    public void testConstructorOnSettingModelList() {
        Model model = new Model();
        AppTest.setAppMdoel(model);

        window = new MainWindow();

        assertSame(model.getContactList(), window.contactList.getModel());

        cleanUp();
    }

    @Test
    public void testSetPositionOnNewSetting() {
        assertTrue(window.getX() > 0);
        assertTrue(window.getY() > 0);

        cleanUp();
    }

    @Test
    public void testSetPositionOnSize() {
        int width = 400;
        int height = 600;
        Config.setProperty(Config.Key.windowWidth, "" + width);
        Config.setProperty(Config.Key.windowHeight, "" + height);

        window = new MainWindow();

        assertEquals(width, window.getWidth());
        assertEquals(height, window.getHeight());

        cleanUp();
    }

    @Test
    public void testSetPositionOnIllegalSize() {
        int width = 1;
        int height = 1;
        Config.setProperty(Config.Key.windowWidth, "" + width);
        Config.setProperty(Config.Key.windowHeight, "" + height);

        window = new MainWindow();

        assertTrue(window.getWidth() > width);
        assertTrue(window.getHeight() > height);

        cleanUp();
    }

    @Test
    public void testSetOfProfileSpecificValuesOnNewSetting() {
        assertTrue(window.nameButton.getText().isEmpty());

        cleanUp();
    }

    @Test
    public void testSetOfProfileSpecificValuesOnExistingSetting() {
        Config.setProperty(Config.Key.participantName, "spock");
        window = new MainWindow();
        assertEquals("spock", window.nameButton.getText());

        cleanUp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnNull() {
        window.setUsername(null);

        cleanUp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnEmptyString() {
        window.setUsername("");

        cleanUp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnSpaceString() {
        window.setUsername("   ");

        cleanUp();
    }

    @Test
    public void testSetUsername() {
        String name = "Timmee";
        window.setUsername(name);
        assertEquals(name, window.nameButton.getText());

        cleanUp();
    }

    @Test
    public void testNameButtonOnShowingInvokingMethod() {
        controller.showNameInSettingsWindow();
        expectLastCall();
        replay(controller);

        window.nameButton.doClick();

        verify(controller);
    }

    @Test
    public void testSettingsButtonOnShowingInvokingMethod() {
        controller.showSettingsWindow();
        expectLastCall();
        replay(controller);

        window.settingsButton.doClick();

        verify(controller);
    }

    /**
     * Gets the MainWindows {@link JButton} of the name for testing purposes.
     *
     * @return The button.
     */
    public static JButton getNameButton() {
        return App.getMainWindow().nameButton;
    }

}
