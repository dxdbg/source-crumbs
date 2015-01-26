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
 * A tag for a DynamicEntry
 *
 * @author mcnulty
 */
public enum DynamicTag {

    @BoundEnumOption(0)
    DT_NULL,

    @BoundEnumOption(1)
    DT_NEEDED,

    @BoundEnumOption(2)
    DT_PLTRELSZ,

    @BoundEnumOption(3)
    DT_PLTGOT,

    @BoundEnumOption(4)
    DT_HASH,

    @BoundEnumOption(5)
    DT_STRTAB,

    @BoundEnumOption(6)
    DT_SYMTAB,

    @BoundEnumOption(7)
    DT_RELA,

    @BoundEnumOption(8)
    DT_RELASZ,

    @BoundEnumOption(9)
    DT_RELAENT,

    @BoundEnumOption(10)
    DT_STRSZ,

    @BoundEnumOption(11)
    DT_SYMENT,

    @BoundEnumOption(12)
    DT_INIT,

    @BoundEnumOption(13)
    DT_FINI,

    @BoundEnumOption(14)
    DT_SONAME,

    @BoundEnumOption(15)
    DT_RPATH,

    @BoundEnumOption(16)
    DT_SYMBOLIC,

    @BoundEnumOption(17)
    DT_REL,

    @BoundEnumOption(18)
    DT_RELSZ,

    @BoundEnumOption(19)
    DT_RELENT,

    @BoundEnumOption(20)
    DT_PLTREL,

    @BoundEnumOption(21)
    DT_DEBUG,

    @BoundEnumOption(22)
    DT_TEXTREL,

    @BoundEnumOption(23)
    DT_JMPREL,

    @BoundEnumOption(24)
    DT_BIND_NOW,

    @BoundEnumOption(25)
    DT_INIT_ARRAY,

    @BoundEnumOption(26)
    DT_FINI_ARRAY,

    @BoundEnumOption(27)
    DT_INIT_ARRAYSZ,

    @BoundEnumOption(28)
    DT_FINI_ARRAYSZ,

    @BoundEnumOption(29)
    DT_RUNPATH,

    @BoundEnumOption(30)
    DT_FLAGS,

    @BoundEnumOption(32)
    DT_PREINIT_ARRAY,

    @BoundEnumOption(33)
    DT_PREINIT_ARRAYSZ,

    @BoundEnumOption(0x70000000)
    DT_LOPROC,

    @BoundEnumOption(0x7fffffff)
    DT_HIPROC,

    UNKNOWN;
}
