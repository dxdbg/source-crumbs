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

package net.sourcecrumbs.refimpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import net.sourcecrumbs.api.files.Binary;
import net.sourcecrumbs.api.files.BinaryReader;
import net.sourcecrumbs.api.files.CoreFile;
import net.sourcecrumbs.api.files.Executable;
import net.sourcecrumbs.api.files.Library;
import net.sourcecrumbs.api.files.ObjectFile;
import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.elf.ElfReader;

/**
 * A BinaryReader that can open any of the file formats supported.
 *
 * TODO this can be more intelligent, and only try to open a file with a reader if it has a chance of succeeding
 *
 * @author mcnulty
 */
public class CrossPlatformBinaryReader implements BinaryReader {

    private final List<BinaryReader> readers = new LinkedList<>();

    public CrossPlatformBinaryReader() {
        // Add all supported BinaryReaders
        readers.add(new ElfReader());
    }

    @Override
    public Binary open(Path path) throws IOException, UnknownFormatException {
        UnknownFormatException ex = null;
        for (BinaryReader reader : readers) {
            try {
                return reader.open(path);
            }catch (UnknownFormatException e) {
                ex = e;
            }
        }

        if (ex != null) {
            throw ex;
        }
        throw new UnknownFormatException("Failed to open " + path);
    }

    @Override
    public Executable openExecutable(Path path) throws IOException, UnknownFormatException {
        UnknownFormatException ex = null;
        for (BinaryReader reader : readers) {
            try {
                return reader.openExecutable(path);
            }catch (UnknownFormatException e) {
                ex = e;
            }
        }

        if (ex != null) {
            throw ex;
        }
        throw new UnknownFormatException("Failed to open " + path);
    }

    @Override
    public CoreFile openCoreFile(Path path) throws IOException, UnknownFormatException {
        UnknownFormatException ex = null;
        for (BinaryReader reader : readers) {
            try {
                return reader.openCoreFile(path);
            }catch (UnknownFormatException e) {
                ex = e;
            }
        }

        if (ex != null) {
            throw ex;
        }
        throw new UnknownFormatException("Failed to open " + path);
    }

    @Override
    public Library openLibrary(Path path) throws IOException, UnknownFormatException {
        UnknownFormatException ex = null;
        for (BinaryReader reader : readers) {
            try {
                return reader.openLibrary(path);
            }catch (UnknownFormatException e) {
                ex = e;
            }
        }

        if (ex != null) {
            throw ex;
        }
        throw new UnknownFormatException("Failed to open " + path);
    }

    @Override
    public ObjectFile openObjectFile(Path path) throws IOException, UnknownFormatException {
         UnknownFormatException ex = null;
        for (BinaryReader reader : readers) {
            try {
                return reader.openObjectFile(path);
            }catch (UnknownFormatException e) {
                ex = e;
            }
        }

        if (ex != null) {
            throw ex;
        }
        throw new UnknownFormatException("Failed to open " + path);
    }
}
