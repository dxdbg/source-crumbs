/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.Init;

import net.sourcecrumbs.refimpl.dwarf.preon.ElementTerminatedList;
import net.sourcecrumbs.refimpl.dwarf.preon.SectionOffset;

/**
 * An abbreviation table from the .debug_abbrev section that may be referenced by multiple compilation units
 *
 * @author mcnulty
 */
public class AbbreviationTable implements SectionOffset {

    @Bound
    @ElementTerminatedList(elementType = AbbreviationDeclaration.class)
    private List<AbbreviationDeclaration> decls;

    private long sectionOffset;

    private Map<Integer, AbbreviationDeclaration> abbreviationsByCode = new HashMap<>();

    @Init
    public void init() {
        for (AbbreviationDeclaration decl : decls) {
            abbreviationsByCode.put(decl.getCode(), decl);
        }
    }

    public AbbreviationDeclaration getByCode(Integer code) {
        return abbreviationsByCode.get(code);
    }

    @Override
    public long getSectionOffset() {
        return sectionOffset;
    }

    @Override
    public void setSectionOffset(long sectionOffset) {
        this.sectionOffset = sectionOffset;
    }
}
