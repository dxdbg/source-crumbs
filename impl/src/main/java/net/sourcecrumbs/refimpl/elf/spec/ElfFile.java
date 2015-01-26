/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.annotation.Init;

import net.sourcecrumbs.api.machinecode.MachineCodeMapping;
import net.sourcecrumbs.api.machinecode.MachineCodeSource;
import net.sourcecrumbs.refimpl.dwarf.DwarfMachineCodeMapping;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugLine;
import net.sourcecrumbs.refimpl.elf.spec.preon.AbsoluteOffset;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;
import net.sourcecrumbs.refimpl.elf.spec.sections.SymbolTable;
import net.sourcecrumbs.refimpl.elf.spec.sym.ElfSymbol;

/**
 * Top level data structure representing an ELF file
 *
 * @author mcnulty
 */
public class ElfFile implements MachineCodeSource {

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

    @Override
    public MachineCodeMapping getMachineCodeMapping() {
        ElfSection debugInfoSection = getSection(DebugInfo.SECTION_NAME);
        ElfSection debugLineSection = getSection(DebugLine.SECTION_NAME);
        if (debugInfoSection != null && debugInfoSection.getSectionContent() instanceof DebugInfo &&
            debugLineSection != null && debugLineSection.getSectionContent() instanceof DebugLine) {
            return new DwarfMachineCodeMapping((DebugInfo)debugInfoSection.getSectionContent(),
                    (DebugLine)debugLineSection.getSectionContent());
        }
        return null;
    }
}
