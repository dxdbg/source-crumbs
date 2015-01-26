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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sourcecrumbs.refimpl.dwarf.DwarfSectionPostProcessor;
import net.sourcecrumbs.refimpl.elf.ElfReader;
import net.sourcecrumbs.refimpl.elf.ElfSectionPostProcessor;

/**
 * A test that validates reading in all files from the native-file-tests repository doesn't result in an exception
 *
 * @author mcnulty
 */
@RunWith(Parameterized.class)
public class FileReadTest extends BaseNativeFileTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static URL nativeFileTestsBaseUrl;
    private static URL indexUrl;
    static {
        try {
            nativeFileTestsBaseUrl = new URL("http://mcnulty.github.io/native-file-tests/");
            indexUrl = new URL(nativeFileTestsBaseUrl, "index.json");
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ElfReader reader;
    static {
        reader = new ElfReader();
    }

    private final URL fileUrl;

    @Parameters
    public static Collection<Object[]> getUrls() throws IOException {
        try (InputStream indexStream = indexUrl.openStream()) {
            NativeFileTestsIndex index = objectMapper.readValue(indexStream, NativeFileTestsIndex.class);
            List<Object[]> urls = new ArrayList<>();
            for (String file : index.getFiles()) {
                urls.add(new URL[]{ new URL(nativeFileTestsBaseUrl, file) });
            }
            return urls;
        }
    }

    /**
     * Constructor.
     *
     * @param fileUrl the file
     */
    public FileReadTest(URL fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Test
    public void readFile() throws Exception {

        // Validate that this call does not cause any exceptions
        System.out.println(fileUrl);
        reader.open(filePath);
    }

    @Override
    protected URL getFileUrl() throws MalformedURLException {
        return fileUrl;
    }
}
