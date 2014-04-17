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
import org.aeonbits.owner.ConfigFactory;
import static org.easymock.EasyMock.*;
import org.beamproject.client.App;
import org.beamproject.client.AppTest;
import org.beamproject.client.Config;
import org.beamproject.client.Controller;
import org.beamproject.client.Model;
import org.beamproject.common.util.ConfigWriter;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MainWindowTest {

    private MainWindow window;
    private ConfigWriter writer;

    @Before
    public void setUp() {
        writer = createMock(ConfigWriter.class);
        AppTest.setAppConfigWriter(writer);
        AppTest.setAppConfig(ConfigFactory.create(Config.class));
        AppTest.setAppMdoel(new Model());

        window = new MainWindow();
    }

    @Test
    public void testConstructorOnSettingModelList() {
        assertSame(App.getModel().getContactList(), window.contactList.getModel());
    }

    @Test
    public void testSetPositionOnNewSetting() {
        assertTrue(window.getX() > 0);
        assertTrue(window.getY() > 0);
    }

    @Test
    public void testSetSizeOnMinimalSizes() {
        assertEquals(MainWindow.MINIMAL_WINDOW_WIDTH_IN_PX, window.getWidth());
        assertEquals(MainWindow.MINIMAL_WINDOW_HEIGHT_IN_PX, window.getHeight());
    }

    @Test
    public void testSetProfileSpecificValuesOnExistingSetting() {
        assertEquals(App.getConfig().participantName(), window.nameButton.getText());
    }

    @Test
    public void testSetProfileSpecificValuesOnInexistingSetting() {
        App.getConfig().setProperty("participantName", null);

        window.nameButton.setText("a name");
        window.setParticipantName();
        assertEquals(window.DEFAULT_USERNAME, window.nameButton.getText());
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
        String name = "Timmee";
        window.setUsername(name);
        assertEquals(name, window.nameButton.getText());
    }

    @Test
    public void testInfoButtonOnShowingInvokingMethod() {
        Controller controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        controller.showInfoWindow();
        expectLastCall();
        replay(controller);

        window.infoButton.doClick();

        verify(controller);
    }

    @Test
    public void testNameButtonOnShowingInvokingMethod() {
        Controller controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        controller.showNameInSettingsWindow();
        expectLastCall();
        replay(controller);

        window.nameButton.doClick();

        verify(controller);
    }

    @Test
    public void testSettingsButtonOnShowingInvokingMethod() {
        Controller controller = createMock(Controller.class);
        AppTest.setAppController(controller);
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
