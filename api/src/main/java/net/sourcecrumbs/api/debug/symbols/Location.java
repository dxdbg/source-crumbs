/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.debug.symbols;

import net.libudi.api.Register;

/**
 * A Location describes how to retrieve the data associated with a Variable given an execution context
 *
 * @author mcnulty
 */
public interface Location
{
    boolean isRegister();

    Register getRegister();

    boolean isMemory();

    long getMemoryAddress();
}
