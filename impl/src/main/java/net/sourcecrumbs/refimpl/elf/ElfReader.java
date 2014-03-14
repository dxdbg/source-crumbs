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

    private final List<ElfSectionPostProcessor> postProcessors = new ArrayList<>();

    /**
     * Constructors.
     *
     * @param postProcessors the section post processors
     */
    public ElfReader(List<ElfSectionPostProcessor> postProcessors) {
        this.postProcessors.addAll(postProcessors);
    }

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

            int classLength;
            switch (ident.getElfClass()) {
                case ELFCLASS32:
                    classLength = 32;
                    break;
                case ELFCLASS64:
                    classLength = 64;
                    break;
                default:
                    throw new UnknownFormatException("Unknown ELF class " + ident.getElfClass());
            }

            ByteOrder byteOrder;
            switch (ident.getDataEncoding()) {
                case ELFDATA2MSB:
                    byteOrder = ByteOrder.BigEndian;
                    break;
                case ELFDATA2LSB:
                    byteOrder = ByteOrder.LittleEndian;
                    break;
                default:
                    throw new UnknownFormatException("Unknown ELF data encoding " + ident.getDataEncoding());
            }

            ElfCodecFactory elfCodecFactory = new ElfCodecFactory(classLength, byteOrder);

            Codec<ElfFile> fileCodec = Codecs.create(ElfFile.class,
                    new CodecFactory[]{elfCodecFactory},
                    new CodecDecorator[]{codecDecorator});
            ElfFile elfFile = Codecs.decode(fileCodec, path.toFile());

            // Run the post-processors over the constructed file
            for (ElfSectionPostProcessor postProcessor : postProcessors) {
                for (ElfSection section : elfFile.getSections()) {
                    postProcessor.process(section);
                }
            }

            return elfFile;
        }catch (DecodingException e) {
            throw new UnknownFormatException(e);
        }
    }
}
