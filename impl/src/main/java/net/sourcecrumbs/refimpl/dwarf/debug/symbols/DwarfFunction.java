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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.emory.mathcs.backport.java.util.Collections;
import net.sourcecrumbs.api.Range;
import net.sourcecrumbs.api.debug.symbols.DebugType;
import net.sourcecrumbs.api.debug.symbols.Function;
import net.sourcecrumbs.api.debug.symbols.Variable;
import net.sourcecrumbs.refimpl.dwarf.entries.AttributeValue;
import net.sourcecrumbs.refimpl.dwarf.entries.CompilationUnit;
import net.sourcecrumbs.refimpl.dwarf.entries.DIE;

/**
 * @author mcnulty
 */
public class DwarfFunction implements Function
{
    private static final Logger logger = LoggerFactory.getLogger(DwarfFunction.class);

    private String name;

    private DebugType returnType;

    private Long entryAddress;

    private List<Range<Long>> scopes = new LinkedList<>();

    private List<DwarfVariable> formalParams = new LinkedList<>();

    private Map<String, List<DwarfVariable>> localVarsByName = new HashMap<>();

    public DwarfFunction(CompilationUnit compilationUnit, DIE entry, ByteOrder byteOrder)
    {
        Long start = null, end = null;
        for (AttributeValue value : entry.getAttributeValues()) {
            switch (value.getName()) {
                case DW_AT_name:
                    name = value.getDataAsString(compilationUnit.getStringTable());
                    break;
                case DW_AT_type:
                    DIE typeEntry = value.getReferencedDie(compilationUnit, byteOrder);
                    if (typeEntry == null) {
                        logger.debug("Failed to determine type for function entry: {}", entry);
                    }else{
                        returnType = DwarfType.getType(compilationUnit, typeEntry, byteOrder);
                    }
                    break;
                case DW_AT_entry_pc:
                    entryAddress = value.getDataAsLong(byteOrder);
                    break;
                case DW_AT_low_pc:
                    if (start != null) {
                        logger.debug("Multiple low_pc entries for function entry: {}", entry);
                    }
                    start = value.getDataAsLong(byteOrder);
                    break;
                case DW_AT_high_pc:
                    if (end != null) {
                        logger.debug("Multiple high_pc entries for function entry: {}", entry);
                    }
                    end = value.getDataAsLong(byteOrder);
                    break;
                // TODO handle DW_AT_ranges
                default:
                    break;
            }
        }

        if (start == null || end == null) {
            logger.debug("Failed to calculate scopes for function entry: {}", entry);
        }else{
            scopes.add(new Range<>(start, end));
        }

        if (returnType == null) {
            returnType = DwarfVoidType.INSTANCE;
        }

        if (entryAddress == null && start != null) {
            entryAddress = start;
        }

        for (DIE child : entry.getChildren()) {
            // TODO handle nested functions, named constants
            switch (child.getTag()) {
                case DW_TAG_formal_parameter:
                    formalParams.add(new DwarfVariable(compilationUnit, child, byteOrder));
                    break;
                case DW_TAG_variable:
                    DwarfVariable dwarfVariable = new DwarfVariable(compilationUnit, child, byteOrder);
                    if (dwarfVariable.getName() != null) {
                        List<DwarfVariable> vars = localVarsByName.get(dwarfVariable.getName());
                        if (vars == null) {
                            vars = new LinkedList<>();
                            localVarsByName.put(dwarfVariable.getName(), vars);
                        }
                        vars.add(dwarfVariable);
                    }else{
                        logger.debug("Invalid variable definition in entry: {}", child);
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
    public List<Range<Long>> getScopes()
    {
        return new LinkedList<>(scopes);
    }

    @Override
    public Long getEntryAddress()
    {
        return entryAddress;
    }

    @Override
    public DebugType getReturnType()
    {
        return returnType;
    }

    @Override
    public List<Variable> getFormalParameters()
    {
        return new ArrayList<>(formalParams);
    }

    @Override
    public Iterable<Variable> getLocalVariables()
    {
        List<Variable> result = new LinkedList<>();
        for (List<DwarfVariable> vars : localVarsByName.values()) {
            result.addAll(vars);
        }
        return result;
    }

    @Override
    public List<Variable> getLocalVariablesByName(String name)
    {
        List<DwarfVariable> localVars = localVarsByName.get(name);
        if (localVars != null) {
            return new LinkedList<>(localVars);
        }
        return Collections.<Variable>emptyList();
    }
}
