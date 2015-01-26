/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundString;

import net.sourcecrumbs.refimpl.dwarf.entries.lnp.FileEntry;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.LineNumberProgramHeader;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberRow;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberState;
import net.sourcecrumbs.refimpl.dwarf.preon.LEBSigned;
import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

/**
 * A line number operation that defines another possible file for the line number program
 *
 * @author mcnulty
 */
public class DefineFile implements LineNumberOperation {

    @BoundString
    private String sourceFilePath;

    @Bound
    @LEBSigned(false)
    private LEB128 directoryIndex;

    @Bound
    @LEBSigned(false)
    private LEB128 lastModificationTime;

    @Bound
    @LEBSigned(false)
    private LEB128 length;

    @Override
    public LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state) {
        // Append a new entry to the program header list of files
        FileEntry fileEntry = new FileEntry();
        fileEntry.setSourceFilePath(sourceFilePath);
        fileEntry.setDirectoryIndex(directoryIndex);
        fileEntry.setLastModificationTime(lastModificationTime);
        fileEntry.setLength(length);
        header.getFiles().add(fileEntry);

        return null;
    }
}
