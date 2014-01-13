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
import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDescriptor;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.Resolver;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.channel.BitChannel;
import org.codehaus.preon.el.Expression;

/**
 * Codec for decoding an object at a specific offset in the BitBuffer. Similar to the {@link org.codehaus.preon.codec.SlicingCodec}.
 *
 * @author mcnulty
 */
public class AbsoluteOffsetCodec<T> implements Codec<T> {

    private final Expression<Object, Resolver> offsetExpr;
    private final Codec<T> decorated;
    private final boolean adjustBitStream;

    public AbsoluteOffsetCodec(Codec<T> decorated, Expression<Object, Resolver> offsetExpr, boolean adjustBitStream) {
        this.decorated = decorated;
        this.offsetExpr = offsetExpr;
        this.adjustBitStream = adjustBitStream;
    }

    @Override
    public T decode(BitBuffer buffer, Resolver resolver, Builder builder) throws DecodingException {
        Object offsetObj = offsetExpr.eval(resolver);

        long offset;
        if (offsetObj instanceof Number) {
            offset = ((Number)offsetObj).longValue();
        }else if (offsetObj instanceof String) {
            offset = Long.parseLong((String)offsetObj);
        }else{
            throw new DecodingException("Failed to determine offset from " + offsetExpr);
        }

        BitBuffer codecBuffer;
        if (adjustBitStream) {
            codecBuffer = buffer;
        }else{
            codecBuffer = buffer.duplicate();
        }
        codecBuffer.setBitPos(offset);

        return decorated.decode(codecBuffer, resolver, builder);
    }

    @Override
    public void encode(T value, BitChannel channel, Resolver resolver) throws IOException {
        throw new UnsupportedOperationException("Value encoding for absolute offset field currently unsupported.");
    }

    @Override
    public Expression<Integer, Resolver> getSize() {
        return decorated.getSize();
    }

    @Override
    public CodecDescriptor getCodecDescriptor() {
        return decorated.getCodecDescriptor();
    }

    @Override
    public Class<?>[] getTypes() {
        return decorated.getTypes();
    }

    @Override
    public Class<?> getType() {
        return decorated.getType();
    }
}
