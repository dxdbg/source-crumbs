/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;

import net.sourcecrumbs.refimpl.dwarf.types.InitialLength;

/**
 * Header for a compilation unit in a DWARF file
 *
 * @author mcnulty
 */
public class CompilationUnitHeader {

    @Bound
    private InitialLength unitLength;

    @BoundNumber(size = "16")
    private short version;

    @BoundNumber(size = "unitLength.offsetLength")
    private long debugAbbrevOffset;

    @BoundNumber(size = "8")
    private byte addressSize;

    public long getUnitLength() {
        return unitLength.getLength();
    }

    public void setUnitLength(long unitLength) {
        this.unitLength.setLength(unitLength);
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public long getDebugAbbrevOffset() {
        return debugAbbrevOffset;
    }

    public void setDebugAbbrevOffset(long debugAbbrevOffset) {
        this.debugAbbrevOffset = debugAbbrevOffset;
    }

    public byte getAddressSize() {
        return addressSize;
    }

    public void setAddressSize(byte addressSize) {
        this.addressSize = addressSize;
    }

    public boolean is32bitDWARF() {
        return unitLength.getOffsetLength() == 32;
    }

    public int getOffsetLength() {
        return  unitLength.getOffsetLength();
    }
}
