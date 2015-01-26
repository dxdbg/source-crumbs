/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
