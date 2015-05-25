/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.symbols;


import java.util.List;

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
     * @return the symbols with the specified name or empty if no symbols exist with that name
     */
    List<Symbol> getSymbolsByName(String name);
}
