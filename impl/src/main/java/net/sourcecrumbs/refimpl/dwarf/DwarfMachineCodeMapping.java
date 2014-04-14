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

package net.sourcecrumbs.refimpl.dwarf;

import java.util.List;

import net.sourcecrumbs.api.Range;
import net.sourcecrumbs.api.machinecode.MachineCodeMapping;
import net.sourcecrumbs.api.machinecode.SourceLine;
import net.sourcecrumbs.api.machinecode.SourceLineRange;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugLine;

/**
 * Implementation of MachineCodeMapping, backed by DWARF
 *
 * @author mcnulty
 */
public class DwarfMachineCodeMapping implements MachineCodeMapping {

    private final DebugInfo debugInfo;
    private final DebugLine debugLine;

    public DwarfMachineCodeMapping(DebugInfo debugInfo, DebugLine debugLine) {
        this.debugInfo = debugInfo;
        this.debugLine = debugLine;

        // TODO build all the line number matrices
    }

    @Override
    public List<Range<Long>> getMachineCodeRanges(SourceLine sourceLine) {
        return null;
    }

    @Override
    public List<SourceLineRange> getSourceLinesRanges(long machineCodeAddress) {
        return null;
    }

    @Override
    public long getNextStatementAddress(SourceLine sourceLine, boolean descend) {
        return 0;
    }

    @Override
    public long getNextStatementAddress(long address, boolean descend) {
        return 0;
    }

    @Override
    public long getStatementAddress(long address) {
        return 0;
    }

    @Override
    public long getStatementAddress(SourceLine sourceLine) {
        return 0;
    }
}
