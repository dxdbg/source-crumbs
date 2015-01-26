/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec;

import org.codehaus.preon.annotation.BoundObject;
import org.codehaus.preon.annotation.Choices;
import org.codehaus.preon.annotation.Choices.Choice;
import org.codehaus.preon.el.ImportStatic;

import net.sourcecrumbs.refimpl.elf.spec.constants.ElfClass;
import net.sourcecrumbs.refimpl.elf.spec.constants.SegmentType;
import net.sourcecrumbs.refimpl.elf.spec.preon.AbsoluteOffset;
import net.sourcecrumbs.refimpl.elf.spec.sections.DynamicLinkingInfo;
import net.sourcecrumbs.refimpl.elf.spec.sections.Note;
import net.sourcecrumbs.refimpl.elf.spec.segments.GenericSegment;
import net.sourcecrumbs.refimpl.elf.spec.segments.InterpreterSegment;
import net.sourcecrumbs.refimpl.elf.spec.segments.SegmentContent;

/**
 * A segment in an ELF file
 *
 * @author mcnulty
 */
@ImportStatic({ElfClass.class, SegmentType.class})
public class ElfSegment {

    @BoundObject(
            selectFrom = @Choices(
                    alternatives = {
                            @Choice(condition = "outer.header.ident.elfClass == ElfClass.ELFCLASS32", type = Elf32ProgramHeader.class),
                            @Choice(condition = "outer.header.ident.elfClass == ElfClass.ELFCLASS64", type = Elf64ProgramHeader.class)
                    }
            )
    )
    private ElfProgramHeader programHeader;

    @BoundObject(
            selectFrom = @Choices(
                    defaultType = GenericSegment.class,
                    alternatives = {
                            @Choice(condition="programHeader.type == SegmentType.PT_INTERP", type = InterpreterSegment.class)
                    }

            )
    )
    @AbsoluteOffset(value = "programHeader.offset.value * 8", adjustBitStream = false)
    private SegmentContent segmentContent;

    public ElfProgramHeader getProgramHeader() {
        return programHeader;
    }

    public void setProgramHeader(ElfProgramHeader programHeader) {
        this.programHeader = programHeader;
    }

    public SegmentContent getSegmentContent() {
        return segmentContent;
    }

    public void setSegmentContent(SegmentContent segmentContent) {
        this.segmentContent = segmentContent;
    }
}
