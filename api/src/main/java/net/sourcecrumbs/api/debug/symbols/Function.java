/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.debug.symbols;

import java.util.List;

import net.sourcecrumbs.api.Range;

/**
 * A Function represents a collection of executable code
 *
 * @author mcnulty
 */
public interface Function
{
    String getName();

    Range<Long> getScope();

    DebugType getReturnType();

    List<Variable> getFormalParameters();

    Iterable<Variable> getLocalVariables();

    List<Variable> getLocalVariablesByName(String name);
}
