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

package net.sourcecrumbs.refimpl.dwarf.entries.lnp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.Slice;

import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberRow;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberState;
import net.sourcecrumbs.refimpl.dwarf.preon.SectionOffset;

/**
 * The line number information for a specific compilation unit
 *
 * @author mcnulty
 */
public class LineNumberProgram implements SectionOffset {

    @Bound
    private LineNumberProgramHeader header = null;

    @BoundList(type = LineNumberInstruction.class)
    @Slice(size = "(header.unitLength.length - 2 - (header.unitLength.offsetLength/8) - header.headerLength)*8")
    private List<LineNumberInstruction> instructions = null;

    private Map<Long, LineNumberRow> lineNumberMatrix = new HashMap<>();

    private long sectionOffset;

    @Override
    public long getSectionOffset() {
        return sectionOffset;
    }

    @Override
    public void setSectionOffset(long sectionOffset) {
        this.sectionOffset = sectionOffset;
    }

    private void buildLineNumberMatrix() {
        LineNumberState state = new LineNumberState(header);
        LineNumberRow previous = null;
        for (LineNumberInstruction instruction : instructions) {
            LineNumberRow row = instruction.apply(header, state);
            if (row != null) {
               lineNumberMatrix.put(row.getAddress(), row);
                if (previous != null) {
                    previous.setNext(row);
                    row.setPrevious(previous);
                }
                previous = row;
            }

            // The current sequence has ended, reinitialize the context
            if (state.isEndSequence()) {
                state = new LineNumberState(header);
            }
        }
    }

    public LineNumberRow getLineNumberRow(long address) {
        if (lineNumberMatrix.size() == 0) {
            synchronized (this) {
                if (lineNumberMatrix.size() == 0) {
                    buildLineNumberMatrix();
                }
            }
        }

        return lineNumberMatrix.get(address);
    }
}
