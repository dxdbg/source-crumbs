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

package net.sourcecrumbs.refimpl.dwarf.v4.types;

import org.codehaus.preon.buffer.BitBuffer;

/**
 * Represents a Little Endian Base 128 encoded value
 *
 * <p>
 *     Note: this class assumes that the value encoded will never be more than 64-bits in length.
 * </p>
 *
 * @author mcnulty
 */
public class LEB128 {

    private final long value;

    private final boolean signed;

    /**
     * Constructor.
     *
     * @param value the value
     * @param signed true, if the LEB128 value is signed
     */
    public LEB128(long value, boolean signed) {
        this.value = value;
        this.signed = signed;
    }

    /**
     * Constructor.
     *
     * @param stream the BitBuffer being used by preon to decode the data
     * @param signed true, if the LEB128 value is signed
     */
    public LEB128(BitBuffer stream, boolean signed) {

        // Based on algorithms in DWARFv4 spec
        long result = 0;
        int shift = 0;
        byte currentByte;
        while (true) {
            currentByte = stream.readAsByte(8);
            result |= ((0x7f & currentByte) << shift);
            shift += 7;
            if ((0x80 & currentByte) == 0) {
                break;
            }
        }
        if (signed && (shift < 64) && ((0x40 & currentByte) != 0)) {
            // sign extend
            result |= -(1 << shift);
        }

        this.value = result;
        this.signed = signed;
    }

    /**
     * Constructor.
     *
     * @param stream the stream of bytes containing the encoded value
     * @param signed true, if the LEB128 value is signed
     */
    public LEB128(byte[] stream, boolean signed) {

        // Based on algorithms in DWARFv4 spec
        long result = 0;
        int shift = 0;
        byte currentByte;

        int i = 0;
        while (true) {
            currentByte = stream[i];
            result |= ((0x7f & currentByte) << shift);
            shift += 7;
            if ((0x80 & currentByte) == 0) {
                break;
            }
            i++;
        }
        if (signed && (shift < 64) && ((0x40 & currentByte) != 0)) {
            // sign extend
            result |= -(1 << shift);
        }

        this.value = result;
        this.signed = signed;
    }

    public long getValue() {
        return value;
    }

    public boolean isSigned() {
        return signed;
    }
}
