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
import java.util.Arrays;
import lombok.experimental.Delegate;
import org.beamproject.client.Event;
import static org.beamproject.client.Event.PASSWORD_CHANGE_WRONG_PASSWORD;
import static org.beamproject.client.Event.SHOW_MAIN_WINDOW;
import static org.beamproject.client.Event.SHOW_PASSWORD_CHANGE_LAYER;
import static org.beamproject.client.Event.SHOW_SERVER_CHANGE_LAYER;
import org.beamproject.client.model.MainModel.AcceptedSender;
import static org.beamproject.client.util.ConfigKey.ACCEPTED_MESSAGE_SENDER;
import static org.beamproject.client.util.ConfigKey.SERVER_ADDRESS;
import org.beamproject.common.Server;
import org.beamproject.common.crypto.EncryptedConfig;

/**
 * This is the model handling the logic required by menus of the main window.
 */
@Singleton
public class MenuModel {

    @Delegate
    private final MainModel mainModel;
    private final EventBus bus;

    @Inject
    public MenuModel(MainModel mainModel, EventBus bus) {
        this.mainModel = mainModel;
        this.bus = bus;
    }

    /**
     * Sets the {@link EncryptedConfig} to accept messages from the given
     * senders.
     *
     * @param sender The setting to store.
     */
    public void setAcceptedMessageSenders(AcceptedSender sender) {
        mainModel.getEncryptedConfig().set(ACCEPTED_MESSAGE_SENDER, sender.toString());
    }

    public void showPasswordChangeLayer() {
        bus.post(SHOW_PASSWORD_CHANGE_LAYER);
    }

    /**
     * Checks if the {@code oldPassword} is correct and, if so, updates the
     * password and stores the instance of {@link EncryptedConfig} again using
     * the new password.
     * <p>
     * If everything went okay, the main window will be shown.<br />
     * If the {@code oldPassword} was is wrong, an {@link Event} about that will
     * be sent to the bus.
     *
     * @param oldPassword The old password that was used to unlock.
     * @param newPassword The new password to use from now on.
     */
    public void processPasswordChange(char[] oldPassword, char[] newPassword) {
        if (isOldPasswordCorrect(oldPassword)) {
            mainModel.getEncryptedConfig().changePassword(newPassword);
            mainModel.storeConfigs();
            bus.post(SHOW_MAIN_WINDOW);
        } else {
            bus.post(PASSWORD_CHANGE_WRONG_PASSWORD);
        }
    }

    private boolean isOldPasswordCorrect(char[] oldPassword) {
        return Arrays.equals(oldPassword, mainModel.getEncryptedConfig().getPassword());
    }

    public void abortPasswordChange() {
        bus.post(SHOW_MAIN_WINDOW);
    }

    public void showServerChangeLayer() {
        bus.post(SHOW_SERVER_CHANGE_LAYER);
    }

    /**
     * Updates the {@link Server} address. Since this will probably require to
     * re-connect an re-authenticate against the server, the client will also
     * restart.
     *
     * @param serverAddress The new Beam address of the server.
     */
    public void processServerChange(String serverAddress) {
        Server server = new Server(serverAddress);
        mainModel.setServer(server);
        mainModel.getEncryptedConfig().set(SERVER_ADDRESS, server.getAddress());
        mainModel.restart();
    }

    public void abortServerChange() {
        bus.post(SHOW_MAIN_WINDOW);
    }

}
