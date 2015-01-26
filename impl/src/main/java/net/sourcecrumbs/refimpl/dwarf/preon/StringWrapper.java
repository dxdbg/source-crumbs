/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.preon;

import org.codehaus.preon.annotation.BoundString;
import org.codehaus.preon.annotation.Init;

/**
 * A wrapper around a string that allows you to use the length of the String during decoding
 *
 * @author mcnulty
 */
public class StringWrapper {

    @BoundString
    private String value;

    private int length;

    @Init
    public void init() {
        length = value.length();
    }

    public String getValue() {
        return value;
    }
}
