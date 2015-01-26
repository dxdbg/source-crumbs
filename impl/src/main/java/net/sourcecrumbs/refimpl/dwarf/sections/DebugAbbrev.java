/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.sections;

import java.util.List;

import org.codehaus.preon.annotation.BoundList;

import net.sourcecrumbs.refimpl.dwarf.entries.AbbreviationTable;
import net.sourcecrumbs.refimpl.elf.spec.sections.SectionContent;

/**
 * Represents a .debug_abbrev section in an ELF file
 *
 * @author mcnulty
 */
public class DebugAbbrev implements SectionContent {

    public static final String SECTION_NAME = ".debug_abbrev";

    // This assumes that the data used to decode this section is limited to just this section
    @BoundList(type = AbbreviationTable.class)
    private List<AbbreviationTable> abbreviationTables;

    public List<AbbreviationTable> getAbbreviationTables() {
        return abbreviationTables;
    }

    public void setAbbreviationTables(List<AbbreviationTable> abbreviationTables) {
        this.abbreviationTables = abbreviationTables;
    }
}
