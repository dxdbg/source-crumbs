/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec;

/**
 * Represents an Elf*_Off field.
 *
 * @author mcnulty
 */
public class Offset implements ClassLengthField {

    private final long value;

    public Offset(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
