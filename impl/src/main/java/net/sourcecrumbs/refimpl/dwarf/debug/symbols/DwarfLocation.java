/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.debug.symbols;

import net.libudi.api.Register;
import net.sourcecrumbs.api.debug.symbols.Location;

/**
 * @author mcnulty
 */
public class DwarfLocation implements Location
{
    @Override
    public boolean isRegister()
    {
        return false;
    }

    @Override
    public Register getRegister()
    {
        return null;
    }

    @Override
    public boolean isMemory()
    {
        return false;
    }

    @Override
    public long getMemoryAddress()
    {
        return 0;
    }
}
