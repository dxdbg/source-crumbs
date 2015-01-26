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
public enum SegmentFlag {

    @BoundEnumOption(0x1)
    PF_X(0x1),

    @BoundEnumOption(0x2)
    PF_W(0x2),

    @BoundEnumOption(0x4)
    PF_R(0x4),

    @BoundEnumOption(0xf0000000)
    PF_MASKPROC(0xf0000000),

    // Arbitrary value to represent unknown
    UNKNOWN(0x0000f000);

    private final int flagValue;

    private SegmentFlag(int flagValue) {
        this.flagValue = flagValue;
    }

    public static EnumSet<SegmentFlag> setFromBitValue(int value) {
        EnumSet<SegmentFlag> result = null;
        for (SegmentFlag element : values()) {
            if ((element.flagValue & value) != 0) {
                if (result == null) {
                    result = EnumSet.of(element);
                }else{
                    result.add(element);
                }
            }
        }

        if (result == null) {
            return EnumSet.noneOf(SegmentFlag.class);
        }

        return result;
    }

    public static int toBitValue(EnumSet<SegmentFlag> flags) {
        int value = 0;
        for (SegmentFlag flag : flags) {
            value |= flag.flagValue;
        }
        return value;
    }
}
