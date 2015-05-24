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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourcecrumbs.api.debug.symbols.DebugSymbolContainer;
import net.sourcecrumbs.api.debug.symbols.Function;
import net.sourcecrumbs.api.debug.symbols.Variable;
import net.sourcecrumbs.refimpl.dwarf.constants.AbbreviationTag;
import net.sourcecrumbs.refimpl.dwarf.entries.CompilationUnit;
import net.sourcecrumbs.refimpl.dwarf.entries.DIE;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;

/**
 * @author mcnulty
 */
public class DwarfDebugSymbolContainer implements DebugSymbolContainer
{
    private static final Logger logger = LoggerFactory.getLogger(DwarfDebugSymbolContainer.class);

    private final Map<String, Function> functionsByName = new HashMap<>();

    private final Map<String, Variable> variablesByName = new HashMap<>();

    public DwarfDebugSymbolContainer(DebugInfo debugInfo)
    {
        for (CompilationUnit compilationUnit : debugInfo.getCompilationUnits()) {
            extractFunctions(compilationUnit, compilationUnit.getRootDIE(), debugInfo.getByteOrder());
        }
    }

    private void extractFunctions(CompilationUnit compilationUnit, DIE currentDie, ByteOrder byteOrder)
    {
        // TODO should probably handle inlined_subroutine and entry_point
        if (currentDie.getTag() == AbbreviationTag.DW_TAG_subprogram) {
            DwarfFunction function = new DwarfFunction(compilationUnit, currentDie, byteOrder);

            if (StringUtils.isEmpty(function.getName())) {
                logger.debug("Failed to determine name for function entry: {}", currentDie);
            }else{
                functionsByName.put(function.getName(), function);
            }
        }

        for (DIE child : currentDie.getChildren()) {
            extractFunctions(compilationUnit, child, byteOrder);
        }
    }

    private void extractGlobalVariables(CompilationUnit compilationUnit, DIE rootDie, ByteOrder byteOrder)
    {

    }

    @Override
    public Iterable<Variable> getGlobalVariables()
    {
        return new LinkedList<>(variablesByName.values());
    }

    @Override
    public Variable getGlobalVariable(String name)
    {
        return variablesByName.get(name);
    }

    @Override
    public Iterable<Function> getFunctions()
    {
        return new LinkedList<>(functionsByName.values());
    }

    @Override
    public Function getFunction(String name)
    {
        return functionsByName.get(name);
    }

    @Override
    public Function getContainingFunction(long pc)
    {
        return null;
    }
}
