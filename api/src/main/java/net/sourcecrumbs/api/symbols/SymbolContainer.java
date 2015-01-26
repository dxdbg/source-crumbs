/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.symbols;


/**
 * Represents a container for Symbols
 *
 * @author mcnulty
 */
public interface SymbolContainer
{
    /**
     * @return the symbols in this container
     */
    Iterable<Symbol> getSymbols();

    /**
     * @param name the name of the symbol
     *
     * @return the symbol or null if no symbol with the specified name exists
     */
    Symbol getSymbol(String name);
}
