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

import net.sourcecrumbs.refimpl.dwarf.elf.DwarfSectionPostProcessor;
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
        List<ElfSectionPostProcessor> postProcessors = new ArrayList<>();
        postProcessors.add(new DwarfSectionPostProcessor());
        reader = new ElfReader(postProcessors);
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
