/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands;

import org.codehaus.preon.annotation.BoundNumber;

import net.sourcecrumbs.refimpl.dwarf.entries.lnp.LineNumberProgramHeader;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberRow;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberState;

/**
 * Line number operation that advances the address register the operand contained
 *
 * @author mcnulty
 */
public class FixedAdvancePc implements LineNumberOperation {

    @BoundNumber(size = "16")
    private short advance;

    @Override
    public LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state) {
        state.setAddress(state.getAddress() + advance);

        return null;
    }
}
