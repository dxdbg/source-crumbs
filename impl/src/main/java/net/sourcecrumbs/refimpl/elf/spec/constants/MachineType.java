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
 * The target machine for an ELF file
 *
 * @author mcnulty
 */
public enum MachineType {

    @BoundEnumOption(0)
    EM_NONE,

    @BoundEnumOption(1)
    EM_M32,

    @BoundEnumOption(2)
    EM_SPARC,

    @BoundEnumOption(3)
    EM_386,

    @BoundEnumOption(4)
    EM_68K,

    @BoundEnumOption(5)
    EM_88K,

    @BoundEnumOption(7)
    EM_860,

    @BoundEnumOption(8)
    EM_MIPS,

    @BoundEnumOption(10)
    EM_MIPS_RS4_BE,

    @BoundEnumOption(62)
    EM_X86_64,

    UNKNOWN;
}
