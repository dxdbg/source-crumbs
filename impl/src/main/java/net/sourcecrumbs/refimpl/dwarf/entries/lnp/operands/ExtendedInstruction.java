/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp.operands;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.BoundObject;
import org.codehaus.preon.annotation.Choices;
import org.codehaus.preon.annotation.Choices.Choice;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.annotation.Init;
import org.codehaus.preon.el.ImportStatic;

import net.sourcecrumbs.refimpl.dwarf.constants.ExtendedOpcode;
import net.sourcecrumbs.refimpl.dwarf.constants.ExtendedOpcodeValues;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.LineNumberProgramHeader;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberRow;
import net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm.LineNumberState;
import net.sourcecrumbs.refimpl.dwarf.preon.LEBSigned;
import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

/**
 * An extended instruction in a line number instruction
 *
 * @author mcnulty
 */
@ImportStatic(ExtendedOpcodeValues.class)
public class ExtendedInstruction implements LineNumberOperation {

    @Bound
    @LEBSigned(false)
    private LEB128 size;

    @BoundNumber(size = "8")
    private short opcodeValue;

    private ExtendedOpcode opcode;

    @If("opcodeValue == ExtendedOpcodeValues.DW_LNE_set_address || " +
        "opcodeValue == ExtendedOpcodeValues.DW_LNE_define_file || " +
        "opcodeValue == ExtendedOpcodeValues.DW_LNE_set_discriminator"
    )
    @BoundObject(
            selectFrom = @Choices(
                    alternatives = {
                            @Choice(condition="opcodeValue == ExtendedOpcodeValues.DW_LNE_set_address", type=SetAddress.class),
                            @Choice(condition="opcodeValue == ExtendedOpcodeValues.DW_LNE_define_file", type=DefineFile.class),
                            @Choice(condition="opcodeValue == ExtendedOpcodeValues.DW_LNE_set_discriminator", type=SetDiscriminator.class)
                    }
            )
    )
    private LineNumberOperation operation = null;

    @Init
    public void init() {
        opcode = ExtendedOpcode.getOpcode((byte)opcodeValue);
        if (operation == null && opcode != null) {
            switch (opcode) {
                case DW_LNE_end_sequence:
                    operation = EndSequence.INSTANCE;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public LineNumberRow apply(LineNumberProgramHeader header, LineNumberState state) {
        if (operation != null) {
            return operation.apply(header, state);
        }

        throw new IllegalStateException("Cannot apply instruction with opcode " + opcodeValue);
    }
}
