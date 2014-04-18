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
package org.beamproject.client.ui.settings;

import java.beans.PropertyChangeListener;
import org.beamproject.client.App;
import static org.easymock.EasyMock.*;
import org.beamproject.client.AppTest;
import org.beamproject.client.ConfigTest;
import org.beamproject.client.Controller;
import org.beamproject.client.Model;
import org.beamproject.client.ModelTest;
import org.beamproject.common.Participant;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class IdentityPanelTest {

    private final String USERNAME = "Mr/Mrs Garrison";
    private final String SERVER_URL = "http://my-server.beamproject.org:1234";
    private IdentityPanel panel;
    private Controller controller;
    private Model model;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        App.getConfig().setProperty("username", USERNAME);
        App.getConfig().setProperty("serverUrl", SERVER_URL);
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        model = new Model();
        model.setUser(Participant.generate());
        model.setServer(Participant.generate());
        AppTest.setAppModel(model);

        panel = new IdentityPanel();
    }

    @Test
    public void testLoadingFieldsWithServerAndParticipant() {
        assertEquals(USERNAME, panel.usernameTextField.getText());
        assertEquals(model.getUser().getPublicKeyAsBase58(), panel.userPublicKeyTextField.getText());
        assertEquals(SERVER_URL, panel.serverUrlTextField.getText());
        assertEquals(model.getServer().getPublicKeyAsBase58(), panel.serverPublicKeyTextField.getText());
    }

    @Test
    public void testLoadingFieldsWithoutServerAndParticipant() {
        ModelTest.setUser(null, model);
        ModelTest.setServer(null, model);
        App.getConfig().removeProperty("username");
        App.getConfig().removeProperty("serverUrl");
        panel = new IdentityPanel();

        assertTrue(panel.usernameTextField.getText().isEmpty());
        assertTrue(panel.userPublicKeyTextField.getText().isEmpty());
        assertTrue(panel.serverUrlTextField.getText().isEmpty());
        assertTrue(panel.serverPublicKeyTextField.getText().isEmpty());
    }

    @Test
    public void testIsUsernameValid() {
        assertFalse(IdentityPanel.isUsernameValid(null));
        assertFalse(IdentityPanel.isUsernameValid(""));
        assertTrue(IdentityPanel.isUsernameValid("a"));
        assertTrue(IdentityPanel.isUsernameValid("a sdlfkj sdlfja d"));
        assertFalse(IdentityPanel.isUsernameValid(" a;klf spfa9difasd;flk23lkjsdf "));
        assertTrue(IdentityPanel.isUsernameValid("@sldkfj!#Ralskgjsal'"));
    }

    @Test
    public void testUsernameTextFieldPropertyChange() {
        PropertyChangeListener listener = panel.usernameTextField.getPropertyChangeListeners()[2];
        String validUsername1 = "Mr Garrison";
        String validUsername2 = "Mrs Garrison";

        controller.setUsername(validUsername1);
        expectLastCall();
        controller.setUsername(validUsername2);
        expectLastCall();
        replay(controller);

        panel.usernameTextField.setText("   ");
        listener.propertyChange(null);

        panel.usernameTextField.setText("");
        listener.propertyChange(null);

        String changedUsername = "Mr Garrison";
        panel.usernameTextField.setText(changedUsername);
        listener.propertyChange(null);

        String changedUsernameToTrim = "  Mrs Garrison   ";
        panel.usernameTextField.setText(changedUsernameToTrim);
        listener.propertyChange(null);

        verify(controller);
    }

    @Test
    public void testIsServerUrlValid() {
        assertFalse(IdentityPanel.isServerUrlValid(null));
        assertFalse(IdentityPanel.isServerUrlValid(""));
        assertFalse(IdentityPanel.isServerUrlValid("z "));
        assertTrue(IdentityPanel.isServerUrlValid("http://org"));
        assertTrue(IdentityPanel.isServerUrlValid(SERVER_URL));
        assertTrue(IdentityPanel.isServerUrlValid("    \t   " + SERVER_URL + "    \t   "));
    }

    @Test
    public void testServerUrlTextFieldPropertyChange() {
        PropertyChangeListener listener = panel.serverUrlTextField.getPropertyChangeListeners()[2];
        String validUrl1 = "http://inchtat.org/myserver/is/very/cool";
        String validUrl2 = "http://192.168.0.123";

        controller.setServerUrl(validUrl1);
        expectLastCall();
        controller.setServerUrl(validUrl2);
        expectLastCall();
        replay(controller);

        panel.serverUrlTextField.setText("   ");
        listener.propertyChange(null);

        panel.serverUrlTextField.setText("");
        listener.propertyChange(null);

        panel.serverUrlTextField.setText(validUrl1);
        listener.propertyChange(null);

        panel.serverUrlTextField.setText("    " + validUrl2 + "     ");
        listener.propertyChange(null);

        verify(controller);
    }

    @Test
    public void testIsServerPublicKeyValid() {
        Participant server = Participant.generate();
        String validPublicKey = server.getPublicKeyAsBase58();
        String invalidPublicKey1 = validPublicKey.replace('a', 'b');
        String invalidPublicKey2 = validPublicKey.replace('a', ' ');
        String invalidPublicKey3 = validPublicKey + "hello";
        String invalidPublicKey4 = "beam:" + validPublicKey;

        assertFalse(IdentityPanel.isServerPublicKeyValid(null));
        assertFalse(IdentityPanel.isServerPublicKeyValid(""));
        assertFalse(IdentityPanel.isServerPublicKeyValid("z "));
        assertFalse(IdentityPanel.isServerPublicKeyValid(invalidPublicKey1));
        assertFalse(IdentityPanel.isServerPublicKeyValid(invalidPublicKey2));
        assertFalse(IdentityPanel.isServerPublicKeyValid(invalidPublicKey3));
        assertFalse(IdentityPanel.isServerPublicKeyValid(invalidPublicKey4));
        assertTrue(IdentityPanel.isServerPublicKeyValid(validPublicKey));
    }

    @Test
    public void testServerPublicKeyTextFieldPropertyChange() {
        PropertyChangeListener listener = panel.serverPublicKeyTextField.getPropertyChangeListeners()[2];
        Participant server1Full = Participant.generate();
        Participant server2Full = Participant.generate();
        Participant server1 = new Participant(EccKeyPairGenerator.fromPublicKey(server1Full.getPublicKeyAsBytes()));
        Participant server2 = new Participant(EccKeyPairGenerator.fromPublicKey(server2Full.getPublicKeyAsBytes()));

        controller.setServer(eq(server1));
        expectLastCall();
        controller.setServer(eq(server2));
        expectLastCall();
        replay(controller);

        panel.serverPublicKeyTextField.setText("   ");
        listener.propertyChange(null);

        panel.serverPublicKeyTextField.setText("");
        listener.propertyChange(null);

        panel.serverPublicKeyTextField.setText(server1.getPublicKeyAsBase58());
        listener.propertyChange(null);

        panel.serverPublicKeyTextField.setText("    " + server2.getPublicKeyAsBase58() + "     ");
        listener.propertyChange(null);

        verify(controller);
    }

}
