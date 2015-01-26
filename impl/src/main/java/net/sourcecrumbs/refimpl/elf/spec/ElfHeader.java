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

import net.sourcecrumbs.refimpl.elf.spec.constants.FileType;
import net.sourcecrumbs.refimpl.elf.spec.constants.MachineType;

/**
 * Header for an ELF file
 *
 * @author mcnulty
 */
public class ElfHeader {

    @Bound
    private ElfIdent ident;

    @BoundNumber(size = ElfHalf)
    private FileType fileType;

    @BoundNumber(size = ElfHalf)
    private MachineType machineType;

    @BoundNumber(size = ElfWord)
    private int version;

    @Bound
    private Address entry;

    @Bound
    private Offset programHeaderOffset;

    @Bound
    private Offset sectionHeaderOffset;

    @BoundNumber(size = ElfWord)
    private int flags;

    @BoundNumber(size = ElfHalf)
    private int headerSize;

    @BoundNumber(size = ElfHalf)
    private int programHeaderSize;

    @BoundNumber(size = ElfHalf)
    private int numProgramHeaders;

    @BoundNumber(size = ElfHalf)
    private int sectionHeaderSize;

    @BoundNumber(size = ElfHalf)
    private int numSectionHeaders;

    @BoundNumber(size = ElfHalf)
    private int sectionNameStrIndex;

    public ElfIdent getIdent() {
        return ident;
    }

    public void setIdent(ElfIdent ident) {
        this.ident = ident;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public MachineType getMachineType() {
        return machineType;
    }

    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Address getEntry() {
        return entry;
    }

    public void setEntry(Address entry) {
        this.entry = entry;
    }

    public Offset getProgramHeaderOffset() {
        return programHeaderOffset;
    }

    public void setProgramHeaderOffset(Offset programHeaderOffset) {
        this.programHeaderOffset = programHeaderOffset;
    }

    public Offset getSectionHeaderOffset() {
        return sectionHeaderOffset;
    }

    public void setSectionHeaderOffset(Offset sectionHeaderOffset) {
        this.sectionHeaderOffset = sectionHeaderOffset;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }

    public int getProgramHeaderSize() {
        return programHeaderSize;
    }

    public void setProgramHeaderSize(int programHeaderSize) {
        this.programHeaderSize = programHeaderSize;
    }

    public int getNumProgramHeaders() {
        return numProgramHeaders;
    }

    public void setNumProgramHeaders(int numProgramHeaders) {
        this.numProgramHeaders = numProgramHeaders;
    }

    public int getSectionHeaderSize() {
        return sectionHeaderSize;
    }

    public void setSectionHeaderSize(int sectionHeaderSize) {
        this.sectionHeaderSize = sectionHeaderSize;
    }

    public int getNumSectionHeaders() {
        return numSectionHeaders;
    }

    public void setNumSectionHeaders(int numSectionHeaders) {
        this.numSectionHeaders = numSectionHeaders;
    }

    public int getSectionNameStrIndex() {
        return sectionNameStrIndex;
    }

    public void setSectionNameStrIndex(int sectionNameStrIndex) {
        this.sectionNameStrIndex = sectionNameStrIndex;
    }
}
