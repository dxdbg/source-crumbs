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
 * @author mcnulty
 */
public enum TypeEncoding
{
    @BoundEnumOption(0x01)
    DW_ATE_address,
    @BoundEnumOption(0x02)
    DW_ATE_boolean,
    @BoundEnumOption(0x03)
    DW_ATE_complex_float,
    @BoundEnumOption(0x04)
    DW_ATE_float,
    @BoundEnumOption(0x05)
    DW_ATE_signed,
    @BoundEnumOption(0x06)
    DW_ATE_signed_char,
    @BoundEnumOption(0x07)
    DW_ATE_unsigned,
    @BoundEnumOption(0x08)
    DW_ATE_unsigned_char,
    @BoundEnumOption(0x09)
    DW_ATE_imaginary_float,
    @BoundEnumOption(0x0a)
    DW_ATE_packed_decimal,
    @BoundEnumOption(0x0b)
    DW_ATE_numeric_string,
    @BoundEnumOption(0x0c)
    DW_ATE_edited,
    @BoundEnumOption(0x0d)
    DW_ATE_signed_fixed,
    @BoundEnumOption(0x0e)
    DW_ATE_unsigned_fixed,
    @BoundEnumOption(0x0f)
    DW_ATE_decimal_float,
    @BoundEnumOption(0x10)
    DW_ATE_UTF,
    @BoundEnumOption(0x80)
    DW_ATE_lo_user,
    @BoundEnumOption(0xff)
    DW_ATE_hi_user;
}
