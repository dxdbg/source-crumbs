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

package net.sourcecrumbs.refimpl.elf.spec;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.annotation.Init;

import net.sourcecrumbs.refimpl.elf.spec.preon.AbsoluteOffset;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;

/**
 * Top level data structure representing an ELF file
 *
 * @author mcnulty
 */
public class ElfFile {

    @Bound
    private ElfHeader header;

    @If("header.numProgramHeaders > 0 && header.programHeaderSize > 0")
    @BoundList(type = ElfSegment.class, size = "header.numProgramHeaders")
    @AbsoluteOffset(value = "header.programHeaderOffset.value * 8", adjustBitStream = true)
    private ElfSegment[] segments;

    @If("header.numSectionHeaders > 0 && header.sectionHeaderSize > 0")
    @BoundList(type = ElfSection.class, size = "header.numSectionHeaders")
    @AbsoluteOffset(value = "header.sectionHeaderOffset.value * 8", adjustBitStream = false)
    private ElfSection[] sections;

    private final Map<String, ElfSection> sectionsByName = new HashMap<>();

    @Init
    public void initialize() {

        // Initialize the mapping from section name to sections
        if (header.getSectionNameStrIndex() < sections.length && header.getSectionNameStrIndex() >= 0) {
            ElfSection shStrTable = sections[header.getSectionNameStrIndex()];
            if (shStrTable.getSectionContent() instanceof StringTable) {
                StringTable table = (StringTable)shStrTable.getSectionContent();
                for (ElfSection section : sections) {
                    String name = table.getString(section.getSectionHeader().getNameIndex());
                    sectionsByName.put(name, section);
                }
            }
        }
    }

    public ElfHeader getHeader() {
        return header;
    }

    public void setHeader(ElfHeader header) {
        this.header = header;
    }
}
