/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sym;

import static net.sourcecrumbs.refimpl.elf.spec.constants.DataTypeSizes.*;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;

import net.sourcecrumbs.refimpl.elf.spec.Address;
import net.sourcecrumbs.refimpl.elf.spec.WordField;

/**
 * Symbol for 32-bit ELF files
 *
 * @author mcnulty
 */
public class Elf32Symbol extends ElfSymbol {

    @BoundNumber(size = ElfWord)
    private int nameIndex;

    @Bound
    private Address value;

    @Bound
    private WordField size;

    @BoundNumber(size = UnsignedChar)
    private byte info;

    @BoundNumber(size = UnsignedChar)
    private byte other;

    @BoundNumber(size = ElfHalf)
    private short sectionIndex;

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
