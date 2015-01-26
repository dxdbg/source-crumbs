/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.constants;

import org.codehaus.preon.annotation.BoundEnumOption;

/**
 * Enumeration indicating whether a DIE has children or not
 *
 * @author mcnulty
 */
public enum ChildrenPresent {

    @BoundEnumOption(0x00)
    DW_CHILDREN_no,

    @BoundEnumOption(0x01)
    DW_CHILDREN_yes,

    UNKNOWN;
}
