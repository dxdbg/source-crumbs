/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.files;

import java.util.List;

import net.sourcecrumbs.api.machinecode.MachineCodeSource;
import net.sourcecrumbs.api.symbols.SymbolContainer;
import net.sourcecrumbs.api.transunit.TranslationUnitContainer;

/**
 * Represents a native executable
 *
 * @author mcnulty
 */
public abstract class Executable implements Binary
{
    public abstract List<Library> getLibraries();

    @Override
    public BinaryType getBinaryType() {
        return BinaryType.Executable;
    }
}
