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

import net.sourcecrumbs.refimpl.dwarf.entries.lnp.LineNumberProgram;
import net.sourcecrumbs.refimpl.elf.spec.sections.SectionContent;

/**
 * Represents a .debug_line section in an ELF file
 *
 * @author mcnulty
 */
public class DebugLine implements SectionContent {

    public static final String SECTION_NAME = ".debug_line";

    @BoundList(type = LineNumberProgram.class)
    private List<LineNumberProgram> lineNumberPrograms;

    public LineNumberProgram getLineNumberProgram(long offset) {
        for (LineNumberProgram lineNumberProgram : lineNumberPrograms) {
            if (lineNumberProgram.getSectionOffset() == offset) {
                return lineNumberProgram;
            }
        }

        return null;
    }
}
