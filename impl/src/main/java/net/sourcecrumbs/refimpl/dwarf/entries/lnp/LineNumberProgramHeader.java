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

package net.sourcecrumbs.refimpl.dwarf.entries.lnp;

import java.util.List;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.If;

import net.sourcecrumbs.refimpl.dwarf.preon.ElementTerminatedList;
import net.sourcecrumbs.refimpl.dwarf.types.ListString;
import net.sourcecrumbs.refimpl.dwarf.types.InitialLength;

/**
 * Header for a line number program
 *
 * @author mcnulty
 */
public class LineNumberProgramHeader {

    @Bound
    private InitialLength unitLength;

    @BoundNumber(size = "16")
    private short version;

    @BoundNumber(size = "unitLength.offsetLength")
    private long headerLength;

    @BoundNumber(size = "8")
    private byte minimumInstructionLength;

    @If("version > 2")
    @BoundNumber(size = "8")
    private byte maximumOperationsPerInstruction;

    @BoundNumber(size = "8")
    private byte defaultIsStmt;

    @BoundNumber(size = "8")
    private byte lineBase;

    @BoundNumber(size = "8")
    private byte lineRange;

    @BoundNumber(size = "8")
    private short opcodeBase;

    @BoundList(size = "opcodeBase - 1", type=byte.class)
    private byte[] standardOpcodeLengths;

    @ElementTerminatedList(elementType = ListString.class)
    @Bound
    private List<ListString> includeDirectories;

    @ElementTerminatedList(elementType = FileEntry.class)
    @Bound
    private List<FileEntry> files;
}
