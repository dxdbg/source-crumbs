/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.util.EnumUtils;

import net.sourcecrumbs.refimpl.dwarf.constants.AttributeForm;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeName;
import net.sourcecrumbs.refimpl.dwarf.preon.LEBSigned;
import net.sourcecrumbs.refimpl.dwarf.preon.ListTerminator;
import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

/**
 * Describes an attribute contained in a DIE
 *
 * @author mcnulty
 */
public class AttributeSpecification implements ListTerminator {

    @Bound
    @LEBSigned(false)
    private LEB128 nameValue;

    @Bound
    @LEBSigned(false)
    private LEB128 formValue;

    @Override
    public boolean terminatesList() {
        return nameValue.getValue() == 0 && formValue.getValue() == 0;
    }

    public AttributeName getName() {
        return EnumUtils.getBoundEnumOptionIndex(AttributeName.class).get(nameValue.getValue());
    }

    public AttributeForm getForm() {
        return EnumUtils.getBoundEnumOptionIndex(AttributeForm.class).get(formValue.getValue());
    }

    @Override
    public String toString()
    {
        return getName() + "[" + nameValue.getValue() + "]" + getForm() + "[" + formValue.getValue() + "]";
    }
}
