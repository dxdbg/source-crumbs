/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.constants;

import static net.sourcecrumbs.refimpl.dwarf.constants.AttributeClass.*;

import org.codehaus.preon.annotation.BoundEnumOption;

/**
 * Enumeration for form of an attribute in an AttributeSpecification
 *
 * @author mcnulty
 */
public enum AttributeForm {

    @BoundEnumOption(0x01)
    DW_FORM_addr(address),

    @BoundEnumOption(0x03)
    DW_FORM_block2(block),

    @BoundEnumOption(0x04)
    DW_FORM_block4(block),

    @BoundEnumOption(0x05)
    DW_FORM_data2(constant),

    @BoundEnumOption(0x06)
    DW_FORM_data4(constant),

    @BoundEnumOption(0x07)
    DW_FORM_data8(constant),

    @BoundEnumOption(0x08)
    DW_FORM_string(string),

    @BoundEnumOption(0x09)
    DW_FORM_block(block),

    @BoundEnumOption(0x0a)
    DW_FORM_block1(block),

    @BoundEnumOption(0x0b)
    DW_FORM_data1(constant),

    @BoundEnumOption(0x0c)
    DW_FORM_flag(flag),

    @BoundEnumOption(0x0d)
    DW_FORM_sdata(constant),

    @BoundEnumOption(0x0e)
    DW_FORM_strp(string),

    @BoundEnumOption(0x0f)
    DW_FORM_udata(constant),

    @BoundEnumOption(0x10)
    DW_FORM_ref_addr(reference),

    @BoundEnumOption(0x11)
    DW_FORM_ref1(reference),

    @BoundEnumOption(0x12)
    DW_FORM_ref2(reference),

    @BoundEnumOption(0x13)
    DW_FORM_ref4(reference),

    @BoundEnumOption(0x14)
    DW_FORM_ref8(address),

    @BoundEnumOption(0x15)
    DW_FORM_ref_udata(reference),

    @BoundEnumOption(0x16)
    DW_FORM_indirect,

    @BoundEnumOption(0x17)
    DW_FORM_sec_offset(lineptr, loclistptr, macptr, rangelistptr),

    @BoundEnumOption(0x18)
    DW_FORM_exprloc(exprloc),

    @BoundEnumOption(0x19)
    DW_FORM_flag_present(flag),

    @BoundEnumOption(0x20)
    DW_FORM_ref_sig8(reference),

    UNKNOWN;

    private AttributeClass[] classes;

    private AttributeForm() {
        this(new AttributeClass[0]);
    }

    private AttributeForm(AttributeClass...classes) {
        this.classes = classes;
    }

    public AttributeClass[] getClasses() {
        return classes;
    }
}
