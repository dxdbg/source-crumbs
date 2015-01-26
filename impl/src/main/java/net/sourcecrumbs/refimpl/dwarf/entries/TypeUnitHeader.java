/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import org.codehaus.preon.annotation.BoundNumber;

/**
 * Header for a type in a DWARF file
 *
 * @author dmcnulty
 */
public class TypeUnitHeader extends CompilationUnitHeader
{
    @BoundNumber(size = "64")
    private long typeSignature;

    @BoundNumber(size = "unitLength.offsetLength")
    private long typeOffset;

    public long getTypeSignature()
    {
        return typeSignature;
    }

    public void setTypeSignature(long typeSignature)
    {
        this.typeSignature = typeSignature;
    }

    public long getTypeOffset()
    {
        return typeOffset;
    }

    public void setTypeOffset(long typeOffset)
    {
        this.typeOffset = typeOffset;
    }
}
