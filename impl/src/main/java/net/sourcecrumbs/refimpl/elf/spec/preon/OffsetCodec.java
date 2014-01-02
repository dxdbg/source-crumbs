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

package net.sourcecrumbs.refimpl.elf.spec.preon;

import java.io.IOException;

import org.codehaus.preon.Builder;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.Resolver;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.channel.BitChannel;

import net.sourcecrumbs.refimpl.elf.spec.Offset;

/**
 * Codec for Elf*_Off fields.
 *
 * @author mcnulty
 */
public class OffsetCodec extends ClassLengthFieldCodec<Offset> {

    /**
     * Constructor.
     *
     * @param length the length of a class-specific field in bits
     * @param byteOrder the byte order of the field
     */
    protected OffsetCodec(int length, ByteOrder byteOrder) {
        super(length, byteOrder);
    }

    @Override
    public Offset decode(BitBuffer buffer, Resolver resolver, Builder builder) throws DecodingException {
        return new Offset(buffer.readAsLong(length, byteOrder));
    }

    @Override
    public void encode(Offset value, BitChannel channel, Resolver resolver) throws IOException {
        channel.write(length, value.getValue(), byteOrder);
    }

    @Override
    public Class<?>[] getTypes() {
        return new Class<?>[] { Offset.class };
    }
}
