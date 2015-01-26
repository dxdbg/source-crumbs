/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.debug.symbols;

import net.sourcecrumbs.api.Range;

/**
 * A Variable is data given a name and a type in source code
 *
 * @author mcnulty
 */
public interface Variable
{
    String getName();

    Location calculateLocation(RegisterContext registerContext) throws ContextInspectionException;

    DebugType getType();

    Range<Long> getContainingScope();
}
