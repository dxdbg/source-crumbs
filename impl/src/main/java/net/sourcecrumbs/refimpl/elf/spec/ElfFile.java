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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.annotation.Init;

import net.sourcecrumbs.api.debug.symbols.DebugSymbolContainer;
import net.sourcecrumbs.api.debug.symbols.Function;
import net.sourcecrumbs.api.debug.symbols.Variable;
import net.sourcecrumbs.api.machinecode.MachineCodeMapping;
import net.sourcecrumbs.api.machinecode.MachineCodeSource;
import net.sourcecrumbs.api.symbols.Symbol;
import net.sourcecrumbs.api.symbols.SymbolContainer;
import net.sourcecrumbs.api.transunit.TranslationUnit;
import net.sourcecrumbs.api.transunit.TranslationUnitContainer;
import net.sourcecrumbs.refimpl.dwarf.debug.symbols.DwarfDebugSymbolContainer;
import net.sourcecrumbs.refimpl.dwarf.DwarfMachineCodeMapping;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugLine;
import net.sourcecrumbs.refimpl.elf.spec.preon.AbsoluteOffset;
import net.sourcecrumbs.refimpl.elf.spec.sections.SectionContent;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;
import net.sourcecrumbs.refimpl.elf.spec.sections.SymbolTable;
import net.sourcecrumbs.refimpl.elf.spec.sym.ElfSymbol;

/**
 * Top level data structure representing an ELF file
 *
 * @author mcnulty
 */
public class ElfFile implements MachineCodeSource, DebugSymbolContainer, TranslationUnitContainer, SymbolContainer
{
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

    private DwarfMachineCodeMapping machineCodeMapping = null;

    private DwarfDebugSymbolContainer debugSymbolContainer = null;

    private ElfSymbolContainer elfSymbolContainer = null;

    @Init
    public void initialize()
    {
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

    private void initMachineCodeMapping()
    {
        if (machineCodeMapping == null) {
            synchronized (this) {
                if (machineCodeMapping == null) {
                    ElfSection debugInfoSection = getSection(DebugInfo.SECTION_NAME);
                    ElfSection debugLineSection = getSection(DebugLine.SECTION_NAME);
                    if (debugInfoSection != null && debugInfoSection.getSectionContent() instanceof DebugInfo &&
                            debugLineSection != null && debugLineSection.getSectionContent() instanceof DebugLine) {
                        machineCodeMapping = new DwarfMachineCodeMapping((DebugInfo) debugInfoSection.getSectionContent(),
                                (DebugLine) debugLineSection.getSectionContent());
                    }
                }
            }
        }
    }

    private void initDebugSymbolContainer()
    {
        if (debugSymbolContainer == null) {
            synchronized (this) {
                if (debugSymbolContainer == null) {
                    ElfSection debugInfoSection = getSection(DebugInfo.SECTION_NAME);
                    if (debugInfoSection != null && debugInfoSection.getSectionContent() instanceof DebugInfo) {
                        debugSymbolContainer = new DwarfDebugSymbolContainer((DebugInfo) debugInfoSection.getSectionContent());
                    }
                }
            }
        }
    }

    private void initSymbolContainer()
    {
        if (elfSymbolContainer == null) {
            synchronized (this) {
                if (elfSymbolContainer == null) {
                    List<SymbolTable> symbolTables = new LinkedList<>();
                    for (ElfSection section : sections) {
                        if (section.getSectionContent() instanceof SymbolTable) {
                            SymbolTable symbolTable = (SymbolTable) section.getSectionContent();
                            symbolTables.add(symbolTable);
                        }
                    }
                    elfSymbolContainer = new ElfSymbolContainer(symbolTables);
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
    public MachineCodeMapping getMachineCodeMapping()
    {
        initMachineCodeMapping();
        return machineCodeMapping;
    }

    @Override
    public Iterable<Variable> getGlobalVariables()
    {
        initDebugSymbolContainer();
        return debugSymbolContainer.getGlobalVariables();
    }

    @Override
    public Variable getGlobalVariable(String name)
    {
        initDebugSymbolContainer();
        return debugSymbolContainer.getGlobalVariable(name);
    }

    @Override
    public Iterable<Function> getFunctions()
    {
        initDebugSymbolContainer();
        return debugSymbolContainer.getFunctions();
    }

    @Override
    public Function getFunction(String name)
    {
        initDebugSymbolContainer();
        return debugSymbolContainer.getFunction(name);
    }

    @Override
    public Function getContainingFunction(long pc)
    {
        initDebugSymbolContainer();
        return debugSymbolContainer.getContainingFunction(pc);
    }

    @Override
    public Iterable<Symbol> getSymbols()
    {
        initSymbolContainer();
        return elfSymbolContainer.getSymbols();
    }

    @Override
    public List<Symbol> getSymbolsByName(String name)
    {
        initSymbolContainer();
        return elfSymbolContainer.getSymbolsByName(name);
    }

    @Override
    public Iterable<TranslationUnit> getTranslationUnits()
    {
        return null;
    }

    @Override
    public TranslationUnit getContainingTranslationUnit(long pc)
    {
        return null;
    }
}
