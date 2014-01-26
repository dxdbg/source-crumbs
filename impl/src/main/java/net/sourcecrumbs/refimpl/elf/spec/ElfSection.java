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
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_NOTE", type = Note.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_NOBITS", type = NoBits.class),
                            @Choice(condition = "sectionHeader.type == SectionType.SHT_REL", type = RelocationTable.class)
                    }
            )
    )
    @AbsoluteOffset(value = "sectionHeader.offset.value * 8", adjustBitStream = false)
    private SectionContent sectionContent;

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
}
