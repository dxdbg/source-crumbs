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
