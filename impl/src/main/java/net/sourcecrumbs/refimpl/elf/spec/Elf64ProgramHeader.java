/*
 * Copyright (c) 2011-2013, Dan McNulty
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the UDI project nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS AND CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
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
