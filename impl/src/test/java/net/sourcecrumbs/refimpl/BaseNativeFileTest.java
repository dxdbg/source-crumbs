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
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.BeforeClass;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertNotNull;

/**
 * A base test that can be used to retrieve a file from a URL that is used for the test
 *
 * @author mcnulty
 */
public abstract class BaseNativeFileTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Path basePath = Paths.get(System.getProperty("native.file.tests.basePath"));
    private static final Map<String, Path> objectPaths = new HashMap<>();
    private static final Map<String, Path> executablePaths = new HashMap<>();

    @BeforeClass
    public static void initMetadata() throws IOException
    {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(basePath, "*.json.*")) {
            for (Path jsonFile : stream)
            {
                String baseName = jsonFile.getFileName().toString().split("\\.")[0];

                try (InputStream jsonFileStream = Files.newInputStream(jsonFile)) {
                    NativeFileMetadata metadata = objectMapper.readValue(jsonFileStream, NativeFileMetadata.class);
                    String objectFileName = baseName + ".o." +
                            metadata.getObjectSha1();
                    objectPaths.put(objectFileName, Paths.get(basePath.toAbsolutePath().toString(),
                            objectFileName));
                    String executableFileName = baseName + "." +
                            metadata.getExecutableSha1();
                    executablePaths.put(executableFileName, Paths.get(basePath.toAbsolutePath().toString(),
                            executableFileName));
                }
            }
        }
    }

    protected static Path getObjectPath(String objectFileName)
    {
        Path objectFilePath = objectPaths.get(objectFileName);
        assertNotNull(objectFilePath);
        return objectFilePath;
    }

    protected static Path getExecutablePath(String executableFileName)
    {
        Path executableFilePath = executablePaths.get(executableFileName);
        assertNotNull(executableFilePath);
        return executableFilePath;
    }

    protected static List<Path> getObjectPaths()
    {
        return objectPaths.values().stream().collect(Collectors.toList());
    }

    protected static List<Path> getExecutablePaths()
    {
        return executablePaths.values().stream().collect(Collectors.toList());
    }
}
