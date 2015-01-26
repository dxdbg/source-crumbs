/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp;

import java.util.List;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.If;

import net.sourcecrumbs.refimpl.dwarf.preon.ElementTerminatedList;
import net.sourcecrumbs.refimpl.dwarf.types.ListString;
import net.sourcecrumbs.refimpl.dwarf.types.InitialLength;

/**
 * Header for a line number program
 *
 * @author mcnulty
 */
public class LineNumberProgramHeader {

    @Bound
    private InitialLength unitLength;

    @BoundNumber(size = "16")
    private short version;

    @BoundNumber(size = "unitLength.offsetLength")
    private long headerLength;

    @BoundNumber(size = "8")
    private byte minimumInstructionLength;

    @If("version > 2")
    @BoundNumber(size = "8")
    private byte maximumOperationsPerInstruction;

    @BoundNumber(size = "8")
    private byte defaultIsStmt;

    @BoundNumber(size = "8")
    private byte lineBase;

    @BoundNumber(size = "8")
    private byte lineRange;

    @BoundNumber(size = "8")
    private short opcodeBase;

    @BoundList(size = "opcodeBase - 1", type=byte.class)
    private byte[] standardOpcodeLengths;

    @ElementTerminatedList(elementType = ListString.class)
    @Bound
    private List<ListString> includeDirectories;

    @ElementTerminatedList(elementType = FileEntry.class)
    @Bound
    private List<FileEntry> files;

    public InitialLength getUnitLength() {
        return unitLength;
    }

    public void setUnitLength(InitialLength unitLength) {
        this.unitLength = unitLength;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public long getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(long headerLength) {
        this.headerLength = headerLength;
    }

    public byte getMinimumInstructionLength() {
        return minimumInstructionLength;
    }

    public void setMinimumInstructionLength(byte minimumInstructionLength) {
        this.minimumInstructionLength = minimumInstructionLength;
    }

    public byte getMaximumOperationsPerInstruction() {
        return maximumOperationsPerInstruction;
    }

    public void setMaximumOperationsPerInstruction(byte maximumOperationsPerInstruction) {
        this.maximumOperationsPerInstruction = maximumOperationsPerInstruction;
    }

    public byte getDefaultIsStmt() {
        return defaultIsStmt;
    }

    public void setDefaultIsStmt(byte defaultIsStmt) {
        this.defaultIsStmt = defaultIsStmt;
    }

    public byte getLineBase() {
        return lineBase;
    }

    public void setLineBase(byte lineBase) {
        this.lineBase = lineBase;
    }

    public byte getLineRange() {
        return lineRange;
    }

    public void setLineRange(byte lineRange) {
        this.lineRange = lineRange;
    }

    public short getOpcodeBase() {
        return opcodeBase;
    }

    public void setOpcodeBase(short opcodeBase) {
        this.opcodeBase = opcodeBase;
    }

    public byte[] getStandardOpcodeLengths() {
        return standardOpcodeLengths;
    }

    public void setStandardOpcodeLengths(byte[] standardOpcodeLengths) {
        this.standardOpcodeLengths = standardOpcodeLengths;
    }

    public List<ListString> getIncludeDirectories() {
        return includeDirectories;
    }

    public void setIncludeDirectories(List<ListString> includeDirectories) {
        this.includeDirectories = includeDirectories;
    }

    public List<FileEntry> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntry> files) {
        this.files = files;
    }
}
