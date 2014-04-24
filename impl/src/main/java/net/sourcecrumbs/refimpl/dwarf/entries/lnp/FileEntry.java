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

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundString;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.annotation.Init;

import net.sourcecrumbs.refimpl.dwarf.preon.LEBSigned;
import net.sourcecrumbs.refimpl.dwarf.preon.ListTerminator;
import net.sourcecrumbs.refimpl.dwarf.preon.StringWrapper;
import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

/**
 * A file entry used by a line number program
 *
 * @author mcnulty
 */
public class FileEntry implements ListTerminator {

    @Bound
    private StringWrapper sourceFilePathWrapper;

    private String sourceFilePath;

    @Bound
    @LEBSigned(false)
    @If("sourceFilePathWrapper.length > 0")
    private LEB128 directoryIndex;

    @Bound
    @LEBSigned(false)
    @If("sourceFilePathWrapper.length > 0")
    private LEB128 lastModificationTime;

    @Bound
    @LEBSigned(false)
    @If("sourceFilePathWrapper.length > 0")
    private LEB128 length;

    @Init
    public void init() {
        sourceFilePath = sourceFilePathWrapper.getValue();
    }

    @Override
    public boolean terminatesList() {
        return sourceFilePathWrapper == null || sourceFilePathWrapper.getValue().isEmpty();
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public LEB128 getDirectoryIndex() {
        return directoryIndex;
    }

    public void setDirectoryIndex(LEB128 directoryIndex) {
        this.directoryIndex = directoryIndex;
    }

    public LEB128 getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(LEB128 lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public LEB128 getLength() {
        return length;
    }

    public void setLength(LEB128 length) {
        this.length = length;
    }
}
