/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.rel;

import org.codehaus.preon.annotation.Bound;

import net.sourcecrumbs.refimpl.elf.spec.Offset;
import net.sourcecrumbs.refimpl.elf.spec.WordField;

/**
 * The element in an AddendRelocation
 *
 * @author mcnulty
 */
public class AddendRelocation {

    @Bound
    private Offset offset;

    @Bound
    private WordField info;

    @Bound
    private WordField addend;

    public Offset getOffset() {
        return offset;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

    public WordField getInfo() {
        return info;
    }

    public void setInfo(WordField info) {
        this.info = info;
    }

    public WordField getAddend() {
        return addend;
    }

    public void setAddend(WordField addend) {
        this.addend = addend;
    }
}
