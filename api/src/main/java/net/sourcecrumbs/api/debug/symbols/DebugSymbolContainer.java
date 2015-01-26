/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.debug.symbols;

/**
 * Represents a container for debugging-related symbols
 *
 * @author mcnulty
 */
public interface DebugSymbolContainer
{
    Iterable<Variable> getGlobalVariables();

    Variable getGlobalVariable(String name);

    Iterable<Function> getFunctions();

    Function getFunction(String name);

    Function getContainingFunction(long pc);
}
