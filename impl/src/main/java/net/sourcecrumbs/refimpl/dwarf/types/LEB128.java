/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.types;

import java.nio.ByteBuffer;

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

    private final int length;

    /**
     * Constructor.
     *
     * @param value the value
     * @param signed true, if the LEB128 value is signed
     */
    public LEB128(long value, boolean signed) {
        this.value = value;
        this.signed = signed;
        this.length = -1;
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

        int i = 0;
        while (true) {
            currentByte = stream.readAsByte(8);
            result |= ((0x7f & currentByte) << shift);
            shift += 7;
            i++;
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
        this.length = i;
    }

    public LEB128(ByteBuffer buffer, boolean signed) {
        // Based on algorithms in DWARFv4 spec
        long result = 0;
        int shift = 0;
        byte currentByte;

        int i = 0;
        while (true) {
            currentByte = buffer.get();
            result |= ((0x7f & currentByte) << shift);
            shift += 7;
            i++;
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
        this.length = i;
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
            i++;
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
        this.length = i;
    }

    public long getValue() {
        return value;
    }

    public int getValueAsInt() {
        return (int)value;
    }

    public boolean isSigned() {
        return signed;
    }

    public int getLength() {
        return length;
    }
}
