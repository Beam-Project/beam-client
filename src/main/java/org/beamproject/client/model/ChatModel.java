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

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.experimental.Delegate;
import static org.beamproject.client.Event.SHOW_ADD_CONTACT_LAYER;
import static org.beamproject.client.Event.SHOW_MAIN_WINDOW;
import org.beamproject.common.User;

/**
 * This is the model handling the logic required by chat and contact features.
 */
@Singleton
public class ChatModel {

    @Delegate
    private final MainModel mainModel;
    private final EventBus bus;

    @Inject
    public ChatModel(MainModel mainModel, EventBus bus) {
        this.mainModel = mainModel;
        this.bus = bus;
    }

    public void showAddContactLayer() {
        bus.post(SHOW_ADD_CONTACT_LAYER);
    }

    /**
     * Adds the {@link User} of the given user address to the contact list.
     *
     * @param userAddress The Beam address of the user to add.
     */
    public void addContact(String userAddress) {
        User user = new User(userAddress);
        System.out.println("user created: " + user.getUsername());
        System.out.println("address: " + user.getAddress());
        bus.post(SHOW_MAIN_WINDOW);
    }

    public void abortAddContact() {
        bus.post(SHOW_MAIN_WINDOW);
    }

}
