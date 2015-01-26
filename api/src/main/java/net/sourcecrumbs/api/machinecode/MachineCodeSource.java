/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.machinecode;

/**
 * Represents a source for machine code
 *
 * @author mcnulty
 */
public interface MachineCodeSource {

    /**
     * @return the mapping used to associate source lines with machine code and vice versa or null if no mapping is available
     */
    MachineCodeMapping getMachineCodeMapping();
}
