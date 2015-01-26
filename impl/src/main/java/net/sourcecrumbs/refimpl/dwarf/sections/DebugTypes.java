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
import java.util.List;

import org.codehaus.preon.annotation.BoundList;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.dwarf.entries.AbbreviationTable;
import net.sourcecrumbs.refimpl.dwarf.entries.TypeUnit;

/**
 * Represents a .debug_types section in an ELF file
 *
 * @author dmcnulty
 */
public class DebugTypes
{
    public static final String SECTION_NAME = ".debug_types";

    // This assumes that the data used to decode this section is limited to just this section
    @BoundList(type = TypeUnit.class)
    private List<TypeUnit> typeUnits;

    /**
     * Build the DIEs, given information available in the DebugAbbrev section
     *
     * @param debugAbbrev the DebugAbbrev section
     * @param byteOrder the byte order of the target machine
     *
     * @throws UnknownFormatException when the data is not in the expected format
     */
    public void buildDIEs(DebugAbbrev debugAbbrev, ByteOrder byteOrder) throws UnknownFormatException
    {
        for (TypeUnit typeUnit : typeUnits) {
            for (AbbreviationTable abbrevTable : debugAbbrev.getAbbreviationTables()) {
                if (abbrevTable.getSectionOffset() == typeUnit.getHeader().getDebugAbbrevOffset()) {
                    typeUnit.buildDIEs(abbrevTable, byteOrder);
                }
            }
        }
    }
}
