/*
 * Copyright (c) 2011-2016, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf;

import java.util.LinkedList;

import net.sourcecrumbs.api.transunit.TranslationUnit;
import net.sourcecrumbs.api.transunit.TranslationUnitContainer;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;

/**
 * TranslationUnitContainer implementation for DWARF
 *
 * @author mcnulty
 */
public class DwarfTranslationUnitContainer implements TranslationUnitContainer
{
    private final DebugInfo debugInfo;

    public DwarfTranslationUnitContainer(DebugInfo debugInfo)
    {
        this.debugInfo = debugInfo;
    }

    @Override
    public Iterable<TranslationUnit> getTranslationUnits()
    {
        return new LinkedList<>(debugInfo.getCompilationUnits());
    }

    @Override
    public TranslationUnit getContainingTranslationUnit(long pc)
    {
        return debugInfo.getCompilationUnit(pc);
    }
}
