/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.file.tests;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Container for native-file-tests metadata
 *
 * @author mcnulty
 */
public final class NativeFileTestsInfo
{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, List<Path>> objectPaths = new HashMap<>();
    private final Map<String, List<Path>> executablePaths = new HashMap<>();

    public NativeFileTestsInfo(Path basePath) throws IOException
    {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(basePath, "*.json.*")) {
            for (Path jsonFile : stream)
            {
                String baseName = jsonFile.getFileName().toString().split("\\.")[0];

                try (InputStream jsonFileStream = Files.newInputStream(jsonFile)) {
                    NativeFileMetadata metadata = objectMapper.readValue(jsonFileStream, NativeFileMetadata.class);

                    String objectFileName = baseName + ".o." +
                            metadata.getObjectSha1();
                    List<Path> objectFilePaths = objectPaths.get(baseName);
                    if (objectFilePaths == null)
                    {
                        objectFilePaths = new LinkedList<>();
                        objectPaths.put(baseName, objectFilePaths);
                    }
                    objectFilePaths.add(Paths.get(basePath.toAbsolutePath().toString(), objectFileName));

                    String executableFileName = baseName + "." +
                            metadata.getExecutableSha1();
                    List<Path> executableFilePaths = executablePaths.get(baseName);
                    if (executableFilePaths == null)
                    {
                        executableFilePaths = new LinkedList<>();
                        executablePaths.put(baseName, executableFilePaths);
                    }
                    executableFilePaths.add(Paths.get(basePath.toAbsolutePath().toString(), executableFileName));
                }
            }
        }
    }

    public Path getFirstObjectPath(String objectFileName)
    {
        List<Path> objectFilePaths = objectPaths.get(objectFileName);
        assertNotNull(objectFilePaths);
        assertTrue(objectFilePaths.size() > 0);
        return objectFilePaths.get(0);
    }

    public Path getFirstExecutablePath(String executableFileName)
    {
        List<Path> executableFilePaths = executablePaths.get(executableFileName);
        assertNotNull(executableFilePaths);
        assertTrue(executableFilePaths.size() > 0);
        return executableFilePaths.get(0);
    }

    public List<Path> getObjectPaths()
    {
        return objectPaths.values()
                          .stream()
                          .flatMap(Collection::stream)
                          .collect(Collectors.toList());
    }

    public List<Path> getExecutablePaths()
    {
        return executablePaths.values()
                              .stream()
                              .flatMap(Collection::stream)
                              .collect(Collectors.toList());
    }

}
