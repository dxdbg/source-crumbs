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
 * The type of section in an ELF file
 *
 * @author mcnulty
 */
public enum SectionType {

    @BoundEnumOption(0)
    SHT_NULL,

    @BoundEnumOption(1)
    SHT_PROGBITS,

    @BoundEnumOption(2)
    SHT_SYMTAB,

    @BoundEnumOption(3)
    SHT_STRTAB,

    @BoundEnumOption(4)
    SHT_RELA,

    @BoundEnumOption(5)
    SHT_HASH,

    @BoundEnumOption(6)
    SHT_DYNAMIC,

    @BoundEnumOption(7)
    SHT_NOTE,

    @BoundEnumOption(8)
    SHT_NOBITS,

    @BoundEnumOption(9)
    SHT_REL,

    @BoundEnumOption(10)
    SHT_SHLIB,

    @BoundEnumOption(11)
    SHT_DYNSYM,

    @BoundEnumOption(14)
    SHT_INIT_ARRAY,

    @BoundEnumOption(15)
    SHT_FINI_ARRAY,

    @BoundEnumOption(16)
    SHT_PREINIT_ARRAY,

    @BoundEnumOption(17)
    SHT_GROUP,

    @BoundEnumOption(18)
    SHT_SYMTAB_SHNDX,

    UNKNOWN;
}
