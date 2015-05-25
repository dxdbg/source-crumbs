/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.primitives.UnsignedBytes;

import net.sourcecrumbs.api.files.Executable;
import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.dwarf.entries.AttributeValue;
import net.sourcecrumbs.refimpl.dwarf.entries.CompilationUnit;
import net.sourcecrumbs.refimpl.dwarf.entries.DIE;
import net.sourcecrumbs.refimpl.dwarf.sections.DebugInfo;
import net.sourcecrumbs.refimpl.elf.ElfExecutable;
import net.sourcecrumbs.refimpl.elf.ElfReader;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;

/**
 * @author mcnulty
 */
public final class DwarfDump
{
    private DwarfDump()
    {
    }

    private static void printCompilationUnit(CompilationUnit compilationUnit)
    {
        System.out.printf("Compilation Unit: Name(%s), Language(%s), Path(%s), CompilationDirectory(%s), Offset(0x%x)",
                compilationUnit.getName(),
                compilationUnit.getLanguage(),
                compilationUnit.getPath(),
                compilationUnit.getCompilationDirectory(),
                compilationUnit.getSectionOffset());
        System.out.println();
    }

    private static void printDIE(DIE die, String indent, StringTable stringTable)
    {
        System.out.print(indent);
        System.out.println(die.getTag() + "[" + Long.toHexString(die.getOffset()) + "]");
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

    public static void dwarfDump(Path filePath) throws UnknownFormatException, IOException
    {
        ElfReader reader = new ElfReader();

        Executable exec = reader.openExecutable(filePath);

        ElfExecutable elfExec = (ElfExecutable) exec;

        DebugInfo debugInfo = (DebugInfo) elfExec.getElfFile().getSection(DebugInfo.SECTION_NAME).getSectionContent();

        // Dump the DIE tree
        for (CompilationUnit compilationUnit : debugInfo.getCompilationUnits()) {
            printCompilationUnit(compilationUnit);
            printDIE(compilationUnit.getRootDIE(), "", compilationUnit.getStringTable());
        }
    }

    public static void main(String[] args)
    {
        try {
            DwarfDump.dwarfDump(Paths.get(args[0]));
        }catch (Exception e) {
            System.err.println("Failed to dump DWARF data from " + args[0]);
            e.printStackTrace();
        }
    }
}
