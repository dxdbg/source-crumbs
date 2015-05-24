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

import static net.sourcecrumbs.refimpl.dwarf.constants.AttributeClass.*;

/**
 * Enumeration for attribute names in an AttributeSpecification
 *
 * @author mcnulty
 */
public enum AttributeName {

    @BoundEnumOption(0x0)
    DW_AT_null(novalue),

    @BoundEnumOption(0x01)
    DW_AT_sibling(reference),

    @BoundEnumOption(0x02)
    DW_AT_location(exprloc, loclistptr),

    @BoundEnumOption(0x03)
    DW_AT_name(string),

    @BoundEnumOption(0x09)
    DW_AT_ordering(constant),

    @BoundEnumOption(0x0b)
    DW_AT_byte_size(constant, exprloc, reference),

    @BoundEnumOption(0x0c)
    DW_AT_bit_offset(constant, exprloc, reference),

    @BoundEnumOption(0x0d)
    DW_AT_bit_size(constant, exprloc, reference),

    @BoundEnumOption(0x10)
    DW_AT_stmt_list(lineptr),

    @BoundEnumOption(0x11)
    DW_AT_low_pc(address),

    @BoundEnumOption(0x12)
    DW_AT_high_pc(address, constant),

    @BoundEnumOption(0x13)
    DW_AT_language(constant),

    @BoundEnumOption(0x15)
    DW_AT_discr(reference),

    @BoundEnumOption(0x16)
    DW_AT_discr_value(constant),

    @BoundEnumOption(0x17)
    DW_AT_visibility(constant),

    @BoundEnumOption(0x18)
    DW_AT_import(reference),

    @BoundEnumOption(0x19)
    DW_AT_string_length(exprloc, loclistptr),

    @BoundEnumOption(0x1a)
    DW_AT_common_reference(reference),

    @BoundEnumOption(0x1b)
    DW_AT_comp_dir(string),

    @BoundEnumOption(0x1c)
    DW_AT_const_value(block, constant, string),

    @BoundEnumOption(0x1d)
    DW_AT_containing_type(reference),

    @BoundEnumOption(0x1e)
    DW_AT_default_value(reference),

    @BoundEnumOption(0x20)
    DW_AT_inline(constant),

    @BoundEnumOption(0x21)
    DW_AT_is_optional(flag),

    @BoundEnumOption(0x22)
    DW_AT_lower_bound(constant, exprloc, reference),

    @BoundEnumOption(0x25)
    DW_AT_producer(string),

    @BoundEnumOption(0x27)
    DW_AT_prototyped(flag),

    @BoundEnumOption(0x2a)
    DW_AT_return_addr(exprloc, loclistptr),

    @BoundEnumOption(0x2c)
    DW_AT_start_scope(constant, rangelistptr),

    @BoundEnumOption(0x2e)
    DW_AT_bit_stride(constant, exprloc, reference),

    @BoundEnumOption(0x2f)
    DW_AT_upper_bound(constant, exprloc, reference),

    @BoundEnumOption(0x31)
    DW_AT_abstract_origin(reference),

    @BoundEnumOption(0x32)
    DW_AT_accessibility(constant),

    @BoundEnumOption(0x33)
    DW_AT_address_class(constant),

    @BoundEnumOption(0x34)
    DW_AT_artificial(flag),

    @BoundEnumOption(0x35)
    DW_AT_base_types(reference),

    @BoundEnumOption(0x36)
    DW_AT_calling_convention(constant),

    @BoundEnumOption(0x37)
    DW_AT_count(constant, exprloc, reference),

    @BoundEnumOption(0x38)
    DW_AT_data_member_location(constant, exprloc, loclistptr),

    @BoundEnumOption(0x39)
    DW_AT_decl_column(constant),

    @BoundEnumOption(0x3a)
    DW_AT_decl_file(constant),

    @BoundEnumOption(0x3b)
    DW_AT_decl_line(constant),

    @BoundEnumOption(0x3c)
    DW_AT_declaration(flag),

    @BoundEnumOption(0x3d)
    DW_AT_discr_list(block),

    @BoundEnumOption(0x3e)
    DW_AT_encoding(constant),

    @BoundEnumOption(0x3f)
    DW_AT_external(flag),

    @BoundEnumOption(0x40)
    DW_AT_frame_base(exprloc, loclistptr),

    @BoundEnumOption(0x41)
    DW_AT_friend(reference),

    @BoundEnumOption(0x42)
    DW_AT_identifier_case(constant),

    @BoundEnumOption(0x43)
    DW_AT_macro_info(macptr),

    @BoundEnumOption(0x44)
    DW_AT_namelist_item(reference),

    @BoundEnumOption(0x45)
    DW_AT_priority(reference),

    @BoundEnumOption(0x46)
    DW_AT_segment(exprloc, loclistptr),

    @BoundEnumOption(0x47)
    DW_AT_specification(reference),

