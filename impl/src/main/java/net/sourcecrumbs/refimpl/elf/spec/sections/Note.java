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

/**
 * Note information in an ELF section
 *
 * @author mcnulty
 */
public class Note implements SectionContent {

    @BoundList(type=NoteEntry.class)
    private List<NoteEntry> entries;

    public List<NoteEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<NoteEntry> entries) {
        this.entries = entries;
    }
}
