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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.api.transunit.SourceLanguage;
import net.sourcecrumbs.api.transunit.TranslationUnit;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeName;
import net.sourcecrumbs.refimpl.dwarf.preon.SectionOffset;

/**
 * Represents a compilation unit in a DWARF file--includes the header and its associated DIEs
 *
 * @author mcnulty
 */
public class CompilationUnit implements SectionOffset, TranslationUnit {

    @Bound
    private CompilationUnitHeader header;

    // The length of individual DIEs is not available during parsing because it depends on the attribute encodings
    // represented in the .debug_abbrev section -- the DIEs are initialized once the .debug_abbrev data is available
    @BoundList(size = "header.unitLength.length - 2 - (header.unitLength.offsetLength/8) - 1", type=byte.class)
    private byte[] compilationUnitContent;

    private long sectionOffset;

    private DIE rootDIE;

    // the following members are lazily initialized

    private Path path = null;

    private SourceLanguage sourceLanguage = null;

    private Path compilationDir = null;

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

    public void buildDIEs(AbbreviationTable abbrevTable, ByteOrder byteOrder) throws UnknownFormatException {
        ByteBuffer buffer = ByteBuffer.wrap(compilationUnitContent);
        buffer.order(byteOrder);

        // the offset from the first byte of the CompilationUnitHeader to the first DIE
        long rootOffset = header.getUnitLength() - 2 - (header.getOffsetLength()/8) - 1;
        rootDIE = new DIE(abbrevTable, buffer, rootOffset, header.is32bitDWARF(), header.getAddressSize(), null);

        // Parse chain of siblings -- it is terminated by a DIE with an abbreviation code of 0
        LinkedList<DIE> parents = new LinkedList<>();
        parents.push(rootDIE);
        while(!parents.isEmpty()) {
            DIE currentParent = parents.pop();

            DIE currentChild;
            do {
                long childOffset = rootOffset + buffer.position();
                currentChild = new DIE(abbrevTable, buffer, childOffset, header.is32bitDWARF(), header.getAddressSize(), currentParent);
            }while(currentChild.getAbbreviationCode() != 0 && !currentChild.getChildrenPresent());

            if (currentChild.getAbbreviationCode() != 0 && currentChild.getChildrenPresent()) {
                // Start a new chain of siblings
                parents.push(currentParent);
                parents.push(currentChild);
            }
        }

        // All the data is now retrievable via the DIEs
        compilationUnitContent = null;
    }

    public DIE getRootDIE() {
        return rootDIE;
    }

    @Override
    public String getName() {
        Path localPath = getPath();
        if (localPath != null) {
            return localPath.toFile().getName();
        }

        return null;
    }

    @Override
    public SourceLanguage getLanguage() {
        if (sourceLanguage == null) {
            synchronized (this) {
                if (sourceLanguage == null) {

                }
            }
        }

        return sourceLanguage;
    }

    @Override
    public Path getCompilationDirectory() {
        if (compilationDir == null) {
            synchronized (this) {
                if (compilationDir == null) {
                    for (AttributeValue value : rootDIE.getAttributeValues()) {
                        if (value.getName() == AttributeName.DW_AT_comp_dir) {
                            compilationDir = Paths.get(value.getDataAsString());
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Path getPath() {
        if (path == null) {
            synchronized (this) {
                if (path == null) {
                    for (AttributeValue value : rootDIE.getAttributeValues()) {
                        if (value.getName() == AttributeName.DW_AT_name) {
                            path = Paths.get(value.getDataAsString());
                            break;
                        }
                    }
                }
            }
        }

        return path;
    }
}
