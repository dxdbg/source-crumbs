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

package net.sourcecrumbs.refimpl.elf.spec.sym;

import org.codehaus.preon.annotation.BoundEnumOption;
import org.codehaus.preon.util.EnumUtils;

import net.sourcecrumbs.refimpl.elf.spec.Address;
import net.sourcecrumbs.refimpl.elf.spec.WordField;
import net.sourcecrumbs.refimpl.elf.spec.constants.SymbolBinding;
import net.sourcecrumbs.refimpl.elf.spec.constants.SymbolType;

/**
 * Encapsulates an ELF symbol the layout for which differs depending on the machine type for the file
 *
 * @author mcnulty
 */
public abstract class ElfSymbol {

    /** The name index for an undefined symbol */
    public static final int STN_UNDEF = 0;

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

    public SymbolBinding getSymbolBinding() {
        return EnumUtils.getBoundEnumOptionIndex(SymbolBinding.class).get(getInfo() >> 4);
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
        return EnumUtils.getBoundEnumOptionIndex(SymbolType.class).get(getInfo() & 0xf);
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
