/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.constants;

import org.codehaus.preon.annotation.BoundEnumOption;

/**
 * An ELF symbols binding
 *
 * @author mcnulty
 */
public enum SymbolBinding {

    @BoundEnumOption(0)
    STB_LOCAL,

    @BoundEnumOption(1)
    STB_GLOBAL,

    @BoundEnumOption(2)
    STB_WEAK,

    @BoundEnumOption(13)
    STB_LOPROC,

    @BoundEnumOption(15)
    STB_HIPROC,

    UNKNOWN;
}
