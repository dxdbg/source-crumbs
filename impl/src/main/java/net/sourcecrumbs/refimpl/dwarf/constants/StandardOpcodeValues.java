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
 * @author mcnulty
 */
public class StandardOpcodeValues {

    public static byte DW_LNS_extended = 0x00;

    public static byte DW_LNS_copy = 0x01;

    public static byte DW_LNS_advance_pc = 0x02;

    public static byte DW_LNS_advance_line = 0x03;

    public static byte DW_LNS_set_file = 0x04;

    public static byte DW_LNS_set_column = 0x05;

    public static byte DW_LNS_negate_stmt = 0x06;

    public static byte DW_LNS_set_basic_block = 0x07;

    public static byte DW_LNS_const_add_pc = 0x08;

    public static byte DW_LNS_fixed_advance_pc = 0x09;

    public static byte DW_LNS_set_prologue_end = 0x0a;

    public static byte DW_LNS_set_epilogue_begin = 0x0b;

    public static byte DW_LNS_set_isa = 0x0c;
}
