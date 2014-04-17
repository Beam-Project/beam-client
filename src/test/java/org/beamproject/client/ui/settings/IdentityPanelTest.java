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

    private final String NAME = "Mr/Mrs Garrison";
    private final String SERVER_URL = "http://my-server.beamproject.org:1234";
    private IdentityPanel panel;
    private Controller controller;
    private Model model;

    @Before
    public void setUp() {
        ConfigTest.loadDefaultConfig();
        App.getConfig().setProperty("participantName", NAME);
        App.getConfig().setProperty("serverUrl", SERVER_URL);
        controller = createMock(Controller.class);
        AppTest.setAppController(controller);
        model = new Model();
        model.setParticipant(new Participant(EccKeyPairGenerator.generate()));
        model.setServer(new Participant(EccKeyPairGenerator.generate()));
        AppTest.setAppModel(model);

        panel = new IdentityPanel();
    }

    @Test
    public void testLoadingFieldsWithServerAndParticipant() {
        assertEquals(NAME, panel.nameTextField.getText());
        assertEquals(model.getParticipant().getPublicKeyAsBase58(), panel.userPublicKeyTextField.getText());
        assertEquals(SERVER_URL, panel.serverUrlTextField.getText());
        assertEquals(model.getServer().getPublicKeyAsBase58(), panel.serverPublicKeyTextField.getText());
    }

    @Test
    public void testLoadingFieldsWithoutServerAndParticipant() {
        ModelTest.setParticipant(null, model);
        ModelTest.setServer(null, model);
        App.getConfig().removeProperty("participantName");
        App.getConfig().removeProperty("serverUrl");
        panel = new IdentityPanel();

        assertEquals("", panel.nameTextField.getText());
        assertEquals("", panel.userPublicKeyTextField.getText());
        assertEquals("", panel.serverUrlTextField.getText());
        assertEquals("", panel.serverPublicKeyTextField.getText());
    }

    @Test
    public void testIsNameValid() {
        assertFalse(IdentityPanel.isNameValid(null));
        assertFalse(IdentityPanel.isNameValid(""));
        assertTrue(IdentityPanel.isNameValid("a"));
        assertTrue(IdentityPanel.isNameValid("a sdlfkj sdlfja d"));
        assertFalse(IdentityPanel.isNameValid(" a;klf spfa9difasd;flk23lkjsdf "));
        assertTrue(IdentityPanel.isNameValid("@sldkfj!#Ralskgjsal'"));
    }

    @Test
    public void testNameTextFieldPropertyChange() {
        PropertyChangeListener listener = panel.nameTextField.getPropertyChangeListeners()[2];
        String validName1 = "Mr Garrison";
        String validName2 = "Mrs Garrison";

        controller.changeName(validName1);
        expectLastCall();
        controller.changeName(validName2);
        expectLastCall();
        replay(controller);

        panel.nameTextField.setText("   ");
        listener.propertyChange(null);

        panel.nameTextField.setText("");
        listener.propertyChange(null);

        String changedName = "Mr Garrison";
        panel.nameTextField.setText(changedName);
        listener.propertyChange(null);

        String changedNameToTrim = "  Mrs Garrison   ";
        panel.nameTextField.setText(changedNameToTrim);
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
        Participant server = new Participant(EccKeyPairGenerator.generate());
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
        Participant server1Full = new Participant(EccKeyPairGenerator.generate());
        Participant server2Full = new Participant(EccKeyPairGenerator.generate());
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
