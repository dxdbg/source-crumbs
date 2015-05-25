/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.dwarf.preon.SectionOffset;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;

/**
 * Represents a type unit in a DWARF file--includes the header and its associated DIEs
 *
 * @author dmcnulty
 */
public class TypeUnit implements SectionOffset
{
    @Bound
    private TypeUnitHeader header;

    @BoundList(size = "header.unitLength.length - 2 - (header.unitLength.offsetLength/8) - 1 - 8 - (header.unitLength.offsetLength/8)",
        type=byte.class)
    private byte[] typeUnitContent;

    private long sectionOffset;

    private DIE rootDIE;

    @Override
    public long getSectionOffset()
    {
        return sectionOffset;
    }

    @Override
    public void setSectionOffset(long sectionOffset)
    {
       this.sectionOffset = sectionOffset;
    }

    public TypeUnitHeader getHeader()
    {
        return header;
    }

    public void setHeader(TypeUnitHeader header)
    {
        this.header = header;
    }

    public void buildDIEs(AbbreviationTable abbrevTable, ByteOrder byteOrder)
        throws UnknownFormatException
    {
        ByteBuffer buffer = ByteBuffer.wrap(typeUnitContent);
        buffer.order(byteOrder);

        // the offset from the first byte of the TypeUnitHeader to the first DIE
        long rootOffset = header.getUnitLength() - 2 - (header.getOffsetLength()/8) - 1;
        rootDIE = DIE.buildDIETree(abbrevTable, buffer, rootOffset, header.is32bitDWARF(), header.getAddressSize());

        // All the data is now retrievable via the DIEs
        typeUnitContent = null;
    }
}
