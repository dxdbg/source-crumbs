/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sections;

import java.util.List;

import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.If;

import net.sourcecrumbs.refimpl.elf.spec.segments.SegmentContent;

/**
 * Dynamic linking information containing in an ELF section
 *
 * @author mcnulty
 */
public class DynamicLinkingInfo implements SectionContent {

    @If("outer.sectionHeader.entrySize.value > 0")
    @BoundList(size = "outer.sectionHeader.size.value / outer.sectionHeader.entrySize.value", type=DynamicEntry.class)
    private List<DynamicEntry> entries;

    public List<DynamicEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DynamicEntry> entries) {
        this.entries = entries;
    }
}
