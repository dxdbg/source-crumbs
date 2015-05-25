/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.types;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.annotation.Init;

/**
 * Represents an initial length field in a DWARF file
 *
 * @author mcnulty
 */
public class InitialLength {

    private static final int X64_LENGTH_SENTINEL = -1;

    @BoundNumber(size = "32")
    private int x32length;

    @If("x32length == (0 - 1)") // Dirty hack to get around Limbo's lack of unary - operator
    @BoundNumber(size = "64")
    private long x64length;

    private long length;

    private int offsetLength;

    @Init
    public void init() {
        if (x32length == X64_LENGTH_SENTINEL) {
            length = x64length;
            offsetLength = 64;
        }else{
            length = x32length;
            offsetLength = 32;
        }
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getOffsetLength() {
        return offsetLength;
    }

    public void setOffsetLength(int offsetLength) {
        this.offsetLength = offsetLength;
    }

    public int getSize()
    {
        if (x32length == X64_LENGTH_SENTINEL) {
            return 96 / 8;
        }
        return 32 / 8;
    }
}
