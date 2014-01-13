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

import net.sourcecrumbs.refimpl.elf.spec.constants.SectionType;


/**
 * Header for section in an ELF file
 *
 * @author mcnulty
 */
public class ElfSectionHeader {

    @BoundNumber(size = ElfWord)
    private int nameIndex;

    @BoundNumber(size = ElfWord)
    private SectionType type;

    @Bound
    private WordField flags;

    @Bound
    private Address address;

    @Bound
    private Offset offset;

    @Bound
    private WordField size;

    @BoundNumber(size = ElfWord)
    private int link;

    @BoundNumber(size = ElfWord)
    private int info;

    @Bound
    private WordField addressAlign;

    @Bound
    private WordField entrySize;

    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType type) {
        this.type = type;
    }

    public WordField getFlags() {
        return flags;
    }

    public void setFlags(WordField flags) {
        this.flags = flags;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Offset getOffset() {
        return offset;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

    public WordField getSize() {
        return size;
    }

    public void setSize(WordField size) {
        this.size = size;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public WordField getAddressAlign() {
        return addressAlign;
    }

    public void setAddressAlign(WordField addressAlign) {
        this.addressAlign = addressAlign;
    }

    public WordField getEntrySize() {
        return entrySize;
    }

    public void setEntrySize(WordField entrySize) {
        this.entrySize = entrySize;
    }
}
