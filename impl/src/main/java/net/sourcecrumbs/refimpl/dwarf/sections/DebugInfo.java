/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.sections;

import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.codehaus.preon.annotation.BoundList;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.dwarf.constants.AbbreviationTag;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeName;
import net.sourcecrumbs.refimpl.dwarf.entries.AbbreviationTable;
import net.sourcecrumbs.refimpl.dwarf.entries.AttributeValue;
import net.sourcecrumbs.refimpl.dwarf.entries.CompilationUnit;
import net.sourcecrumbs.refimpl.elf.spec.sections.SectionContent;

/**
 * Represents a .debug_info section in an ELF file
 *
 * @author mcnulty
 */
public class DebugInfo implements SectionContent {

    public static final String SECTION_NAME = ".debug_info";

    // This assumes that the data used to decode this section is limited to just this section
    @BoundList(type = CompilationUnit.class)
    private List<CompilationUnit> compilationUnits;

    private TreeMap<Long, CompilationUnit> unitsByStartingAddress = new TreeMap<>();

    private ByteOrder byteOrder = null;

    /**
     * Build the DIEs, given information available in the DebugAbbrev section
     *
     * @param debugAbbrev the DebugAbbrev section
     * @param debugStr the DebugStr section
     * @param byteOrder the byte order of the target machine
     *
     * @throws UnknownFormatException when the data is not in the expected format
     */
    public void buildDIEs(DebugAbbrev debugAbbrev, DebugStr debugStr, ByteOrder byteOrder) throws UnknownFormatException
    {
        this.byteOrder = byteOrder;

        long currentOffset = 0L;
        for (CompilationUnit compilationUnit : compilationUnits) {
            for (AbbreviationTable abbrevTable : debugAbbrev.getAbbreviationTables()) {
                if (abbrevTable.getSectionOffset() == compilationUnit.getHeader().getDebugAbbrevOffset()) {
                    compilationUnit.buildDIEs(abbrevTable, currentOffset, debugStr, byteOrder);
                }
            }
            if (compilationUnit.getRootDIE().getTag() == AbbreviationTag.DW_TAG_compile_unit) {
                for (AttributeValue value : compilationUnit.getRootDIE().getAttributeValues()) {
                    if (value.getName() == AttributeName.DW_AT_low_pc) {
                        unitsByStartingAddress.put(value.getDataAsLong(byteOrder), compilationUnit);
                        break;
                    }
                }
            }
            currentOffset = compilationUnit.getRootDIE().getChildrenEndOffset() + 1;
        }
    }

    /**
     * Retrieves the compilation unit that contains an address
     *
     * @param containingAddress the containing address
     *
     * @return the compilation unit
     */
    public CompilationUnit getCompilationUnit(Long containingAddress) {
        return unitsByStartingAddress.floorEntry(containingAddress).getValue();
    }

    public List<CompilationUnit> getCompilationUnits()
    {
        return new LinkedList<>(compilationUnits);
    }

    public ByteOrder getByteOrder()
    {
        return byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder)
    {
        this.byteOrder = byteOrder;
    }
}
