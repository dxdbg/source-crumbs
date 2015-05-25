/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourcecrumbs.api.symbols.Symbol;
import net.sourcecrumbs.api.symbols.SymbolContainer;
import net.sourcecrumbs.refimpl.elf.spec.sections.SymbolTable;
import net.sourcecrumbs.refimpl.elf.spec.sym.ElfSymbol;

/**
 * @author mcnulty
 */
public class ElfSymbolContainer implements SymbolContainer
{
    private static final Logger logger = LoggerFactory.getLogger(ElfSymbolContainer.class);

    private final Map<String, List<ElfSymbol>> symbolsByName = new HashMap<>();

    public ElfSymbolContainer(List<SymbolTable> symbolTables)
    {
        for (SymbolTable symbolTable : symbolTables) {
            for (ElfSymbol symbol : symbolTable.getSymbols()) {
                if (symbol.getName() != null) {
                    List<ElfSymbol> symbols = symbolsByName.get(symbol.getName());
                    if (symbols == null) {
                        symbols = new LinkedList<>();
                        symbolsByName.put(symbol.getName(), symbols);
                    }
                    symbols.add(symbol);
                }else{
                    logger.debug("Found symbol without name: {}", symbol);
                }
            }
        }
    }

    @Override
    public Iterable<Symbol> getSymbols()
    {
        List<Symbol> result = new LinkedList<>();
        for (List<ElfSymbol> syms : symbolsByName.values()) {
            result.addAll(syms);
        }
        return result;
    }

    @Override
    public List<Symbol> getSymbolsByName(String name)
    {
        List<ElfSymbol> symbols = symbolsByName.get(name);
        if (symbols != null) {
            return new LinkedList<>(symbols);
        }
        return Collections.<Symbol>emptyList();
    }
}
