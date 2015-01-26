/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.sections;

import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;

/**
 * Represents a .debug_str section in an ELF file
 *
 * @author mcnulty
 */
public class DebugStr extends StringTable {

    public static final String SECTION_NAME = ".debug_str";
}
