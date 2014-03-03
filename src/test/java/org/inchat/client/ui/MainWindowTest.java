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
import org.inchat.client.App;
import org.inchat.client.AppTest;
import org.inchat.client.Model;
import org.inchat.common.Config;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MainWindowTest {

    private final String CONFIG_FILE = "MainWindow.conf";
    private MainWindow mainWindow;

    @Before
    public void setUp() {
        cleanUp();
        
        Config.createDefaultConfig(CONFIG_FILE);
        Config.loadConfig(CONFIG_FILE);

        mainWindow = new MainWindow();
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

        mainWindow = new MainWindow();

        assertSame(model.getContactList(), mainWindow.contactList.getModel());
        
        cleanUp();
    }

    @Test
    public void testSetPositionOnNewSetting() {
        assertTrue(mainWindow.getX() > 0);
        assertTrue(mainWindow.getY() > 0);
        
        cleanUp();
    }

    @Test
    public void testSetOfProfileSpecificValuesOnNewSetting() {
        assertTrue(mainWindow.nameButton.getText().isEmpty());
        
        cleanUp();
    }

    @Test
    public void testSetOfProfileSpecificValuesOnExistingSetting() {
        Config.setProperty(Config.Key.participantName, "spock");
        mainWindow = new MainWindow();
        assertEquals("spock", mainWindow.nameButton.getText());
        
        cleanUp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnNull() {
        mainWindow.setUsername(null);
        
        cleanUp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnEmptyString() {
        mainWindow.setUsername("");
        
        cleanUp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnSpaceString() {
        mainWindow.setUsername("   ");
        
        cleanUp();
    }

    @Test
    public void testSetUsername() {
        String name = "Timmee";
        mainWindow.setUsername(name);
        assertEquals(name, mainWindow.nameButton.getText());
        
        cleanUp();
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
