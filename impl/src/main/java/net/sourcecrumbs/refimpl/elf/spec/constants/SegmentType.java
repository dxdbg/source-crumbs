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
 * The type of a segment in an ELF file
 *
 * @author mcnulty
 */
public enum SegmentType {

    @BoundEnumOption(0)
    PT_NULL,

    @BoundEnumOption(1)
    PT_LOAD,

    @BoundEnumOption(2)
    PT_DYNAMIC,

    @BoundEnumOption(3)
    PT_INTERP,

    @BoundEnumOption(4)
    PT_NOTE,

    @BoundEnumOption(5)
    PT_SHLIB,

    @BoundEnumOption(6)
    PT_PHDR,

    @BoundEnumOption(0x70000000)
    PT_LOPROC,

    @BoundEnumOption(0x7fffffff)
    PT_HIPROC,

    UNKNOWN;
}
