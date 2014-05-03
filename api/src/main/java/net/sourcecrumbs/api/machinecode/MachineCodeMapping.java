/*
 * Copyright (c) 2011-2013, Dan McNulty
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the UDI project nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS AND CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
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
