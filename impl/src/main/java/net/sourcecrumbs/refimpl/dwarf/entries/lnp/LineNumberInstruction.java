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

package net.sourcecrumbs.refimpl.dwarf.entries.lnp;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.BoundObject;
import org.codehaus.preon.annotation.Choices;
import org.codehaus.preon.annotation.Choices.Choice;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.annotation.Init;
import org.codehaus.preon.el.ImportStatic;

import net.sourcecrumbs.refimpl.dwarf.constants.StandardOpcode;
import net.sourcecrumbs.refimpl.dwarf.constants.StandardOpcodeValues;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.AdvanceLine;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.AdvancePc;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.ConstAddPc;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.Copy;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.ExtendedInstruction;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.FixedAdvancePc;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.LineNumberOperation;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.NegateStatement;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.SetBasicBlock;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.SetColumn;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.SetEpilogueBegin;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.SetFile;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.SetIsa;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.SetPrologueEnd;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands.SpecialOpcode;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberRow;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberState;

/**
 * An instruction in a line number program
 *
 * @author mcnulty
 */
@ImportStatic(StandardOpcodeValues.class)
public class LineNumberInstruction implements LineNumberOperation {

    @BoundNumber(size = "8")
    private short opcodeValue;

    private StandardOpcode opcode;

    @If(
            "opcodeValue == StandardOpcodeValues.DW_LNS_extended || " +
            "opcodeValue == StandardOpcodeValues.DW_LNS_advance_pc || " +
            "opcodeValue == StandardOpcodeValues.DW_LNS_advance_line || " +
            "opcodeValue == StandardOpcodeValues.DW_LNS_set_file || " +
            "opcodeValue == StandardOpcodeValues.DW_LNS_set_column || " +
            "opcodeValue == StandardOpcodeValues.DW_LNS_fixed_advance_pc || " +
            "opcodeValue == StandardOpcodeValues.DW_LNS_set_isa"
    )
    @BoundObject(
            selectFrom = @Choices(
                    alternatives = {
                            @Choice(condition="opcodeValue == StandardOpcodeValues.DW_LNS_extended", type= ExtendedInstruction.class),
                            @Choice(condition="opcodeValue == StandardOpcodeValues.DW_LNS_advance_pc", type=AdvancePc.class),
                            @Choice(condition="opcodeValue == StandardOpcodeValues.DW_LNS_advance_line", type=AdvanceLine.class),
                            @Choice(condition="opcodeValue == StandardOpcodeValues.DW_LNS_set_file", type=SetFile.class),
                            @Choice(condition="opcodeValue == StandardOpcodeValues.DW_LNS_set_column", type=SetColumn.class),
                            @Choice(condition="opcodeValue == StandardOpcodeValues.DW_LNS_fixed_advance_pc", type=FixedAdvancePc.class),
                            @Choice(condition="opcodeValue == StandardOpcodeValues.DW_LNS_set_isa", type=SetIsa.class),
                    }
            )
    )
    private LineNumberOperation operation = null;

    @Init
    public void init() {
        // Initialize the operations without any arguments encoded into the instruction
        opcode = StandardOpcode.getOpcode((byte)opcodeValue);
        if (operation == null && opcode != null) {
            switch (opcode) {
                case DW_LNS_copy:
                    operation = Copy.INSTANCE;
                    break;
                case DW_LNS_negate_stmt:
                    operation = NegateStatement.INSTANCE;
                    break;
                case DW_LNS_set_basic_block:
                    operation = SetBasicBlock.INSTANCE;
                    break;
                case DW_LNS_const_add_pc:
                    operation = ConstAddPc.INSTANCE;
                    break;
                case DW_LNS_set_epilogue_begin:
                    operation = SetEpilogueBegin.INSTANCE;
                    break;
                case DW_LNS_set_prologue_end:
                    operation = SetPrologueEnd.INSTANCE;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state) {
        if (opcodeValue >= header.getOpcodeBase()) {
            return SpecialOpcode.apply(opcodeValue, header, state);
        }

        if (operation != null) {
            return operation.apply(header, state);
        }

        throw new IllegalStateException("Cannot apply instruction with opcode " + opcodeValue);
    }
}
