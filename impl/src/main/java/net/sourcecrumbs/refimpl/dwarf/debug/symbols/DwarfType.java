/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.debug.symbols;

import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourcecrumbs.api.debug.symbols.DebugType;
import net.sourcecrumbs.refimpl.dwarf.constants.AbbreviationTag;
import net.sourcecrumbs.refimpl.dwarf.entries.AttributeValue;
import net.sourcecrumbs.refimpl.dwarf.entries.CompilationUnit;
import net.sourcecrumbs.refimpl.dwarf.entries.DIE;

/**
 * @author mcnulty
 */
public class DwarfType implements DebugType
{
    private static final Logger logger = LoggerFactory.getLogger(DwarfType.class);

    private String name;

    private boolean immutable;

    private boolean pointer;

    private DwarfType[] members;

    private DwarfType baseType;

    private DwarfType(CompilationUnit compilationUnit, DIE entry, ByteOrder byteOrder)
    {
        switch (entry.getTag()) {
            case DW_TAG_pointer_type:
                pointer = true;
                break;
            default:
                pointer = false;
                break;
        }

        for (AttributeValue value : entry.getAttributeValues()) {
            switch (value.getName()) {
                case DW_AT_name:
                    name = value.getDataAsString(compilationUnit.getStringTable());
                    break;
                case DW_AT_type:
                    DIE typeEntry = value.getReferencedDie(compilationUnit, byteOrder);
                    if (typeEntry == null) {
                        logger.debug("Failed to determine base type for type entry: " + entry);
                    }else{
                        baseType = new DwarfType(compilationUnit, typeEntry, byteOrder);
                    }
                    break;
            }
        }
    }

    public static DwarfType getType(CompilationUnit compilationUnit, DIE entry, ByteOrder byteOrder)
    {
        // TODO flyweight DwarfTypes
        return new DwarfType(compilationUnit, entry, byteOrder);
    }

    @Override
    public String getName()
    {
        if (name == null && pointer && baseType != null) {
            return baseType.getName() + "*";
        }
        return name;
    }

    @Override
    public boolean isImmutable()
    {
        return immutable;
    }

    @Override
    public boolean isPointer()
    {
        return pointer;
    }

    @Override
    public boolean isMemberStructure()
    {
        return members != null && members.length > 0;
    }

    @Override
    public DebugType[] getMemberTypes()
    {
        return members;
    }

    @Override
    public DebugType getBaseType()
    {
        return baseType;
    }
}
