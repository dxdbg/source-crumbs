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

package net.sourcecrumbs.refimpl.dwarf.entries;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;

import net.sourcecrumbs.refimpl.dwarf.preon.SectionOffset;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugAbbrev;

/**
 * Represents a compilation unit in a DWARF file--includes the header and its associated DIEs
 *
 * @author mcnulty
 */
public class CompilationUnit implements SectionOffset {

    @Bound
    private CompilationUnitHeader header;

    // The length of individual DIEs is not available during parsing because it depends on the attribute encodings
    // represented in the .debug_abbrev section -- the DIEs are initialized once the .debug_abbrev data is available
    @BoundList(size = "header.unitLength.length - 2 - (header.unitLength.offsetLength/8) - 1", type=byte.class)
    private byte[] compilationUnitContent;

    private long sectionOffset;

    @Override
    public long getSectionOffset() {
        return sectionOffset;
    }

    @Override
    public void setSectionOffset(long sectionOffset) {
        this.sectionOffset = sectionOffset;
    }

    public CompilationUnitHeader getHeader() {
        return header;
    }

    public void setHeader(CompilationUnitHeader header) {
        this.header = header;
    }
}
