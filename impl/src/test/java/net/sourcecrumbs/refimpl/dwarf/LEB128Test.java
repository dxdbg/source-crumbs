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

package net.sourcecrumbs.refimpl.dwarf;

import org.junit.Test;

import net.sourcecrumbs.refimpl.dwarf.v4.types.LEB128;

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
