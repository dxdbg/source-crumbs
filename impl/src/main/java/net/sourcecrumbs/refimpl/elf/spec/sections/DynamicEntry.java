/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sections;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.util.EnumUtils;

import net.sourcecrumbs.refimpl.elf.spec.Address;
import net.sourcecrumbs.refimpl.elf.spec.WordField;
import net.sourcecrumbs.refimpl.elf.spec.constants.DynamicTag;

/**
 * An entry in a .dynamic section or PT_DYNAMIC segment
 *
 * @author mcnulty
 */
public class DynamicEntry {

    @Bound
    private WordField tagValue;

    @Bound
    private Address value;

    public WordField getTagValue() {
        return tagValue;
    }

    public void setTagValue(WordField tagValue) {
        this.tagValue = tagValue;
    }

    public DynamicTag getTag() {
        return EnumUtils.getBoundEnumOptionIndex(DynamicTag.class).get(tagValue.getValue());
    }

    public Address getValue() {
        return value;
    }

    public void setValue(Address value) {
        this.value = value;
    }
}
