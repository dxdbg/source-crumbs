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
 * A field that is the word length of the architecture for the file
 *
 * @author mcnulty
 */
public class WordField implements ClassLengthField {

    private final long value;

    public WordField(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }
}
