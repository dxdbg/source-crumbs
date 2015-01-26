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
 * Line number instruction that advances the address and
 *
 * @author mcnulty
 */
public enum ConstAddPc implements LineNumberOperation {

    INSTANCE;

    @Override
    public LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state) {
        if (header.getVersion() == 2) {
            int adjustedOpcode = 255 - header.getOpcodeBase();
            int addressAdvance = adjustedOpcode / header.getLineRange();
            state.setAddress(state.getAddress() + addressAdvance);

            return null;
        }else{
            throw new UnsupportedOperationException("Line number program with version " + header.getVersion() + " is not supported");
        }

    }
}
