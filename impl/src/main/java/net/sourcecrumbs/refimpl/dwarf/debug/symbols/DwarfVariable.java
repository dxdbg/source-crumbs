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

import net.sourcecrumbs.api.Range;
import net.sourcecrumbs.api.debug.symbols.ContextInspectionException;
import net.sourcecrumbs.api.debug.symbols.DebugType;
import net.sourcecrumbs.api.debug.symbols.Location;
import net.sourcecrumbs.api.debug.symbols.RegisterContext;
import net.sourcecrumbs.api.debug.symbols.Variable;
import net.sourcecrumbs.refimpl.dwarf.entries.AttributeValue;
import net.sourcecrumbs.refimpl.dwarf.entries.CompilationUnit;
import net.sourcecrumbs.refimpl.dwarf.entries.DIE;
import net.sourcecrumbs.refimpl.dwarf.entries.expr.DwarfExpression;

/**
 * @author mcnulty
 */
public class DwarfVariable implements Variable
{
    private static final Logger logger = LoggerFactory.getLogger(DwarfVariable.class);

    private DebugType type;

    private String name;

    private DwarfExpression expr;

    public DwarfVariable(CompilationUnit compilationUnit, DIE entry, ByteOrder byteOrder)
    {
        for (AttributeValue value : entry.getAttributeValues()) {
            switch (value.getName()) {
                case DW_AT_name:
                    name = value.getDataAsString(compilationUnit.getStringTable());
                    break;
                case DW_AT_type:
                    DIE typeEntry = value.getReferencedDie(compilationUnit, byteOrder);
                    if (typeEntry == null) {
                        logger.debug("Failed to determine type for variable entry: {}", entry);
                    }else{
                        type = DwarfType.getType(compilationUnit, entry, byteOrder);
                    }
                    break;
                case DW_AT_location:
                    DwarfExpression locationExpr = value.getExpression(byteOrder);
                    if (locationExpr == null) {
                        logger.debug("Failed to determine location expression for variable entry: {}", entry);
                    }else{
                        this.expr = locationExpr;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Location calculateLocation(RegisterContext registerContext) throws ContextInspectionException
    {
        if (expr != null) {
            return expr.calculateLocation(registerContext);
        }
        return null;
    }

    @Override
    public DebugType getType()
    {
        return type;
    }

    @Override
    public Range<Long> getScope()
    {
        return new Range<>(0L, 0L);
    }
}
