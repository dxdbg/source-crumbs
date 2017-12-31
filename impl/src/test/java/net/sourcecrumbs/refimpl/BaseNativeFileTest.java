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
import java.nio.file.Paths;
import java.util.List;

import org.junit.BeforeClass;

import net.libudi.nativefiletests.NativeFileTestsInfo;

/**
 * A base test that can be used to retrieve a file from a URL that is used for the test
 */
public abstract class BaseNativeFileTest {

    private static final Path basePath = Paths.get(System.getProperty("native.file.tests.basePath"));
    private static NativeFileTestsInfo info = null;

    @BeforeClass
    public static void initMetadata() throws IOException
    {
        info = new NativeFileTestsInfo(basePath);
    }

    protected static Path getFirstObjectPath(String objectFileName)
    {
        return info.getFirstObjectPath(objectFileName);
    }

    protected static Path getFirstExecutablePath(String executableFileName)
    {
        return info.getFirstExecutablePath(executableFileName);
    }

    protected static List<Path> getObjectPaths()
    {
        return info.getObjectPaths();
    }

    protected static List<Path> getExecutablePaths()
    {
        return info.getExecutablePaths();
    }
}
