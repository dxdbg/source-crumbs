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
 * The data encoding of the ELF file's processor-specific data
 *
 * @author mcnulty
 */
public enum DataEncoding {

    @BoundEnumOption(0)
    ELFDATANONE,

    @BoundEnumOption(1)
    ELFDATA2LSB,

    @BoundEnumOption(2)
    ELFDATA2MSB,

    UNKNOWN;
}
