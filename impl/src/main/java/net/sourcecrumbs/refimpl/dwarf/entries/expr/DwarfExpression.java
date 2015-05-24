/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.expr;

import net.sourcecrumbs.api.debug.symbols.ContextInspectionException;
import net.sourcecrumbs.api.debug.symbols.Location;
import net.sourcecrumbs.api.debug.symbols.RegisterContext;

/**
 * @author mcnulty
 */
public class DwarfExpression
{
    private byte[] data;

    public DwarfExpression(byte[] data)
    {
        this.data = data;
    }

    public Location calculateLocation(RegisterContext registerContext) throws ContextInspectionException
    {
        // TODO
        return null;
    }
}
