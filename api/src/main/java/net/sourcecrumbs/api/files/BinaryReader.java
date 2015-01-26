/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.files;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Abstract factory for reading native binaries
 *
 * @author mcnulty
 */
public interface BinaryReader
{
    /**
     * Opens the binary at the specified path
     *
     * @param path the path
     * @return the binary
     *
     * @throws IOException on failure to open the binary
     * @throws UnknownFormatException if the binary is in a format that is unknown or unsupported
     */
    Binary open(Path path) throws IOException, UnknownFormatException;

    /**
     * Opens an executable at the specified path
     *
     * @param path the path
     * @return the executable
     *
     * @throws IOException on failure to open the binary
     * @throws UnknownFormatException if the binary is in a format that is unknown or unsupported
     */
    Executable openExecutable(Path path) throws IOException, UnknownFormatException;

    /**
     * Opens a core file at the specified path
     *
     * @param path the path
     * @return the core file
     *
     * @throws IOException on failure to open the binary
     * @throws UnknownFormatException if the binary is in a format that is unknown or unsupported
     */
    CoreFile openCoreFile(Path path) throws IOException, UnknownFormatException;

    /**
     * Opens a library at the specified path
     *
     * @param path the path
     * @return the library file
     *
     * @throws IOException on failure to open the binary
     * @throws UnknownFormatException if the binary is in a format that is unknown or unsupported
     */
    Library openLibrary(Path path) throws IOException, UnknownFormatException;

    /**
     * Opens an object file at the specified path
     *
     * @param path the path
     * @return the object file
     *
     * @throws IOException on failure to open the binary
     * @throws UnknownFormatException if the binary is in a format that is unknown or unsupported
     */
    ObjectFile openObjectFile(Path path) throws IOException, UnknownFormatException;
}
