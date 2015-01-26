/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sections;

import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.If;

import net.sourcecrumbs.refimpl.elf.spec.rel.AddendRelocation;

/**
 * A table of relocations with an explicit addend in an ELF section
 *
 * @author mcnulty
 */
public class AddendRelocationTable implements SectionContent {

    @If("outer.sectionHeader.entrySize.value > 0")
    @BoundList(size = "outer.sectionHeader.size.value / outer.sectionHeader.entrySize.value")
    private AddendRelocation[] relocations;

    public AddendRelocation[] getRelocations() {
        return relocations;
    }

    public void setRelocations(AddendRelocation[] relocations) {
        this.relocations = relocations;
    }
}
