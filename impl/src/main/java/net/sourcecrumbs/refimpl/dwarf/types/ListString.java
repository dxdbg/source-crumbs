/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.types;

import org.codehaus.preon.annotation.BoundString;

import net.sourcecrumbs.refimpl.dwarf.preon.ListTerminator;

/**
 * Wrapper around a String that allows a List to be terminated by an empty string
 *
 * @author mcnulty
 */
public class ListString implements ListTerminator {

    @BoundString
    private String value;

    @Override
    public boolean terminatesList() {
        return value == null || value.isEmpty();
    }

    public String getValue() {
        return value;
    }
}
