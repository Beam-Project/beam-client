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
package org.inchat.client;

import java.util.ArrayList;
import java.util.List;
import org.inchat.client.ui.ConversationWindow;
import org.inchat.client.ui.Frames;
import org.inchat.common.Config;
import org.inchat.common.Contact;

/**
 * The controller manages the activities between the components, typically
 * triggered by the user interface.
 */
public class Controller {
    
    List<ConversationWindow> conversationWindows = new ArrayList<>();
    
    public void changeUsername(String username) {
        Config.setProperty(Config.Key.participantName, username);
        App.getMainWindow().setUsername(username);
    }
    
    public void addContact(Contact contact) {
        App.getModel().addContact(contact);
    }
    
    public void openConversationWindow(Contact contact) {
        ConversationWindow window = new ConversationWindow();
        Frames.setIcons(window);
        window.setContact(contact);
        conversationWindows.add(window);
        window.setVisible(true);
    }
}
