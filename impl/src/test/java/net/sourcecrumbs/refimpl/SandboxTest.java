/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sourcecrumbs.api.debug.symbols.Function;
import net.sourcecrumbs.api.files.Executable;
import net.sourcecrumbs.api.machinecode.MachineCodeMapping;
import net.sourcecrumbs.api.machinecode.SourceLineRange;
import net.sourcecrumbs.refimpl.BaseNativeFileTest;
import net.sourcecrumbs.refimpl.elf.ElfExecutable;
import net.sourcecrumbs.refimpl.elf.ElfReader;
import net.sourcecrumbs.refimpl.elf.spec.ElfSegment;
import net.sourcecrumbs.refimpl.elf.spec.constants.MachineType;
import net.sourcecrumbs.refimpl.elf.spec.sections.SymbolTable;
import net.sourcecrumbs.refimpl.elf.spec.segments.InterpreterSegment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        ElfReader reader = new ElfReader();

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

        MachineCodeMapping mapping = exec.getMachineCodeMapping();
        long nextAddress = 0x4004e0;
        while (nextAddress != 0) {

            System.out.printf("0x%x", nextAddress);

            List<SourceLineRange> sourceLineRanges = mapping.getSourceLinesRanges(nextAddress);
            for (SourceLineRange range : sourceLineRanges) {
                System.out.printf(" [%d,%d] %s", range.getLineRange().getStart(), range.getLineRange().getEnd(),
                        range.getTranslationUnit().getPath().toString());
            }

            System.out.println();

            nextAddress = mapping.getNextStatementAddress(nextAddress);
        }
    }

    @Test
    public void loadSymbols() throws Exception
    {
        ElfReader reader = new ElfReader();

        Executable exec = reader.openExecutable(filePath);
        assertTrue(exec instanceof ElfExecutable);

        Function main = exec.getFunction("main");
        assertNotNull(main);
        assertEquals("main", main.getName());
        assertEquals("int", main.getReturnType().getName());
        assertEquals(2, main.getFormalParameters().size());
        assertEquals("argc", main.getFormalParameters().get(0).getName());
        assertEquals("int", main.getFormalParameters().get(0).getType().getName());
        assertEquals("argv", main.getFormalParameters().get(1).getName());
        assertEquals("char**", main.getFormalParameters().get(1).getType().getName());
    }

    @Override
    protected URL getFileUrl() throws MalformedURLException {
        return new URL("http://mcnulty.github.io/native-file-tests/files/linux/clang/3.2-11/basic-64bit-dynamic");
    }
}
