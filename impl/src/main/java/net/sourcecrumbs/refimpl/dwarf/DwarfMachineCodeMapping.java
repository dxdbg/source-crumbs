/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourcecrumbs.api.Range;
import net.sourcecrumbs.api.machinecode.MachineCodeMapping;
import net.sourcecrumbs.api.machinecode.SourceLine;
import net.sourcecrumbs.api.machinecode.SourceLineRange;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeName;
import net.sourcecrumbs.refimpl.dwarf.entries.AttributeValue;
import net.sourcecrumbs.refimpl.dwarf.entries.CompilationUnit;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.LineNumberProgram;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberRow;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugLine;

/**
 * Implementation of MachineCodeMapping, backed by DWARF
 *
 * @author mcnulty
 */
public class DwarfMachineCodeMapping implements MachineCodeMapping {

    private static final Logger logger = LoggerFactory.getLogger(DwarfMachineCodeMapping.class);

    private final DebugInfo debugInfo;
    private final DebugLine debugLine;

    /**
     * Constructor.
     *
     * @param debugInfo the DebugInfo section
     * @param debugLine the DebugLine section
     */
    public DwarfMachineCodeMapping(DebugInfo debugInfo, DebugLine debugLine) {
        this.debugInfo = debugInfo;
        this.debugLine = debugLine;
    }

    private List<LineNumberRow> getLineNumberRows(long address) {
        CompilationUnit compilationUnit = debugInfo.getCompilationUnit(address);
        if (compilationUnit != null) {
            return getLineNumberRows(compilationUnit, address);
        }
        return new LinkedList<>();
    }

    private List<LineNumberRow> getLineNumberRows(CompilationUnit compilationUnit, long address) {
        for (AttributeValue value : compilationUnit.getRootDIE().getAttributeValues()) {
            if (value.getName() == AttributeName.DW_AT_stmt_list) {
                long lnpOffset = value.getDataAsLong();
                LineNumberProgram lnp = debugLine.getLineNumberProgram(lnpOffset);
                if (lnp != null) {
                    return lnp.getLineNumberRowsByAddress(address);
                }
                break;
            }
        }
        return new LinkedList<>();
    }

    private LineNumberRow getFirstLineNumberRow(long address) {
        List<LineNumberRow> rows = getLineNumberRows(address);
        if (rows != null && rows.size() > 0) {
            return rows.get(0);
        }
        return null;
    }

    private List<LineNumberRow> getLineNumberRows(CompilationUnit compilationUnit, int line) {
        for (AttributeValue value : compilationUnit.getRootDIE().getAttributeValues()) {
            if (value.getName() == AttributeName.DW_AT_stmt_list) {
                long lnpOffset = value.getDataAsLong();
                LineNumberProgram lnp = debugLine.getLineNumberProgram(lnpOffset);
                if (lnp != null) {
                    return lnp.getLineNumberRowsByLine(line);
                }
                break;
            }
        }
        return new LinkedList<>();
    }

    private LineNumberRow getFirstLineNumberRow(CompilationUnit compilationUnit, int line) {
        List<LineNumberRow> rows = getLineNumberRows(compilationUnit, line);
        if (rows != null && rows.size() > 0) {
            return rows.get(0);
        }

        return null;
    }

    @Override
    public List<Range<Long>> getMachineCodeRanges(SourceLine sourceLine) {
        List<Range<Long>> ranges;
        if (sourceLine.getTranslationUnit() instanceof CompilationUnit) {
            List<LineNumberRow> rows = getLineNumberRows((CompilationUnit)sourceLine.getTranslationUnit(), sourceLine.getLine());

            Collections.sort(rows,
                    new Comparator<LineNumberRow>() {

                        @Override
                        public int compare(LineNumberRow o1, LineNumberRow o2) {
                            return Long.compare(o1.getAddress(), o2.getAddress());
                        }
                    }
            );

            ranges = new LinkedList<>();

            LineNumberRow start = null;
            LineNumberRow last = null;
            for (LineNumberRow row : rows) {
                if (start == null) {
                    start = row;
                    last = row;
                }else{
                    if (row.getAddress() != (last.getAddress() + 1) && row.getAddress() != last.getAddress()) {
                        ranges.add(new Range<Long>(start.getAddress(), last.getAddress()));
                        start = row;
                        last = row;
                    }else{
                        last = row;
                    }
                }
            }
        }else{
            logger.warn("specified translation unit doesn't implement expected interface");
            ranges = new LinkedList<>();
        }

        return ranges;
    }

