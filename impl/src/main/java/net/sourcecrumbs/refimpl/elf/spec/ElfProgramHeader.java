/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec;

import java.util.EnumSet;

import net.sourcecrumbs.refimpl.elf.spec.constants.SegmentFlag;
import net.sourcecrumbs.refimpl.elf.spec.constants.SegmentType;

/**
 * Header for segment in an ELF file
 *
 * @author mcnulty
 */
public abstract class ElfProgramHeader {

    public abstract SegmentType getType();

    public abstract void setType(SegmentType type);

    public abstract Offset getOffset();

    public abstract void setOffset(Offset offset);

    public abstract Address getPhysicalAddress();

    public abstract void setPhysicalAddress(Address physicalAddress);

    public abstract Address getVirtualAddress();

    public abstract void setVirtualAddress(Address virtualAddress);

    public abstract WordField getFileSize();

    public abstract void setFileSize(WordField fileSize);

    public abstract WordField getMemorySize();

    public abstract void setMemorySize(WordField memorySize);

    public abstract WordField getAlignment();

    public abstract void setAlignment(WordField alignment);

    public abstract int getFlagsField();

    public abstract void setFlagsField(int flagsField);

    public EnumSet<SegmentFlag> getFlags() {
        return SegmentFlag.setFromBitValue(getFlagsField());
    }

    public void setFlags(EnumSet<SegmentFlag> flags) {
        setFlagsField(SegmentFlag.toBitValue(flags));
    }
}
