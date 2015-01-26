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
 * Line number operation that sets the epilogue begin register to true
 *
 * @author mcnulty
 */
public enum SetEpilogueBegin implements LineNumberOperation {

    INSTANCE;

    @Override
    public LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state) {
        state.setEpilogueBegin(true);

        return null;
    }
}
