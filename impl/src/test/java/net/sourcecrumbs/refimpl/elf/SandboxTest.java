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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sourcecrumbs.api.files.Executable;
import net.sourcecrumbs.refimpl.BaseNativeFileTest;
import net.sourcecrumbs.refimpl.dwarf.DwarfSectionPostProcessor;
import net.sourcecrumbs.refimpl.elf.spec.ElfSegment;
import net.sourcecrumbs.refimpl.elf.spec.constants.MachineType;
import net.sourcecrumbs.refimpl.elf.spec.sections.SymbolTable;
import net.sourcecrumbs.refimpl.elf.spec.segments.InterpreterSegment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Throw-away test used to experiment with the library
 *
 * @author mcnulty
 */
@Ignore
public class SandboxTest extends BaseNativeFileTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void loadExec() throws Exception {
        List<ElfSectionPostProcessor> postProcessors = new ArrayList<>();
        postProcessors.add(new DwarfSectionPostProcessor());
        ElfReader reader = new ElfReader(postProcessors);

        Executable exec = reader.openExecutable(filePath);
        assertTrue(exec instanceof ElfExecutable);

        ElfExecutable elfExec = (ElfExecutable) exec;
        assertEquals(MachineType.EM_X86_64, elfExec.getElfFile().getHeader().getMachineType());

        ((SymbolTable)elfExec.getElfFile().getSection(".dynsym").getSectionContent()).getSymbols()[1].getSymbolType();
        for (ElfSegment segment : elfExec.getElfFile().getSegments()) {
            if (segment.getSegmentContent() instanceof InterpreterSegment) {
                ((InterpreterSegment) segment.getSegmentContent()).getInterpreterPath();
            }
        }

        objectMapper.writer(new DefaultPrettyPrinter()).writeValue(System.out, elfExec.getElfFile());
    }

    @Override
    protected URL getFileUrl() throws MalformedURLException {
        return new URL("http://mcnulty.github.io/native-file-tests/files/linux/gcc/4.8.2/basic-64bit-dynamic");
    }
}
