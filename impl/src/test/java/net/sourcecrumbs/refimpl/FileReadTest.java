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
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import net.sourcecrumbs.refimpl.elf.ElfReader;

/**
 * A test that validates reading in all files from the native-file-tests repository doesn't result in an exception
 *
 * @author mcnulty
 */
@RunWith(Parameterized.class)
public class FileReadTest extends BaseNativeFileTest {

    private static ElfReader reader;
    static {
        reader = new ElfReader();
    }

    private final Path nativeFilePath;

    @Parameters
    public static Collection<Object[]> getPaths() throws IOException {
        initMetadata();

        return Stream.concat(getObjectPaths().stream(), getExecutablePaths().stream())
                     .map(p -> new Object[]{ p })
                     .collect(Collectors.toList());
    }

    /**
     * Constructor.
     *
     * @param nativeFilePath
     */
    public FileReadTest(Path nativeFilePath) {
        this.nativeFilePath = nativeFilePath;
    }

    @Test
    public void readFile() throws Exception {

        // Validate that this call does not cause any exceptions
        System.out.println(nativeFilePath.toAbsolutePath().toString());
        reader.open(nativeFilePath);
    }
}
