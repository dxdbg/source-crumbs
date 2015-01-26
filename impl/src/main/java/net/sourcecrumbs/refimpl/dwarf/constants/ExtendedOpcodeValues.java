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
public class ExtendedOpcodeValues {

    public static byte DW_LNE_end_sequence = 0x01;

    public static byte DW_LNE_set_address = 0x02;

    public static byte DW_LNE_define_file = 0x03;

    public static byte DW_LNE_set_discriminator = 0x04;

    public static short DW_LNE_lo_user = 0x80;

    public static short DW_LNE_hi_user = 0xff;
}
