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

package net.sourcecrumbs.refimpl.dwarf;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDecorator;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.dwarf.preon.DwarfCodecFactory;
import net.sourcecrumbs.refimpl.dwarf.preon.ListTreeNodeCodecDecorator;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugAbbrev;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugLine;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugStr;
import net.sourcecrumbs.refimpl.elf.ElfSectionPostProcessor;
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
    private ElfCodecFactory elfCodecFactory = null;

    public DwarfSectionPostProcessor() {
        this.codecDecorators = new CodecDecorator[]{ new ListTreeNodeCodecDecorator() };
        this.dwarfCodecFactory = new DwarfCodecFactory(codecDecorators);
    }

    @Override
    public void process(ElfIdent elfIdent, ElfSection section) throws UnknownFormatException {
        if (elfCodecFactory == null) {
            synchronized (this) {
                if (elfCodecFactory == null) {
                    elfCodecFactory = new ElfCodecFactory(elfIdent);
                }
            }
        }

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
}
