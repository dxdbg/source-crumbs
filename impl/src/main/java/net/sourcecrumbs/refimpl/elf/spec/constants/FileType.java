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
 * ELF file types
 *
 * @author mcnulty
 */
public enum FileType {

    @BoundEnumOption(0)
    ET_NONE,

    @BoundEnumOption(1)
    ET_REL,

    @BoundEnumOption(2)
    ET_EXEC,

    @BoundEnumOption(3)
    ET_DYN,

    @BoundEnumOption(4)
    ET_CORE,

    @BoundEnumOption(0xff00)
    ET_LOPROC,

    @BoundEnumOption(0xffff)
    ET_HIPROC,

    UNKNOWN;
}
