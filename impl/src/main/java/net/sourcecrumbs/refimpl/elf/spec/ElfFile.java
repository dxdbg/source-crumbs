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

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import net.sourcecrumbs.refimpl.elf.spec.preon.AbsoluteOffset;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;
import net.sourcecrumbs.refimpl.elf.spec.sections.SymbolTable;
import net.sourcecrumbs.refimpl.elf.spec.sym.ElfSymbol;

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

        // Populate name fields
        if (header.getSectionNameStrIndex() < sections.length && header.getSectionNameStrIndex() >= 0) {
            ElfSection shStrTableSection = sections[header.getSectionNameStrIndex()];
            if (shStrTableSection.getSectionContent() instanceof StringTable) {
                StringTable shStrTable = (StringTable)shStrTableSection.getSectionContent();
                for (ElfSection section : sections) {

                    // Set the section name
                    String name = shStrTable.getString(section.getSectionHeader().getNameIndex());
                    sectionsByName.put(name, section);
                    section.setName(name);

                    if (section.getSectionContent() instanceof SymbolTable) {
                        SymbolTable symbolTable = (SymbolTable)section.getSectionContent();
                        ElfSection associatedStrTableSection = sections[section.getSectionHeader().getLink()];
                        if (associatedStrTableSection.getSectionContent() instanceof StringTable) {
                            StringTable associatedStrTable = (StringTable) associatedStrTableSection.getSectionContent();
                            for (ElfSymbol symbol : symbolTable.getSymbols()) {
                                if (symbol.getNameIndex() != 0) {
                                    symbol.setName(associatedStrTable.getString(symbol.getNameIndex()));
                                }else{
                                    symbol.setName("");
                                }
                            }
                        }
                    }
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

    public ElfSegment[] getSegments() {
        return segments;
    }

    public void setSegments(ElfSegment[] segments) {
        this.segments = segments;
    }

    public ElfSection[] getSections() {
        return sections;
    }

    public void setSections(ElfSection[] sections) {
        this.sections = sections;
    }

    public ElfSection getSection(String name) {
        return sectionsByName.get(name);
    }
}
