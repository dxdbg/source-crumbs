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

import java.util.EnumSet;

import org.codehaus.preon.annotation.BoundEnumOption;

/**
 * Flags associated with an ELF section.
 *
 * @author mcnulty
 */
public enum SectionFlag {

    @BoundEnumOption(0x1)
    SHF_WRITE(0x1),

    @BoundEnumOption(0x2)
    SHF_ALLOC(0x2),

    @BoundEnumOption(0x4)
    SHF_EXECINSTR(0x4),

    @BoundEnumOption(0xf0000000)
    SHF_MASKPROC(0xf0000000),

    // Arbitrary value to represent unknown
    UNKNOWN(0x0000f000);

    private final long flagValue;

    private SectionFlag(long flagValue) {
        this.flagValue = flagValue;
    }

    public static EnumSet<SectionFlag> setFromBitValue(long value) {
        EnumSet<SectionFlag> result = null;
        for (SectionFlag element : values()) {
            if ((element.flagValue & value) != 0) {
                if (result == null) {
                    result = EnumSet.of(element);
                }else{
                    result.add(element);
                }
            }
        }

        if (result == null) {
            return EnumSet.noneOf(SectionFlag.class);
        }

        return result;
    }

    public static long toBitValue(EnumSet<SectionFlag> flags) {
        long value = 0;
        for (SectionFlag flag : flags) {
            value |= flag.flagValue;
        }
        return value;
    }
}
