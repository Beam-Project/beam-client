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
package org.beamproject.client;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.util.Properties;
import org.beamproject.client.model.ChatModel;
import org.beamproject.client.model.MainModel;
import org.beamproject.client.model.MenuModel;
import org.beamproject.client.model.WizardModel;
import org.beamproject.client.util.ClipboardAccess;
import org.beamproject.client.util.ConfigKey;
import org.beamproject.common.util.Config;
import org.beamproject.common.util.Files;
import org.beamproject.client.view.AddContactLayer;
import org.beamproject.client.view.wizard.AddressLayer;
import org.beamproject.client.view.BootstrapLayer;
import org.beamproject.client.view.menu.InfoLayer;
import org.beamproject.client.view.MainWindow;
import org.beamproject.client.view.PasswordChangeLayer;
import org.beamproject.client.view.ServerChangeLayer;
import org.beamproject.client.view.menu.SettingsLayer;
import org.beamproject.client.view.menu.StatusLayer;
import org.beamproject.client.view.UnlockLayer;
import org.beamproject.client.view.wizard.WelcomeLayer;
import org.beamproject.common.util.Executor;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // Models
        bind(MainModel.class);
        bind(WizardModel.class);
        bind(MenuModel.class);
        bind(ChatModel.class);

        // Views
        bind(MainWindow.class);
        bind(BootstrapLayer.class);
        bind(InfoLayer.class);
        bind(StatusLayer.class);
        bind(SettingsLayer.class);
        bind(WelcomeLayer.class);
        bind(AddressLayer.class);
        bind(UnlockLayer.class);
        bind(PasswordChangeLayer.class);
        bind(ServerChangeLayer.class);
        bind(AddContactLayer.class);

        // Utils
        bind(ClipboardAccess.class);
        bind(Files.class);
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return new EventBus(new SubscriberExceptionHandler() {
            @Override
            public void handleException(Throwable exception, SubscriberExceptionContext context) {
                System.out.println("EventBus exception occurred: " + context.getSubscriber().toString());
                System.out.println("Method: " + context.getSubscriberMethod().toString());
                System.out.println("");
                exception.printStackTrace();
            }
        });
    }

    @Provides
    @Singleton
    Config<ConfigKey> providesConfig() {
        Files files = new Files();
        Properties properties = files.loadConfigIfAvailable(App.CONFIG_PATH);
        return new Config<>(properties);
    }

    @Provides
    @Singleton
    Executor providesExecutor() {
        return new Executor();
    }

}
