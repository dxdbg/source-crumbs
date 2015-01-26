/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands;

import org.codehaus.preon.annotation.Bound;

import net.sourcecrumbs.refimpl.dwarf.entries.lnp.LineNumberProgramHeader;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberRow;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberState;
import net.sourcecrumbs.refimpl.dwarf.preon.LEBSigned;
import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

/**
 * Line number operation that sets the isa value to the contained operand
 *
 * @author mcnulty
 */
public class SetIsa implements LineNumberOperation {

    @Bound
    @LEBSigned(false)
    private LEB128 isaValue;

    @Override
    public LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state) {
        state.setIsa(isaValue.getValueAsInt());

        return null;
    }
}
