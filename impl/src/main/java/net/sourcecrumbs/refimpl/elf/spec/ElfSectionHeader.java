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

import static net.sourcecrumbs.refimpl.elf.spec.constants.DataTypeSizes.*;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;

import net.sourcecrumbs.refimpl.elf.spec.constants.SectionFlag;
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
    private WordField flagsField;

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

    public WordField getFlagsField() {
        return flagsField;
    }

    public void setFlagsField(WordField flagsField) {
        this.flagsField = flagsField;
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

    public EnumSet<SectionFlag> getFlags() {
        return SectionFlag.setFromBitValue(flagsField.getValue());
    }

    public void setFlags(EnumSet<SectionFlag> flags) {
        long newValue = SectionFlag.toBitValue(flags);
        flagsField = new WordField(newValue);
    }
}
