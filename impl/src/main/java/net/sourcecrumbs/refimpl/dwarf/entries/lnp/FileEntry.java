/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
