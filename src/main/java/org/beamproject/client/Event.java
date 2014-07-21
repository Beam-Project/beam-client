package org.beamproject.client;

import com.google.common.eventbus.EventBus;
import org.beamproject.client.model.MainModel;
import org.beamproject.client.model.MenuModel;
import org.beamproject.client.model.WizardModel;
import org.beamproject.client.view.AddContactLayer;
import org.beamproject.client.view.MainWindow;
import org.beamproject.client.view.PasswordChangeLayer;
import org.beamproject.client.view.ServerChangeLayer;
import org.beamproject.client.view.UnlockLayer;
import org.beamproject.client.view.wizard.AddressLayer;
import org.beamproject.client.view.wizard.PasswordLayer;
import org.beamproject.client.view.wizard.WelcomeLayer;
import org.beamproject.common.crypto.EncryptedConfig;

/**
 * Stores all the events used for the {@link EventBus}.
 */
public enum Event {

    /**
     * This event is sent when the {@link MainWindow} should be shown and
     * especially no layer, hiding the {@link MainWindow}, should be activated.
     */
    SHOW_MAIN_WINDOW,
    /**
     * This event is sent by the {@link MainModel} during the first start of the
     * application in order to show the {@link WelcomeLayer}.
     */
    SHOW_WIZARD_WELCOME_LAYER,
    /**
     * This event is sent by the {@link WizardModel} in order to show the
     * {@link AddressLayer}.
     */
    SHOW_WIZARD_ADDRESS_LAYER,
    /**
     * This event is sent by the {@link WizardModel} after the new Beam address
     * has been generated, so that it can be shown.
     */
    ENABLE_WIZARD_ADDRESS_GENERATED_ADDRESS,
    /**
     * This event is sent by the {@link WizardModel} in order to show the
     * {@link PasswordLayer}.
     */
    SHOW_WIZARD_PASSWORD_LAYER,
    /**
     * This event is sent by the {@link MainModel} in order to show the
     * {@link UnlockLayer}. This is necessary after the start of the client when
     * the password is not stored/remembered.
     */
    SHOW_UNLOCK_LAYER,
    /**
     * This event is sent by the {@link MainModel} and indicates to the
     * {@link UnlockLayer} that the entered password is wrong.
     */
    UNLOCK_LAYER_WRONG_PASSWORD,
    /**
     * This event is sent by the {@link MainModel} and indicates that the
     * {@link EncryptedConfig} is now decrypted. Parts of the view may now
     * updated its values as they have become available.
     */
    ENCRYPTED_CONFIG_UNLOCKED,
    /**
     * This event is sent by the {@link MenuModel} in order to show the
     * {@link PasswordChangeLayer}.
     */
    SHOW_PASSWORD_CHANGE_LAYER,
    /**
     * This event is sent by the {@link MenuModel} and indicates to the
     * {@link PasswordChangeLayer} that the entered password is wrong.
     */
    PASSWORD_CHANGE_WRONG_PASSWORD,
    /**
     * This event is sent by the {@link MenuModel} in order to show the
     * {@link ServerChangeLayer}.
     */
    SHOW_SERVER_CHANGE_LAYER,
    /**
     * This event is sent by the {@link MenuModel} in order to show the
     * {@link AddContactLayer}.
     */
    SHOW_ADD_CONTACT_LAYER,
    /**
     * This event is sent by the {@link MainModel} in order to dispose all
     * frames.
     */
    DISPOSE,

}
