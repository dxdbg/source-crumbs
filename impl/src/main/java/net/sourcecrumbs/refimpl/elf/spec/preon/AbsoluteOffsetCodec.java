/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
