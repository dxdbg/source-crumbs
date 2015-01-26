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
 * Represents an Elf*_Addr field
 *
 * @author mcnulty
 */
public class Address implements ClassLengthField {

    private final long value;

    public Address(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }
}
