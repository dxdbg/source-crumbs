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
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.Resolver;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.channel.BitChannel;

import net.sourcecrumbs.refimpl.elf.spec.WordField;

/**
 * Codec for WordField type
 *
 * @author mcnulty
 */
public class WordFieldCodec extends ClassLengthFieldCodec<WordField> {

    /**
     * Constructor.
     *
     * @param length the length of a class-specific field in bits
     * @param byteOrder the byte order of the field
     */
    protected WordFieldCodec(int length, ByteOrder byteOrder) {
        super(length, byteOrder);
    }

    @Override
    public WordField decode(BitBuffer buffer, Resolver resolver, Builder builder) throws DecodingException {
        return new WordField(buffer.readAsLong(length, byteOrder));
    }

    @Override
    public void encode(WordField value, BitChannel channel, Resolver resolver) throws IOException {
        channel.write(length, value.getValue(), byteOrder);
    }

    @Override
    public Class<?>[] getTypes() {
        return new Class<?>[] { WordFieldCodec.class };
    }
}
