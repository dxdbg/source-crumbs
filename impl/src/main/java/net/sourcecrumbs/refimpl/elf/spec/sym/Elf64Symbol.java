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

package net.sourcecrumbs.refimpl.elf.spec.sym;

import static net.sourcecrumbs.refimpl.elf.spec.constants.DataTypeSizes.*;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;

import net.sourcecrumbs.refimpl.elf.spec.Address;
import net.sourcecrumbs.refimpl.elf.spec.WordField;

/**
 * Symbol for 64-bit ELF files
 *
 * @author mcnulty
 */
public class Elf64Symbol extends ElfSymbol {

    @BoundNumber(size = ElfWord)
    private int nameIndex;

    @BoundNumber(size = UnsignedChar)
    private byte info;

    @BoundNumber(size = UnsignedChar)
    private byte other;

    @BoundNumber(size = ElfHalf)
    private short sectionIndex;

    @Bound
    private Address value;

    @Bound
    private WordField size;

    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public Address getValue() {
        return value;
    }

    public void setValue(Address value) {
        this.value = value;
    }

    public WordField getSize() {
        return size;
    }

    public void setSize(WordField size) {
        this.size = size;
    }

    public byte getInfo() {
        return info;
    }

    public void setInfo(byte info) {
        this.info = info;
    }

    public byte getOther() {
        return other;
    }

    public void setOther(byte other) {
        this.other = other;
    }

    public short getSectionIndex() {
        return sectionIndex;
    }

    public void setSectionIndex(short sectionIndex) {
        this.sectionIndex = sectionIndex;
    }
}
