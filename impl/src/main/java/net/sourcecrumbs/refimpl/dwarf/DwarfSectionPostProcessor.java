/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDecorator;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.dwarf.preon.DwarfCodecFactory;
import net.sourcecrumbs.refimpl.dwarf.preon.ListTreeNodeCodecDecorator;
import net.sourcecrumbs.refimpl.dwarf.preon.SectionOffsetCodecDecorator;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugAbbrev;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugLine;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugStr;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugTypes;
import net.sourcecrumbs.refimpl.elf.ElfSectionPostProcessor;
import net.sourcecrumbs.refimpl.elf.spec.ElfFile;
import net.sourcecrumbs.refimpl.elf.spec.ElfIdent;
import net.sourcecrumbs.refimpl.elf.spec.ElfSection;
import net.sourcecrumbs.refimpl.elf.spec.preon.ElfCodecFactory;
import net.sourcecrumbs.refimpl.elf.spec.sections.GenericSection;
import net.sourcecrumbs.refimpl.elf.spec.sections.SectionContent;

/**
 * Post-processor for ELF sections that injects DWARF objects
 *
 * @author mcnulty
 */
public class DwarfSectionPostProcessor implements ElfSectionPostProcessor {

    private final DwarfCodecFactory dwarfCodecFactory;
    private final CodecDecorator[] codecDecorators;
    private final ElfCodecFactory elfCodecFactory;

    public DwarfSectionPostProcessor(ElfIdent elfIdent) throws UnknownFormatException {
        this.codecDecorators = new CodecDecorator[]{ new ListTreeNodeCodecDecorator(), new SectionOffsetCodecDecorator() };
        this.dwarfCodecFactory = new DwarfCodecFactory(codecDecorators);
        this.elfCodecFactory = new ElfCodecFactory(elfIdent);
    }

    @Override
    public void process(ElfSection section) throws UnknownFormatException {
        SectionContent sectionContent = section.getSectionContent();
        if (sectionContent instanceof GenericSection) {

            Class<? extends SectionContent> sectionClass;
            switch (section.getName()) {
                case DebugInfo.SECTION_NAME:
                    sectionClass = DebugInfo.class;
                    break;
                case DebugAbbrev.SECTION_NAME:
                    sectionClass = DebugAbbrev.class;
                    break;
                case DebugLine.SECTION_NAME:
                    sectionClass = DebugLine.class;
                    break;
                case DebugStr.SECTION_NAME: {
                    // Just create the StringTable and assign it
                    DebugStr newContent = new DebugStr();
                    newContent.setData(((GenericSection) sectionContent).getData());
                    section.setSectionContent(newContent);
                    sectionClass = null;
                    break;
                }
                default:
                    sectionClass = null;
                    break;
            }
            if (sectionClass != null) {
                // Replace the content in the section with the DWARF data objects
                byte[] data = ((GenericSection) sectionContent).getData();

                try {
                    Codec<? extends SectionContent> codec = Codecs.create(sectionClass,
                            new CodecFactory[] { dwarfCodecFactory, elfCodecFactory }, codecDecorators);
                    SectionContent newContent = Codecs.decode(codec, data);
                    section.setSectionContent(newContent);
                }catch (DecodingException e) {
                    throw new UnknownFormatException(e);
                }
            }
        }
    }

    @Override
    public void completeProcessing(ElfFile elfFile) throws UnknownFormatException {
        // initialize the DIEs, now that all information is known
        ElfSection debugInfoSection = elfFile.getSection(DebugInfo.SECTION_NAME);
        ElfSection debugAbbrevSection = elfFile.getSection(DebugAbbrev.SECTION_NAME);
        ElfSection debugStrSection = elfFile.getSection(DebugStr.SECTION_NAME);
        if (debugInfoSection != null && debugInfoSection.getSectionContent() instanceof DebugInfo &&
            debugAbbrevSection != null && debugAbbrevSection.getSectionContent() instanceof DebugAbbrev &&
            debugStrSection != null && debugStrSection.getSectionContent() instanceof DebugStr)
        {
            ((DebugInfo)debugInfoSection.getSectionContent()).buildDIEs(((DebugAbbrev) debugAbbrevSection.getSectionContent()),
                    ((DebugStr)debugStrSection.getSectionContent()),
                    elfFile.getHeader().getIdent().getByteOrder());
        }

        ElfSection debugTypesSection = elfFile.getSection(DebugTypes.SECTION_NAME);
        if (debugAbbrevSection != null && debugAbbrevSection.getSectionContent() instanceof DebugAbbrev &&
            debugTypesSection != null && debugTypesSection.getSectionContent() instanceof DebugTypes)
        {
            ((DebugTypes)debugTypesSection.getSectionContent()).buildDIEs(((DebugAbbrev) debugAbbrevSection.getSectionContent()),
                                                                          elfFile.getHeader().getIdent().getByteOrder());
        }
    }
}
