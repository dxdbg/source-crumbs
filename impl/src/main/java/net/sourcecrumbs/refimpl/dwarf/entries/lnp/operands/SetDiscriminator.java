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
 * A line number operation that sets the discriminator register
 *
 * @author mcnulty
 */
public class SetDiscriminator implements LineNumberOperation {

    @Bound
    @LEBSigned(false)
    private LEB128 value;

    @Override
    public LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state) {
        state.setDiscriminator(value.getValueAsInt());

        return null;
    }
}
