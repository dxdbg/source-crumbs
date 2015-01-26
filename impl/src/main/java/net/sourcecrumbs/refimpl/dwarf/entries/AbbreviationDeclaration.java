/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import java.util.List;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.util.EnumUtils;

import net.sourcecrumbs.refimpl.dwarf.constants.AbbreviationTag;
import net.sourcecrumbs.refimpl.dwarf.constants.ChildrenPresent;
import net.sourcecrumbs.refimpl.dwarf.preon.ElementTerminatedList;
import net.sourcecrumbs.refimpl.dwarf.preon.LEBSigned;
import net.sourcecrumbs.refimpl.dwarf.preon.ListTerminator;
import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

/**
 * An entry in an AbbreviationTable
 *
 * @author mcnulty
 */
public class AbbreviationDeclaration implements ListTerminator {

    @Bound
    @LEBSigned(false)
    private LEB128 code;

    @If("code.value > 0 || code.value < 0")
    @Bound
    @LEBSigned(false)
    private LEB128 tagValue;

    @If("code.value > 0 || code.value < 0")
    @BoundNumber(size = "8")
    private ChildrenPresent childrenPresent;

    @If("code.value > 0 || code.value < 0")
    @Bound
    @ElementTerminatedList(elementType=AttributeSpecification.class)
    private List<AttributeSpecification> specifications;

    public boolean hasChildren() {
        return childrenPresent != null && childrenPresent == ChildrenPresent.DW_CHILDREN_yes;
    }

    @Override
    public boolean terminatesList() {
        return code.getValue() == 0;
    }

    public int getCode() {
        return (int)code.getValue();
    }

    public AbbreviationTag getTag() {
        return EnumUtils.getBoundEnumOptionIndex(AbbreviationTag.class).get(tagValue.getValue());
    }

    public List<AttributeSpecification> getSpecifications() {
        return specifications;
    }
}
