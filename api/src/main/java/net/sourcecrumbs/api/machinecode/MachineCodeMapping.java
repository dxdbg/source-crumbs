/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.machinecode;

import java.util.List;

import net.sourcecrumbs.api.Range;
import net.sourcecrumbs.api.transunit.TranslationUnit;

/**
 * A container for mapping source lines to machine code and vice versa
 *
 * @author mcnulty
 */
public interface MachineCodeMapping {

    /**
     * Obtains the machine code address ranges that are associated with the specified source line
     *
     * @param sourceLine the source line
     *
     * @return the machine code address ranges
     */
    List<Range<Long>> getMachineCodeRanges(SourceLine sourceLine);

    /**
     * Obtains the source lines ranges that are associated with the specified machine code address
     *
     * @param machineCodeAddress the machine code address
     *
     * @return the source lines
     */
    List<SourceLineRange> getSourceLinesRanges(long machineCodeAddress);

    /**
     * Gets the machine code address of the next statement following the given source line
     *
     * @param sourceLine the source line
     *
     * @return the machine code address or 0 if no address could be determined
     */
    long getNextStatementAddress(SourceLine sourceLine);

    /**
     * Gets the machine code address of the next statement following the given machine code address
     *
     * @param address the machine code address
     *
     * @return the machine code address or 0 if no address could be determined
     */
    long getNextStatementAddress(long address);

    /**
     * Gets the statement address corresponding to the specified address
     *
     * @param address the current address
     *
     * @return the address that corresponds to the statement
     */
    long getStatementAddress(long address);

    /**
     * Gets the statement address corresponding to the specified line
     *
     * @param sourceLine the source line
     *
     * @return the address that corresponds to the statement
     */
    long getStatementAddress(SourceLine sourceLine);
}
