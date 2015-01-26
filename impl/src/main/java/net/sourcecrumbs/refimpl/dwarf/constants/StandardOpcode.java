/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.constants;

/**
 * Enumeration for standard opcodes in a line number program
 *
 * @author mcnulty
 */
public enum StandardOpcode {

    DW_LNS_extended(StandardOpcodeValues.DW_LNS_extended),

    DW_LNS_copy(StandardOpcodeValues.DW_LNS_copy),

    DW_LNS_advance_pc(StandardOpcodeValues.DW_LNS_advance_pc),

    DW_LNS_advance_line(StandardOpcodeValues.DW_LNS_advance_line),

    DW_LNS_set_file(StandardOpcodeValues.DW_LNS_set_file),

    DW_LNS_set_column(StandardOpcodeValues.DW_LNS_set_column),

    DW_LNS_negate_stmt(StandardOpcodeValues.DW_LNS_negate_stmt),

    DW_LNS_set_basic_block(StandardOpcodeValues.DW_LNS_set_basic_block),

    DW_LNS_const_add_pc(StandardOpcodeValues.DW_LNS_const_add_pc),

    DW_LNS_fixed_advance_pc(StandardOpcodeValues.DW_LNS_fixed_advance_pc),

    DW_LNS_set_prologue_end(StandardOpcodeValues.DW_LNS_set_prologue_end),

    DW_LNS_set_epilogue_begin(StandardOpcodeValues.DW_LNS_set_epilogue_begin),

    DW_LNS_set_isa(StandardOpcodeValues.DW_LNS_set_isa);

    private final byte value;

    private StandardOpcode(byte value) {
        this.value = value;
    }

    public static StandardOpcode getOpcode(byte value) {
        for (StandardOpcode opcode : StandardOpcode.values()) {
            if (opcode.value == value) {
                return opcode;
            }
        }
        return null;
    }
}
