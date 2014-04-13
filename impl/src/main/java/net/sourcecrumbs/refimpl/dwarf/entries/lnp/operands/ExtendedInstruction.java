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

    @If("opcodeValue == ExtendedOpcodeValues.DW_LNE_set_address || opcodeValue == ExtendedOpcodeValues.DW_LNE_define_file")
    @BoundObject(
            selectFrom = @Choices(
                    alternatives = {
                            @Choice(condition="opcodeValue == ExtendedOpcodeValues.DW_LNE_set_address", type=SetAddress.class),
                            @Choice(condition="opcodeValue == ExtendedOpcodeValues.DW_LNE_define_file", type=DefineFile.class),
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
                    operation = new EndSequence();
                    break;
                case DW_LNE_set_discriminator:
                    operation = new SetDiscriminator();
                    break;
                default:
                    break;
            }
        }
    }
}
