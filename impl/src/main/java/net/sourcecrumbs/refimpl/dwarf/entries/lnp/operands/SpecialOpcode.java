/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands;

import net.sourcecrumbs.refimpl.dwarf.entries.lnp.LineNumberProgramHeader;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberRow;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberState;

/**
 * Helper for computing the effect of a special opcode
 *
 * @author mcnulty
 */
public final class SpecialOpcode {

    public static LineNumberRow apply(short opcodeValue, LineNumberProgramHeader header, LineNumberState state) {
        if (header.getVersion() == 2) {
            int adjustedOpcode = opcodeValue - header.getOpcodeBase();
            int addressAdvance = adjustedOpcode / header.getLineRange();
            int lineIncrement = header.getLineBase() + (adjustedOpcode % header.getLineRange());

            state.setLine(state.getLine() + lineIncrement);
            state.setAddress(state.getAddress() + (addressAdvance*header.getMinimumInstructionLength()));
            LineNumberRow row = state.createRow();
            state.setBasicBlockEntry(false);
            state.setPrologueEnd(false);
            state.setEpilogueBegin(false);
            state.setDiscriminator(0);

            return row;
        }else{
            throw new UnsupportedOperationException("Line number program with version " + header.getVersion() + " is not supported");
        }
    }
}
