/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.constants;

import org.codehaus.preon.annotation.BoundEnumOption;

/**
 * Enumeration for extended opcodes in a line number program
 *
 * @author mcnulty
 */
public enum ExtendedOpcode {

    DW_LNE_end_sequence((byte)0x01),

    DW_LNE_set_address((byte)0x02),

    DW_LNE_define_file((byte)0x03),

    DW_LNE_set_discriminator((byte)0x04),

    DW_LNE_lo_user((byte)0x80),

    DW_LNE_hi_user((byte)0xff);

    private final byte value;

    private ExtendedOpcode(byte value) {
        this.value = value;
    }

    public static ExtendedOpcode getOpcode(byte value) {
        for (ExtendedOpcode opcode : ExtendedOpcode.values()) {
            if (opcode.value == value) {
                return opcode;
            }
        }
        return null;
    }
}
