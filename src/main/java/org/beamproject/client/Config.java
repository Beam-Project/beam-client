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

import static org.aeonbits.owner.Config.Sources;
import org.beamproject.common.util.ConfigBase;

/**
 * This class is used for configuration purposes and interacts with the OWNER
 * library (see: http://owner.aeonbits.org/).
 *
 * @see ConfigBase
 */
@Sources({"file:~/.beam/client.conf${developmentExtension}"})
public interface Config extends ConfigBase {

    public final static String FOLDER = System.getProperty("user.home") + "/.beam/";
    public final static String FILE = "client.conf";

    @DefaultValue("keypair-password")
    String keyPairPassword();

    @DefaultValue("keypair-salt")
    String keyPairSalt();

    String encryptedPublicKey();

    String encryptedPrivateKey();

    @DefaultValue("Beamer")
    String participantName();

    @DefaultValue("server-salt")
    String serverSalt();
    
    String encryptedServerPublicKey();
    
    String serverUrl();

    @DefaultValue("-1")
    int windowPositionX();

    @DefaultValue("-1")
    int windowPositionY();

    @DefaultValue("-1")
    int windowWidth();

    @DefaultValue("-1")
    int windowHeight();
}
