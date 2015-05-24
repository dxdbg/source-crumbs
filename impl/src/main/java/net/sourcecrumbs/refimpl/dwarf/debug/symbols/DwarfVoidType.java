/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.debug.symbols;

import net.sourcecrumbs.api.debug.symbols.DebugType;

/**
 * A generic "void" type used when a type is left unspecified
 *
 * @author mcnulty
 */
public enum DwarfVoidType implements DebugType
{
    INSTANCE;

    @Override
    public String getName()
    {
        return "void";
    }

    @Override
    public boolean isImmutable()
    {
        return true;
    }

    @Override
    public boolean isPointer()
    {
        return false;
    }

    @Override
    public boolean isMemberStructure()
    {
        return false;
    }

    @Override
    public DebugType[] getMemberTypes()
    {
        return new DebugType[0];
    }

    @Override
    public DebugType getBaseType()
    {
        return null;
    }
}
