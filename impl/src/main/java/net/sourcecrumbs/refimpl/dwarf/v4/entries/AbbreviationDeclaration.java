/*
 * Copyright (c) 2011-2013, Dan McNulty
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the UDI project nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS AND CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package net.sourcecrumbs.refimpl.dwarf.v4.entries;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.util.EnumUtils;

import net.sourcecrumbs.refimpl.dwarf.v4.constants.AbbreviationTag;
import net.sourcecrumbs.refimpl.dwarf.v4.constants.ChildrenPresent;
import net.sourcecrumbs.refimpl.dwarf.v4.preon.ElementTerminatedList;
import net.sourcecrumbs.refimpl.dwarf.v4.preon.LEBSigned;
import net.sourcecrumbs.refimpl.dwarf.v4.preon.ListTreeNode;
import net.sourcecrumbs.refimpl.dwarf.v4.types.LEB128;

/**
 * An entry in an AbbreviationTable
 *
 * @author mcnulty
 */
public class AbbreviationDeclaration implements ListTreeNode {

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

    private AbbreviationDeclaration parent;

    private List<AbbreviationDeclaration> children = new LinkedList<>();

    @Override
    public AbbreviationDeclaration getParent() {
        return parent;
    }

    @Override
    public void setParent(ListTreeNode parent) {
        if (parent instanceof AbbreviationDeclaration) {
            this.parent = (AbbreviationDeclaration)parent;
            this.parent.children.add(this);
        }
    }

    @Override
    public boolean hasChildren() {
        return childrenPresent != null && childrenPresent == ChildrenPresent.DW_CHILDREN_yes;
    }

    @Override
    public boolean terminatesList() {
        return code.getValue() == 0;
    }

    public long getCode() {
        return code.getValue();
    }

    public AbbreviationTag getTag() {
        return EnumUtils.getBoundEnumOptionIndex(AbbreviationTag.class).get(tagValue.getValue());
    }

    public List<AbbreviationDeclaration> getChildren() {
        return children;
    }
}
