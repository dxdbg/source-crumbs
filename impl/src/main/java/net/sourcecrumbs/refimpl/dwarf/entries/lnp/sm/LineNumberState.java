/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm;

import net.sourcecrumbs.refimpl.dwarf.entries.lnp.LineNumberProgramHeader;

/**
 * Represents the current state of the line number state machine
 *
 * @author mcnulty
 */
public class LineNumberState extends LineNumberRegisters {

    public LineNumberState(LineNumberProgramHeader header) {
        super();
        this.statement = (header.getDefaultIsStmt() != 0);
    }

    public LineNumberRow createRow() {
        LineNumberRow row = new LineNumberRow();
        row.address = this.address;
        row.opIndex = this.opIndex;
        row.file = this.file;
        row.line = this.line;
        row.column = this.column;
        row.statement = this.statement;
        row.basicBlockEntry = this.basicBlockEntry;
        row.endSequence = this.endSequence;
        row.prologueEnd = this.prologueEnd;
        row.epilogueBegin = this.epilogueBegin;
        row.isa = this.isa;
        row.discriminator = this.discriminator;

        return row;
    }
}
