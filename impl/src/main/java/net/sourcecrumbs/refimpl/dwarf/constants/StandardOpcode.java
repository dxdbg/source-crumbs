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
