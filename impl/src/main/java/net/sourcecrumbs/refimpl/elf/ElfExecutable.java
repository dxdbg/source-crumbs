/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf;

import java.util.Collections;
import java.util.List;

import net.sourcecrumbs.api.debug.symbols.Function;
import net.sourcecrumbs.api.files.Executable;
import net.sourcecrumbs.api.files.Library;
import net.sourcecrumbs.api.machinecode.MachineCodeMapping;
import net.sourcecrumbs.api.symbols.Symbol;
import net.sourcecrumbs.api.transunit.TranslationUnit;
import net.sourcecrumbs.api.debug.symbols.Variable;
import net.sourcecrumbs.refimpl.elf.spec.ElfFile;

/**
 * High-level abstraction of an ELF executable
 *
 * @author mcnulty
 */
public class ElfExecutable extends Executable implements ELF {

    private final ElfFile elfFile;

    public ElfExecutable(ElfFile elfFile) {
        this.elfFile = elfFile;
    }

    @Override
    public ElfFile getElfFile() {
        return elfFile;
    }

    @Override
    public List<Library> getLibraries() {
        return Collections.<Library>emptyList();
    }

    @Override
    public MachineCodeMapping getMachineCodeMapping() {
        return elfFile.getMachineCodeMapping();
    }

    @Override
    public Iterable<Symbol> getSymbols() {
        return elfFile.getSymbols();
    }

    @Override
    public Symbol getSymbol(String name)
    {
        return elfFile.getSymbol(name);
    }

    @Override
    public Iterable<TranslationUnit> getTranslationUnits() {
        return elfFile.getTranslationUnits();
    }

    @Override
    public TranslationUnit getContainingTranslationUnit(long pc)
    {
        return elfFile.getContainingTranslationUnit(pc);
    }

    @Override
    public Iterable<Variable> getGlobalVariables()
    {
        return elfFile.getGlobalVariables();
    }

    @Override
    public Variable getGlobalVariable(String name)
    {
        return elfFile.getGlobalVariable(name);
    }

    @Override
    public Iterable<Function> getFunctions()
    {
        return elfFile.getFunctions();
    }

    @Override
    public Function getFunction(String name)
    {
        return elfFile.getFunction(name);
    }

    @Override
    public Function getContainingFunction(long pc)
    {
        return elfFile.getContainingFunction(pc);
    }
}
