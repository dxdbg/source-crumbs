/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundObject;
import org.codehaus.preon.annotation.Choices;
import org.codehaus.preon.annotation.Choices.Choice;
import org.codehaus.preon.annotation.LazyLoading;
import org.codehaus.preon.el.ImportStatic;

import net.sourcecrumbs.refimpl.elf.spec.constants.SectionType;
import net.sourcecrumbs.refimpl.elf.spec.preon.AbsoluteOffset;
import net.sourcecrumbs.refimpl.elf.spec.sections.AddendRelocationTable;
import net.sourcecrumbs.refimpl.elf.spec.sections.DynamicLinkingInfo;
import net.sourcecrumbs.refimpl.elf.spec.sections.GenericSection;
import net.sourcecrumbs.refimpl.elf.spec.sections.NoBits;
import net.sourcecrumbs.refimpl.elf.spec.sections.Note;
import net.sourcecrumbs.refimpl.elf.spec.sections.RelocationTable;
import net.sourcecrumbs.refimpl.elf.spec.sections.SectionContent;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;
import net.sourcecrumbs.refimpl.elf.spec.sections.SymbolHashTable;
import net.sourcecrumbs.refimpl.elf.spec.sections.SymbolTable;

/**
 * A section in an ELF file
 *
 * @author mcnulty
 */
@ImportStatic(SectionType.class)
public class ElfSection {

    /** Section header index for an undefined section reference */
    public static final int SHN_UNDEF = 0;

    public static final int SHN_LORESERVE = 0xff00;
    public static final int SHN_LOPROC = 0xff00;
    public static final int SHN_HIPROC = 0xff1f;
    public static final int SHN_ABS = 0xfff1;
    public static final int SHN_COMMON = 0xfff2;
    public static final int SHN_HIRESERVE = 0xffff;

    @Bound
    private ElfSectionHeader sectionHeader;

    @BoundObject(
            selectFrom = @Choices(
                    defaultType = GenericSection.class,
                    alternatives = {
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_SYMTAB", type = SymbolTable.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_DYNSYM", type = SymbolTable.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_STRTAB", type = StringTable.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_RELA", type = AddendRelocationTable.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_HASH", type = SymbolHashTable.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_DYNAMIC", type = DynamicLinkingInfo.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_NOBITS", type = NoBits.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_REL", type = RelocationTable.class)
                    }
            )
    )
    @AbsoluteOffset(value = "sectionHeader.offset.value * 8", adjustBitStream = false)
    private SectionContent sectionContent;

    private String name;

    public ElfSectionHeader getSectionHeader() {
        return sectionHeader;
    }

    public void setSectionHeader(ElfSectionHeader sectionHeader) {
        this.sectionHeader = sectionHeader;
    }

    public SectionContent getSectionContent() {
        return sectionContent;
    }

    public void setSectionContent(SectionContent sectionContent) {
        this.sectionContent = sectionContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
