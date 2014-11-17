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

package net.sourcecrumbs.refimpl.dwarf;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.google.common.primitives.UnsignedBytes;

import net.sourcecrumbs.api.files.Executable;
import net.sourcecrumbs.refimpl.BaseNativeFileTest;
import net.sourcecrumbs.refimpl.dwarf.entries.AttributeValue;
import net.sourcecrumbs.refimpl.dwarf.entries.CompilationUnit;
import net.sourcecrumbs.refimpl.dwarf.entries.DIE;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;
import net.sourcecrumbs.refimpl.elf.ElfExecutable;
import net.sourcecrumbs.refimpl.elf.ElfReader;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;

import static org.junit.Assert.assertTrue;

/**
 * Utility test to dump DWARF
 *
 * @author mcnulty
 */
public class DwarfDump extends BaseNativeFileTest
{
    private void printCompilationUnit(CompilationUnit compilationUnit)
    {
        System.out.printf("Compilation Unit: Name(%s), Language(%s), Path(%s), CompilationDirectory(%s)",
                compilationUnit.getName(),
                compilationUnit.getLanguage(),
                compilationUnit.getPath(),
                compilationUnit.getCompilationDirectory());
        System.out.println();
    }

    private void printDIE(DIE die, String indent, StringTable stringTable)
    {
        System.out.print(indent);
        System.out.println(die.getTag());
        for (AttributeValue v : die.getAttributeValues()) {
            StringBuilder builder = new StringBuilder();
            switch (v.getForm()) {
                case DW_FORM_string:
                    builder.append("\"").append(v.getDataAsString()).append("\"");
                    break;
                case DW_FORM_strp:
                    builder.append("\"").append(v.getDataAsString(stringTable)).append("\"");
                    break;
                default:
                    builder.append("{ ");
                    for (int i = 0; i < v.getData().length; ++i) {
                        builder.append(Integer.toHexString(UnsignedBytes.toInt(v.getData()[i])));

                        if (i < v.getData().length - 1) {
                            builder.append(",");
                        }
                    }
                    builder.append(" }");
                    break;
            }

            System.out.print(indent);
            System.out.printf("- %s[%s] = %s", v.getName(), v.getForm(), builder.toString());
            System.out.println();
        }

        for (DIE child : die.getChildren()) {
            printDIE(child, "||||" + indent, stringTable);
        }
    }

    @Test
    public void dumpUrl() throws Exception
    {
        ElfReader reader = new ElfReader();

        Executable exec = reader.openExecutable(filePath);
        assertTrue(exec instanceof ElfExecutable);

        ElfExecutable elfExec = (ElfExecutable) exec;

        DebugInfo debugInfo = (DebugInfo) elfExec.getElfFile().getSection(DebugInfo.SECTION_NAME).getSectionContent();

        // Dump the DIE tree
        for (CompilationUnit compilationUnit : debugInfo.getCompilationUnits()) {
            printCompilationUnit(compilationUnit);
            printDIE(compilationUnit.getRootDIE(), "", compilationUnit.getStringTable());
        }
    }

    @Override
    protected URL getFileUrl() throws MalformedURLException
    {
        return new URL("http://mcnulty.github.io/native-file-tests/files/linux/gcc/4.8.3/simple-64bit-dynamic");
    }
}
