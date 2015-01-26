/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sections;

import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.Choices;
import org.codehaus.preon.annotation.Choices.Choice;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.el.ImportStatic;

import net.sourcecrumbs.refimpl.elf.spec.constants.ElfClass;
import net.sourcecrumbs.refimpl.elf.spec.sym.Elf32Symbol;
import net.sourcecrumbs.refimpl.elf.spec.sym.Elf64Symbol;
import net.sourcecrumbs.refimpl.elf.spec.sym.ElfSymbol;

/**
 * A symbol table contained in an ELF section
 *
 * @author mcnulty
 */
@ImportStatic(ElfClass.class)
public class SymbolTable implements SectionContent {

    @If("outer.sectionHeader.entrySize.value > 0")
    @BoundList(
            size = "outer.sectionHeader.size.value / outer.sectionHeader.entrySize.value",
            selectFrom = @Choices(
                    alternatives = {
                            @Choice(condition = "outer.outer.header.ident.elfClass == ElfClass.ELFCLASS32", type = Elf32Symbol.class),
                            @Choice(condition = "outer.outer.header.ident.elfClass == ElfClass.ELFCLASS64", type = Elf64Symbol.class)
                    }
            )
    )
    private ElfSymbol[] symbols;

    public ElfSymbol[] getSymbols() {
        return symbols;
    }

    public void setSymbols(ElfSymbol[] symbols) {
        this.symbols = symbols;
    }
}
