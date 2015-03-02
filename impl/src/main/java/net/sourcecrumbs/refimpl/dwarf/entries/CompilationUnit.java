/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.util.EnumUtils;

import net.sourcecrumbs.api.Range;
import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.api.transunit.SourceLanguage;
import net.sourcecrumbs.api.transunit.TranslationUnit;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeName;
import net.sourcecrumbs.refimpl.dwarf.constants.SourceLanguages;
import net.sourcecrumbs.refimpl.dwarf.preon.SectionOffset;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;

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

    private StringTable stringTable;

    // the following members are lazily initialized

    private Path path = null;

    private SourceLanguage sourceLanguage = null;

    private Path compilationDir = null;

    private List<Range<Long>> scopes = null;

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

    public void buildDIEs(AbbreviationTable abbrevTable, StringTable stringTable, ByteOrder byteOrder) throws UnknownFormatException {
        ByteBuffer buffer = ByteBuffer.wrap(compilationUnitContent);
        buffer.order(byteOrder);

        // the offset from the first byte of the CompilationUnitHeader to the first DIE
        long rootOffset = header.getUnitLength() - 2 - (header.getOffsetLength()/8) - 1;
        rootDIE = DIE.buildDIETree(abbrevTable, buffer, rootOffset, header.is32bitDWARF(), header.getAddressSize());

        // All the data is now retrievable via the DIEs
        this.stringTable = stringTable;
        compilationUnitContent = null;
    }

    public DIE getRootDIE() {
        return rootDIE;
    }

    public StringTable getStringTable()
    {
        return stringTable;
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
                    for (AttributeValue value : rootDIE.getAttributeValues()) {
                        if (value.getName() == AttributeName.DW_AT_language) {
                            sourceLanguage = EnumUtils.getBoundEnumOptionIndex(SourceLanguages.class).get(value.getDataAsLong()).getSourceLanguage();
                        }
                    }
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
                            compilationDir = Paths.get(value.getDataAsString(stringTable));
                            break;
                        }
                    }
                }
            }
        }
        return compilationDir;
    }

    @Override
    public Path getPath() {
        if (path == null) {
            synchronized (this) {
                if (path == null) {
                    for (AttributeValue value : rootDIE.getAttributeValues()) {
                        if (value.getName() == AttributeName.DW_AT_name) {
                            path = Paths.get(value.getDataAsString(stringTable));
                            break;
                        }
                    }
                }
            }
        }

        return path;
    }

    @Override
    public List<Range<Long>> getScopes()
    {
        if (scopes == null) {
            synchronized (this) {
                if (scopes == null) {
                    scopes = new LinkedList<>();

                    // TODO handle DW_AT_ranges
                    long start = 0, end = 0;
                    for (AttributeValue value : rootDIE.getAttributeValues()) {
                        if (value.getName() == AttributeName.DW_AT_low_pc) {
                            start = value.getDataAsLong();
                        }else if(value.getName() == AttributeName.DW_AT_high_pc) {
                            end = value.getDataAsLong();
                        }
                    }
                    if (start != 0 && end != 0) {
                        scopes.add(new Range<>(start, end));
                    }
                }
            }
        }
        return scopes;
    }
}
