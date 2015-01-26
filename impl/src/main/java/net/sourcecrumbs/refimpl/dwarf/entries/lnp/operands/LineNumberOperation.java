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
 * Interface representing the semantics of a specific line number instruction
 *
 * @author mcnulty
 */
public interface LineNumberOperation {

    /**
     * Applies this line number operation to the specified state
     *
     * @param header the header for the line number program
     * @param state the curent state
     *
     * @return the row produced by this instruction
     */
    LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state);
}
