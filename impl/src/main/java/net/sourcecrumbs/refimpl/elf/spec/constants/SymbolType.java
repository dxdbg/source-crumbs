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
 * The type of an ELF symbol
 *
 * @author mcnulty
 */
public enum SymbolType {

    @BoundEnumOption(0)
    STT_NOTYPE,

    @BoundEnumOption(1)
    STT_OBJECT,

    @BoundEnumOption(2)
    STT_FUNC,

    @BoundEnumOption(3)
    STT_SECTION,

    @BoundEnumOption(4)
    STT_FILE,

    @BoundEnumOption(13)
    STT_LOPROC,

    @BoundEnumOption(15)
    STT_HIPROC,

    UNKNOWN;
}
