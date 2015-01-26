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
 * Enum for EI_CLASS values
 *
 * @author mcnulty
 */
public enum ElfClass {

    @BoundEnumOption(0)
    ELFCLASSNONE,

    @BoundEnumOption(1)
    ELFCLASS32,

    @BoundEnumOption(2)
    ELFCLASS64,

    UNKNOWN;
}
