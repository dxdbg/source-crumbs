/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf;

import org.junit.Test;

import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

import static junit.framework.Assert.assertEquals;

/**
 * Unit test for LEB128 decoding and encoding
 *
 * @author mcnulty
 */
public class LEB128Test {

    @Test
    public void unsignedDecode() {

        assertEquals(2, new LEB128(new byte[]{ 2 }, false).getValue());
        assertEquals(127, new LEB128(new byte[]{ 127 }, false).getValue());
        assertEquals(128, new LEB128(new byte[]{ (byte)0x80, 1 }, false).getValue());
        assertEquals(129, new LEB128(new byte[]{ (byte)0x81, 1 }, false).getValue());
        assertEquals(130, new LEB128(new byte[]{ (byte)0x82, 1 }, false).getValue());
        assertEquals(12857, new LEB128(new byte[]{ (byte)(0x80 + 57), 100 }, false).getValue());
    }

    @Test
    public void signedDecode() {

        assertEquals(2, new LEB128(new byte[]{ 2 }, true).getValue());
        assertEquals(-2, new LEB128(new byte[]{ 0x7e }, true).getValue());
        assertEquals(127, new LEB128(new byte[]{ (byte)(127+0x80), 0 }, true).getValue());
        assertEquals(-127, new LEB128(new byte[]{ (byte)(1+0x80), 0x7f }, true).getValue());
        assertEquals(128, new LEB128(new byte[]{ (byte)(0x80), 1 }, true).getValue());
        assertEquals(-128, new LEB128(new byte[]{ (byte)(0x80), 0x7f }, true).getValue());
        assertEquals(129, new LEB128(new byte[]{ (byte)(1+0x80), 1 }, true).getValue());
        assertEquals(-129, new LEB128(new byte[]{ (byte)(0x7f+0x80), 0x7e }, true).getValue());
    }
}
