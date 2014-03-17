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
package org.beamproject.client.ui;

import javax.swing.JButton;
import static org.easymock.EasyMock.*;
import org.beamproject.client.App;
import org.beamproject.client.AppTest;
import org.beamproject.client.ClientConfigKey;
import org.beamproject.client.Controller;
import org.beamproject.client.Model;
import org.beamproject.common.Config;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MainWindowTest {
    
    private int height = 500;
    private int width = 375;
    private String name = "spock";
    private MainWindow window;
    private Controller controller;
    private Model model;
    private Config config;
    
    @Before
    public void setUp() {
        config = createMock(Config.class);
        controller = createMock(Controller.class);
        model = new Model();
        
        AppTest.setAppConfig(config);
        AppTest.setAppMdoel(model);
        AppTest.setAppController(controller);
        
        expect(config.getProperty(ClientConfigKey.participantName)).andReturn(name);
        expect(config.getProperty(ClientConfigKey.windowPositionX)).andReturn("10");
        expect(config.getProperty(ClientConfigKey.windowPositionY)).andReturn("100");
        expect(config.getProperty(ClientConfigKey.windowWidth)).andReturn("" + width);
        expect(config.getProperty(ClientConfigKey.windowHeight)).andReturn("" + height);
        replay(config, controller);
        
        window = new MainWindow();
    }
    
    @After
    public void verifyMocks() {
        verify(config, controller);
    }
    
    @Test
    public void testConstructorOnSettingModelList() {
        assertSame(model.getContactList(), window.contactList.getModel());
    }
    
    @Test
    public void testSetPositionOnNewSetting() {
        assertTrue(window.getX() > 0);
        assertTrue(window.getY() > 0);
    }
    
    @Test
    public void testSetPositionOnSize() {
        assertEquals(width, window.getWidth());
        assertEquals(height, window.getHeight());
    }
    
    @Test
    public void testSetPositionOnIllegalSize() {
        width = 1;
        height = 2;
        
        setUp();
        
        assertTrue(window.getWidth() > width);
        assertTrue(window.getHeight() > height);
    }
    
    @Test
    public void testSetProfileSpecificValuesOnExistingSetting() {
        assertEquals(name, window.nameButton.getText());
    }
    
    @Test
    public void testSetProfileSpecificValuesOnInexistingSetting() {
        config = createMock(Config.class);
        AppTest.setAppConfig(config);
        expect(config.getProperty(ClientConfigKey.participantName)).andReturn(null);
        replay(config);
        
        window.nameButton.setText("a name");
        window.setProfileSpecificValues();
        assertEquals(window.DEFAULT_USERNAME, window.nameButton.getText());
        
        verify(config);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnNull() {
        window.setUsername(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnEmptyString() {
        window.setUsername("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetUsernameOnSpaceString() {
        window.setUsername("   ");
    }
    
    @Test
    public void testSetUsername() {
        name = "Timmee";
        window.setUsername(name);
        assertEquals(name, window.nameButton.getText());
    }
    
    @Test
    public void testInfoButtonOnShowingInvokingMethod() {
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        
        controller.showInfoWindow();
        expectLastCall();
        replay(controller);
        
        window.infoButton.doClick();
    }
    
    @Test
    public void testNameButtonOnShowingInvokingMethod() {
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        
        controller.showNameInSettingsWindow();
        expectLastCall();
        replay(controller);
        
        window.nameButton.doClick();
    }
    
    @Test
    public void testSettingsButtonOnShowingInvokingMethod() {
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        
        controller.showSettingsWindow();
        expectLastCall();
        replay(controller);
        
        window.settingsButton.doClick();
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
