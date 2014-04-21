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

import org.codehaus.preon.annotation.BoundEnumOption;

/**
 * Enumeration for tags in AbbreviationDeclarations
 *
 * @author mcnulty
 */
public enum AbbreviationTag {

    @BoundEnumOption(0x00)
    DW_TAG_null,

    @BoundEnumOption(0x01)
    DW_TAG_array_type,

    @BoundEnumOption(0x02)
    DW_TAG_class_type,

    @BoundEnumOption(0x03)
    DW_TAG_entry_point,

    @BoundEnumOption(0x04)
    DW_TAG_enumeration_type,

    @BoundEnumOption(0x05)
    DW_TAG_formal_parameter,

    @BoundEnumOption(0x08)
    DW_TAG_imported_declaration,

    @BoundEnumOption(0x0a)
    DW_TAG_label,

    @BoundEnumOption(0x0b)
    DW_TAG_lexical_block,

    @BoundEnumOption(0x0d)
    DW_TAG_member,

    @BoundEnumOption(0x0f)
    DW_TAG_pointer_type,

    @BoundEnumOption(0x10)
    DW_TAG_reference_type,

    @BoundEnumOption(0x11)
    DW_TAG_compile_unit,

    @BoundEnumOption(0x12)
    DW_TAG_string_type,

    @BoundEnumOption(0x13)
    DW_TAG_structure_type,

    @BoundEnumOption(0x15)
    DW_TAG_subroutine_type,

    @BoundEnumOption(0x16)
    DW_TAG_typedef,

    @BoundEnumOption(0x17)
    DW_TAG_union_type,

    @BoundEnumOption(0x18)
    DW_TAG_unspecified_parameters,

    @BoundEnumOption(0x19)
    DW_TAG_variant,

    @BoundEnumOption(0x1a)
    DW_TAG_common_block,

    @BoundEnumOption(0x1b)
    DW_TAG_common_inclusion,

    @BoundEnumOption(0x1c)
    DW_TAG_inheritance,

    @BoundEnumOption(0x1d)
    DW_TAG_inlined_subroutine,

    @BoundEnumOption(0x1e)
    DW_TAG_module,

    @BoundEnumOption(0x1f)
    DW_TAG_ptr_to_member_type,

    @BoundEnumOption(0x20)
    DW_TAG_set_type,

    @BoundEnumOption(0x21)
    DW_TAG_subrange_type,

    @BoundEnumOption(0x22)
    DW_TAG_with_stmt,

    @BoundEnumOption(0x23)
    DW_TAG_access_declaration,

    @BoundEnumOption(0x24)
    DW_TAG_base_type,

    @BoundEnumOption(0x25)
    DW_TAG_catch_block,

    @BoundEnumOption(0x26)
    DW_TAG_const_type,

    @BoundEnumOption(0x27)
    DW_TAG_constant,

    @BoundEnumOption(0x28)
    DW_TAG_enumerator,

    @BoundEnumOption(0x29)
    DW_TAG_file_type,

    @BoundEnumOption(0x2a)
    DW_TAG_friend,

    @BoundEnumOption(0x2b)
    DW_TAG_namelist,

    @BoundEnumOption(0x2c)
    DW_TAG_namelist_item,

    @BoundEnumOption(0x2d)
    DW_TAG_packed_type,

    @BoundEnumOption(0x2e)
    DW_TAG_subprogram,

    @BoundEnumOption(0x2f)
    DW_TAG_template_type_parameter,

    @BoundEnumOption(0x30)
    DW_TAG_template_value_parameter,

    @BoundEnumOption(0x31)
    DW_TAG_thrown_type,

    @BoundEnumOption(0x32)
    DW_TAG_try_block,

    @BoundEnumOption(0x33)
    DW_TAG_variant_part,

    @BoundEnumOption(0x34)
    DW_TAG_variable,

    @BoundEnumOption(0x35)
    DW_TAG_volatile_type,

    @BoundEnumOption(0x36)
    DW_TAG_dwarf_procedure,

    @BoundEnumOption(0x37)
    DW_TAG_restrict_type,

    @BoundEnumOption(0x38)
    DW_TAG_interface_type,

    @BoundEnumOption(0x39)
    DW_TAG_namespace,

    @BoundEnumOption(0x3a)
    DW_TAG_imported_module,

    @BoundEnumOption(0x3b)
    DW_TAG_unspecified_type,

    @BoundEnumOption(0x3c)
    DW_TAG_partial_unit,

    @BoundEnumOption(0x3d)
    DW_TAG_imported_unit,

    @BoundEnumOption(0x3f)
    DW_TAG_condition,

    @BoundEnumOption(0x40)
    DW_TAG_shared_type,

    @BoundEnumOption(0x41)
    DW_TAG_type_unit,

    @BoundEnumOption(0x42)
    DW_TAG_rvalue_reference_type,

    @BoundEnumOption(0x43)
    DW_TAG_template_alias,

    @BoundEnumOption(0x4080)
    DW_TAG_lo_user,

    @BoundEnumOption(0xffff)
    DW_TAG_hi_user,

    UNKNOWN;
}
