/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm;

/**
 * Bean for the state machine registers for a line number program
 *
 * @author mcnulty
 */
public abstract class LineNumberRegisters {

    protected long address = 0;

    protected int opIndex = 0;

    protected int file = 1;

    protected int line = 1;

    protected int column = 0;

    protected boolean statement;

    protected boolean basicBlockEntry = false;

    protected boolean endSequence = false;

    protected boolean prologueEnd = false;

    protected boolean epilogueBegin = false;

    protected int isa = 0;

    protected int discriminator = 0;

    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    public int getOpIndex() {
        return opIndex;
    }

    public void setOpIndex(int opIndex) {
        this.opIndex = opIndex;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isStatement() {
        return statement;
    }

    public void setStatement(boolean statement) {
        this.statement = statement;
    }

    public boolean isBasicBlockEntry() {
        return basicBlockEntry;
    }

    public void setBasicBlockEntry(boolean basicBlockEntry) {
        this.basicBlockEntry = basicBlockEntry;
    }

    public boolean isEndSequence() {
        return endSequence;
    }

    public void setEndSequence(boolean endSequence) {
        this.endSequence = endSequence;
    }

    public boolean isPrologueEnd() {
        return prologueEnd;
    }

    public void setPrologueEnd(boolean prologueEnd) {
        this.prologueEnd = prologueEnd;
    }

    public boolean isEpilogueBegin() {
        return epilogueBegin;
    }

    public void setEpilogueBegin(boolean epilogueBegin) {
        this.epilogueBegin = epilogueBegin;
    }

    public int getIsa() {
        return isa;
    }

    public void setIsa(int isa) {
        this.isa = isa;
    }

    public int getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(int discriminator) {
        this.discriminator = discriminator;
    }
}
