/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sym;

import org.codehaus.preon.annotation.BoundEnumOption;
import org.codehaus.preon.util.EnumUtils;

import net.sourcecrumbs.api.symbols.Symbol;
import net.sourcecrumbs.refimpl.elf.spec.Address;
import net.sourcecrumbs.refimpl.elf.spec.WordField;
import net.sourcecrumbs.refimpl.elf.spec.constants.SymbolBinding;
import net.sourcecrumbs.refimpl.elf.spec.constants.SymbolType;

/**
 * Encapsulates an ELF symbol the layout for which differs depending on the machine type for the file
 *
 * @author mcnulty
 */
public abstract class ElfSymbol implements Symbol
{
    /** The name index for an undefined symbol */
    public static final int STN_UNDEF = 0;

    protected String name;

    public abstract int getNameIndex();

    public abstract void setNameIndex(int nameIndex);

    public abstract Address getValue();

    public abstract void setValue(Address value);

    public abstract WordField getSize();

    public abstract void setSize(WordField size);

    public abstract byte getInfo();

    public abstract void setInfo(byte info);

    public abstract byte getOther();

    public abstract void setOther(byte other);

    public abstract short getSectionIndex();

    public abstract void setSectionIndex(short sectionIndex);

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getAddress()
    {
        Address value = getValue();
        if (value != null) {
            return value.getValue();
        }
        return 0L;
    }

    public SymbolBinding getSymbolBinding() {
        return EnumUtils.getBoundEnumOptionIndex(SymbolBinding.class).get((long)(getInfo() >> 4));
    }

    public void setSymbolBinding(SymbolBinding binding) throws IllegalArgumentException {
        try {
            BoundEnumOption option = SymbolBinding.class.getField(binding.name()).getAnnotation(BoundEnumOption.class);
            setInfo((byte)(((byte)(getInfo() & 0x0f)) | ((byte)option.value())));
        }catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public SymbolType getSymbolType() {
        return EnumUtils.getBoundEnumOptionIndex(SymbolType.class).get((long)(getInfo() & 0xf));
    }

    public void setSymbolType(SymbolType symbolType) throws IllegalArgumentException {
        try {
            BoundEnumOption option = SymbolType.class.getField(symbolType.name()).getAnnotation(BoundEnumOption.class);
            setInfo((byte)(((byte)(getInfo() & 0xf0)) | ((byte)option.value())));
        }catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e);
        }
    }


}