    @Override
    public List<SourceLineRange> getSourceLinesRanges(long machineCodeAddress) {

        CompilationUnit compilationUnit = debugInfo.getCompilationUnit(machineCodeAddress);
        if (compilationUnit != null) {
            List<LineNumberRow> rows = getLineNumberRows(machineCodeAddress);

            Collections.sort(rows, new Comparator<LineNumberRow>() {

                @Override
                public int compare(LineNumberRow o1, LineNumberRow o2) {
                    return Integer.compare(o1.getLine(), o2.getLine());
                }
            });

            List<SourceLineRange> ranges = new LinkedList<>();
            if (rows.size() == 1) {
                SourceLineRange sourceLineRange = new SourceLineRange();
                sourceLineRange.setTranslationUnit(compilationUnit);
                sourceLineRange.setLineRange(new Range<Integer>(rows.get(0).getLine(), rows.get(0).getLine()));
                ranges.add(sourceLineRange);
            }else {
                LineNumberRow start = null;
                LineNumberRow last = null;
                for (LineNumberRow row : rows) {
                    if (start == null) {
                        start = row;
                        last = row;
                    } else {
                        if (row.getLine() != (last.getLine() + 1) && row.getLine() != last.getLine()) {
                            SourceLineRange sourceLineRange = new SourceLineRange();
                            sourceLineRange.setTranslationUnit(compilationUnit);
                            sourceLineRange.setLineRange(new Range<Integer>(start.getLine(), last.getLine()));
                            ranges.add(sourceLineRange);
                            start = row;
                            last = row;
                        } else {
                            last = row;
                        }
                    }
                }
            }

            return ranges;
        }

        return new LinkedList<>();
    }

    private long getNextStatementAddress(LineNumberRow row) {
        if (row != null) {
            LineNumberRow next = row;
            do {
                next = next.getNext();
                if (next == null) {
                    break;
                }
            }while(!next.isStatement() || next.getAddress() == row.getAddress());

            if (next != null) {
                return next.getAddress();
            }
        }

        return 0;
    }

    @Override
    public long getNextStatementAddress(SourceLine sourceLine) {
        if (sourceLine.getTranslationUnit() instanceof CompilationUnit) {
            LineNumberRow row = getFirstLineNumberRow((CompilationUnit)sourceLine.getTranslationUnit(),
                    sourceLine.getLine());
            return getNextStatementAddress(row);
        }else{
            logger.warn("specified translation unit doesn't implement expected interface");
        }
        return 0;
    }

    @Override
    public long getNextStatementAddress(long address) {
        return getNextStatementAddress(getFirstLineNumberRow(address));
    }

    private long getStatementAddress(LineNumberRow row) {
        if (row != null) {
            LineNumberRow next = row;
            while (!next.isStatement()) {
                next = next.getNext();
                if (next == null) {
                    break;
                }
            }

            if (next != null) {
                return next.getAddress();
            }
        }

        return 0;
    }

    @Override
    public long getStatementAddress(long address) {
        return getStatementAddress(getFirstLineNumberRow(address));
    }

    @Override
    public long getStatementAddress(SourceLine sourceLine) {
        if (sourceLine.getTranslationUnit() instanceof CompilationUnit) {
            LineNumberRow row = getFirstLineNumberRow((CompilationUnit)sourceLine.getTranslationUnit(),
                    sourceLine.getLine());
            return getStatementAddress(row);
        }else{
            logger.warn("specified translation unit doesn't implement expected interface");
        }

        return 0;
    }
}
