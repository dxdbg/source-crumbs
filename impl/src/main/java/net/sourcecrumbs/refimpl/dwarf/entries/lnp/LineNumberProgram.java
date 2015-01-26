/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp;

import java.util.HashMap;
import java.util.LinkedList;
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

    private boolean lineNumberMatrixBuilt = false;

    private Map<Long, List<LineNumberRow>> lineNumberMatrixByAddr = new HashMap<>();

    private Map<Integer, List<LineNumberRow>> lineNumberMatrixByLine = new HashMap<>();

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
                List<LineNumberRow> rowsByAddr = lineNumberMatrixByAddr.get(row.getAddress());
                if (rowsByAddr == null) {
                    rowsByAddr = new LinkedList<>();
                    lineNumberMatrixByAddr.put(row.getAddress(), rowsByAddr);
                }
                rowsByAddr.add(row);

                List<LineNumberRow> rowsByLine = lineNumberMatrixByLine.get(row.getLine());
                if (rowsByLine == null) {
                    rowsByLine = new LinkedList<>();
                    lineNumberMatrixByLine.put(row.getLine(), rowsByLine);
                }
                rowsByLine.add(row);

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
        lineNumberMatrixBuilt = true;
    }

    public List<LineNumberRow> getLineNumberRowsByAddress(long address) {
        if (!lineNumberMatrixBuilt) {
            synchronized (this) {
                if (!lineNumberMatrixBuilt) {
                    buildLineNumberMatrix();
                }
            }
        }

        return lineNumberMatrixByAddr.get(address);
    }

    public List<LineNumberRow> getLineNumberRowsByLine(int line) {
        if (!lineNumberMatrixBuilt) {
            synchronized (this) {
                if (!lineNumberMatrixBuilt) {
                    buildLineNumberMatrix();
                }
            }
        }

        return lineNumberMatrixByLine.get(line);
    }
}
