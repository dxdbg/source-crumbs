/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf;

import org.junit.Ignore;
import org.junit.Test;


import net.sourcecrumbs.refimpl.BaseNativeFileTest;
import net.sourcecrumbs.refimpl.util.DwarfDump;

/**
 * Utility test to dump DWARF
 *
 * @author mcnulty
 */
@Ignore
public class DwarfDumpSandboxTest extends BaseNativeFileTest
{
    @Test
    public void dumpUrl() throws Exception
    {
        DwarfDump.dwarfDump(getExecutablePath("basic.310b3f661611270328fde43a330a321c7d02173c"), true);
    }
}