    @BoundEnumOption(0x48)
    DW_AT_static_link(exprloc, loclistptr),

    @BoundEnumOption(0x49)
    DW_AT_type(reference),

    @BoundEnumOption(0x4a)
    DW_AT_use_location(exprloc, loclistptr),

    @BoundEnumOption(0x4b)
    DW_AT_variable_parameter(flag),

    @BoundEnumOption(0x4c)
    DW_AT_virtuality(constant),

    @BoundEnumOption(0x4d)
    DW_AT_vtable_elem_location(exprloc, loclistptr),

    @BoundEnumOption(0x4e)
    DW_AT_allocated(constant, exprloc, reference),

    @BoundEnumOption(0x4f)
    DW_AT_associated(constant, exprloc, reference),

    @BoundEnumOption(0x50)
    DW_AT_data_location(exprloc),

    @BoundEnumOption(0x51)
    DW_AT_byte_stride(constant, exprloc, reference),

    @BoundEnumOption(0x52)
    DW_AT_entry_pc(address),

    @BoundEnumOption(0x53)
    DW_AT_use_UTF8(flag),

    @BoundEnumOption(0x54)
    DW_AT_extension(reference),

    @BoundEnumOption(0x55)
    DW_AT_ranges(rangelistptr),

    @BoundEnumOption(0x56)
    DW_AT_trampoline(address, flag, reference, string),

    @BoundEnumOption(0x57)
    DW_AT_call_column(constant),

    @BoundEnumOption(0x58)
    DW_AT_call_file(constant),

    @BoundEnumOption(0x59)
    DW_AT_call_line(constant),

    @BoundEnumOption(0x5a)
    DW_AT_description(string),

    @BoundEnumOption(0x5b)
    DW_AT_binary_scale(constant),

    @BoundEnumOption(0x5c)
    DW_AT_decimal_scale(constant),

    @BoundEnumOption(0x5d)
    DW_AT_small(reference),

    @BoundEnumOption(0x5e)
    DW_AT_decimal_sign(constant),

    @BoundEnumOption(0x5f)
    DW_AT_digit_count(constant),

    @BoundEnumOption(0x60)
    DW_AT_picture_string(string),

    @BoundEnumOption(0x61)
    DW_AT_mutable( flag),

    @BoundEnumOption(0x62)
    DW_AT_threads_scaled(flag),

    @BoundEnumOption(0x63)
    DW_AT_explicit(flag),

    @BoundEnumOption(0x64)
    DW_AT_object_pointer(reference),

    @BoundEnumOption(0x65)
    DW_AT_endianity(constant),

    @BoundEnumOption(0x66)
    DW_AT_elemental(flag),

    @BoundEnumOption(0x67)
    DW_AT_pure(flag),

    @BoundEnumOption(0x68)
    DW_AT_recursive(flag),

    @BoundEnumOption(0x69)
    DW_AT_signature(reference),

    @BoundEnumOption(0x6a)
    DW_AT_main_subprogram(flag),

    @BoundEnumOption(0x6b)
    DW_AT_data_bit_offset(constant),

    @BoundEnumOption(0x6c)
    DW_AT_const_expr(flag),

    @BoundEnumOption(0x6d)
    DW_AT_enum_class(flag),

    @BoundEnumOption(0x6e)
    DW_AT_linkage_name(string),

    @BoundEnumOption(0x3fe1)
    DW_AT_APPLE_optimized,

    @BoundEnumOption(0x3fe2)
    DW_AT_APPLE_flags,

    @BoundEnumOption(0x3fe3)
    DW_AT_APPLE_isa,

    @BoundEnumOption(0x3fe4)
    DW_AT_APPLE_block,

    @BoundEnumOption(0x3fe5)
    DW_AT_APPLE_major_runtime_vers,

    @BoundEnumOption(0x3fe6)
    DW_AT_APPLE_runtime_class,

    @BoundEnumOption(0x3fe7)
    DW_AT_APPLE_omit_frame_ptr,

    @BoundEnumOption(0x3fe8)
    DW_AT_APPLE_property_name,

    @BoundEnumOption(0x3fe9)
    DW_AT_APPLE_property_getter,

    @BoundEnumOption(0x3fea)
    DW_AT_APPLE_property_setter,

    @BoundEnumOption(0x3feb)
    DW_AT_APPLE_property_attribute,

    @BoundEnumOption(0x3fec)
    DW_AT_APPLE_objc_complete_type,

    @BoundEnumOption(0x3fed)
    DW_AT_APPLE_property,

    @BoundEnumOption(0x2000)
    DW_AT_lo_user,

    @BoundEnumOption(0x3fff)
    DW_AT_hi_user,

    UNKNOWN;

    private AttributeClass[] classes;

    private AttributeName() {
        this(new AttributeClass[0]);
    }

    private AttributeName(AttributeClass...classes) {
        this.classes = classes;
    }

    public AttributeClass[] getClasses() {
        return classes;
    }
}
