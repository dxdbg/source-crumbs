/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDecorator;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.DefaultCodecFactory;
import org.codehaus.preon.buffer.ByteOrder;

import net.sourcecrumbs.api.files.Binary;
import net.sourcecrumbs.api.files.BinaryReader;
import net.sourcecrumbs.api.files.CoreFile;
import net.sourcecrumbs.api.files.Executable;
import net.sourcecrumbs.api.files.Library;
import net.sourcecrumbs.api.files.ObjectFile;
import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.dwarf.DwarfSectionPostProcessor;
import net.sourcecrumbs.refimpl.elf.spec.ElfFile;
import net.sourcecrumbs.refimpl.elf.spec.ElfIdent;
import net.sourcecrumbs.refimpl.elf.spec.ElfSection;
import net.sourcecrumbs.refimpl.elf.spec.constants.FileType;
import net.sourcecrumbs.refimpl.elf.spec.preon.AbsoluteOffsetCodecDecorator;
import net.sourcecrumbs.refimpl.elf.spec.preon.ElfCodecFactory;

/**
 * Reader for ELF files
 *
 * @author mcnulty
 */
public class ElfReader implements BinaryReader {

    private static final AbsoluteOffsetCodecDecorator codecDecorator = new AbsoluteOffsetCodecDecorator();

    @Override
    public Binary open(Path path) throws IOException, UnknownFormatException {
        ElfFile elfFile = loadElfFile(path);

        switch (elfFile.getHeader().getFileType()) {
            case ET_EXEC:
                return new ElfExecutable(elfFile);
            case ET_REL:
                return new ElfObjectFile(elfFile);
            case ET_CORE:
                return new ElfCoreFile(elfFile);
            case ET_DYN:
                return new ElfLibrary(elfFile);
            default:
                throw new UnknownFormatException("Unknown ELF file type " + elfFile.getHeader().getFileType());
        }
    }

    @Override
    public Executable openExecutable(Path path) throws IOException, UnknownFormatException {
        ElfFile elfFile = loadElfFile(path);

        if (elfFile.getHeader().getFileType() != FileType.ET_EXEC) {
            throw new UnknownFormatException(path + " is not an executable");
        }

        return new ElfExecutable(elfFile);
    }

    @Override
    public CoreFile openCoreFile(Path path) throws IOException, UnknownFormatException {
        ElfFile elfFile = loadElfFile(path);

        if (elfFile.getHeader().getFileType() != FileType.ET_CORE) {
            throw new UnknownFormatException(path + " is not an executable");
        }

        return new ElfCoreFile(elfFile);
    }

    @Override
    public Library openLibrary(Path path) throws IOException, UnknownFormatException {
        ElfFile elfFile = loadElfFile(path);

        if (elfFile.getHeader().getFileType() != FileType.ET_DYN) {
            throw new UnknownFormatException(path + " is not an executable");
        }

        return new ElfLibrary(elfFile);
    }

    @Override
    public ObjectFile openObjectFile(Path path) throws IOException, UnknownFormatException {
        ElfFile elfFile = loadElfFile(path);

        if (elfFile.getHeader().getFileType() != FileType.ET_REL) {
            throw new UnknownFormatException(path + " is not an executable");
        }

        return new ElfObjectFile(elfFile);
    }

    private ElfFile loadElfFile(Path path) throws IOException, UnknownFormatException {
        try {
            // Read the identification to determine the parameters for decoding the rest of the file
            Codec<ElfIdent> identCodec = Codecs.create(ElfIdent.class);
            ElfIdent ident = Codecs.decode(identCodec, path.toFile());

            ElfCodecFactory elfCodecFactory = new ElfCodecFactory(ident);

            Codec<ElfFile> fileCodec = Codecs.create(ElfFile.class,
                    new CodecFactory[]{elfCodecFactory},
                    new CodecDecorator[]{codecDecorator});
            ElfFile elfFile = Codecs.decode(fileCodec, path.toFile());

            // Run the DWARF post-processor over the constructed file
            DwarfSectionPostProcessor dwarfSectionPostProcessor = new DwarfSectionPostProcessor(ident);
            for (ElfSection section : elfFile.getSections()) {
                dwarfSectionPostProcessor.process(section);
            }
            dwarfSectionPostProcessor.completeProcessing(elfFile);

            return elfFile;
        }catch (DecodingException e) {
            throw new UnknownFormatException(e);
        }
    }
}
