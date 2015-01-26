/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.files;

import net.sourcecrumbs.api.debug.symbols.DebugSymbolContainer;
import net.sourcecrumbs.api.machinecode.MachineCodeSource;
import net.sourcecrumbs.api.symbols.SymbolContainer;
import net.sourcecrumbs.api.transunit.TranslationUnitContainer;

/**
 * Marker interface for all native binaries. Allows all types of binaries to be treated generically in certain situations.
 *
 * @author dmcnulty
 */
public interface Binary extends MachineCodeSource, SymbolContainer, TranslationUnitContainer, DebugSymbolContainer
{
    BinaryType getBinaryType();
}
