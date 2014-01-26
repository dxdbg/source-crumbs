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
 * A tag for a DynamicEntry
 *
 * @author mcnulty
 */
public enum DynamicTag {

    @BoundEnumOption(0)
    DT_NULL,

    @BoundEnumOption(1)
    DT_NEEDED,

    @BoundEnumOption(2)
    DT_PLTRELSZ,

    @BoundEnumOption(3)
    DT_PLTGOT,

    @BoundEnumOption(4)
    DT_HASH,

    @BoundEnumOption(5)
    DT_STRTAB,

    @BoundEnumOption(6)
    DT_SYMTAB,

    @BoundEnumOption(7)
    DT_RELA,

    @BoundEnumOption(8)
    DT_RELASZ,

    @BoundEnumOption(9)
    DT_RELAENT,

    @BoundEnumOption(10)
    DT_STRSZ,

    @BoundEnumOption(11)
    DT_SYMENT,

    @BoundEnumOption(12)
    DT_INIT,

    @BoundEnumOption(13)
    DT_FINI,

    @BoundEnumOption(14)
    DT_SONAME,

    @BoundEnumOption(15)
    DT_RPATH,

    @BoundEnumOption(16)
    DT_SYMBOLIC,

    @BoundEnumOption(17)
    DT_REL,

    @BoundEnumOption(18)
    DT_RELSZ,

    @BoundEnumOption(19)
    DT_RELENT,

    @BoundEnumOption(20)
    DT_PLTREL,

    @BoundEnumOption(21)
    DT_DEBUG,

    @BoundEnumOption(22)
    DT_TEXTREL,

    @BoundEnumOption(23)
    DT_JMPREL,

    @BoundEnumOption(24)
    DT_BIND_NOW,

    @BoundEnumOption(0x70000000)
    DT_LOPROC,

    @BoundEnumOption(0x7fffffff)
    DT_HIPROC,

    UNKNOWN;
}
