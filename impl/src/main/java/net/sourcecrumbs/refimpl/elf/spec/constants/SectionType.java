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

package net.sourcecrumbs.refimpl.elf.spec.constants;

import org.codehaus.preon.annotation.BoundEnumOption;

/**
 * The type of section in an ELF file
 *
 * @author mcnulty
 */
public enum SectionType {

    @BoundEnumOption(0)
    SHT_NULL,

    @BoundEnumOption(1)
    SHT_PROGBITS,

    @BoundEnumOption(2)
    SHT_SYMTAB,

    @BoundEnumOption(3)
    SHT_STRTAB,

    @BoundEnumOption(4)
    SHT_RELA,

    @BoundEnumOption(5)
    SHT_HASH,

    @BoundEnumOption(6)
    SHT_DYNAMIC,

    @BoundEnumOption(7)
    SHT_NOTE,

    @BoundEnumOption(8)
    SHT_NOBITS,

    @BoundEnumOption(9)
    SHT_REL,

    @BoundEnumOption(10)
    SHT_SHLIB,

    @BoundEnumOption(11)
    SHT_DYNSYM,

    @BoundEnumOption(14)
    SHT_INIT_ARRAY,

    @BoundEnumOption(15)
    SHT_FINI_ARRAY,

    @BoundEnumOption(16)
    SHT_PREINIT_ARRAY,

    @BoundEnumOption(17)
    SHT_GROUP,

    @BoundEnumOption(18)
    SHT_SYMTAB_SHNDX,

    UNKNOWN;
}
