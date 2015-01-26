/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec;

import static net.sourcecrumbs.refimpl.elf.spec.constants.DataTypeSizes.*;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;

import net.sourcecrumbs.refimpl.elf.spec.constants.SegmentType;

/**
 * Program header for a 64-bit ELF file
 *
 * @author mcnulty
 */
public class Elf64ProgramHeader extends ElfProgramHeader {

    @BoundNumber(size = ElfWord)
    private SegmentType type;

    @BoundNumber(size = ElfWord)
    private int flagsField;

    @Bound
    private Offset offset;

    @Bound
    private Address virtualAddress;

    @Bound
    private Address physicalAddress;

    @Bound
    private WordField fileSize;

    @Bound
    private WordField memorySize;

    @Bound
    private WordField alignment;

    public SegmentType getType() {
        return type;
    }

    public void setType(SegmentType type) {
        this.type = type;
    }

    public Offset getOffset() {
        return offset;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

    public Address getVirtualAddress() {
        return virtualAddress;
    }

    public void setVirtualAddress(Address virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    public Address getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(Address physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public WordField getFileSize() {
        return fileSize;
    }

    public void setFileSize(WordField fileSize) {
        this.fileSize = fileSize;
    }

    public WordField getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(WordField memorySize) {
        this.memorySize = memorySize;
    }

    public int getFlagsField() {
        return flagsField;
    }

    public void setFlagsField(int flagsField) {
        this.flagsField = flagsField;
    }

    public WordField getAlignment() {
        return alignment;
    }

    public void setAlignment(WordField alignment) {
        this.alignment = alignment;
    }}
