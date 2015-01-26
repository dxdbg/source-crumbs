/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
