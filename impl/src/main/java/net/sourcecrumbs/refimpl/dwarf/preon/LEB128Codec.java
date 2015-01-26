/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.preon;

import java.io.IOException;

import org.codehaus.preon.Builder;
import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDescriptor;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.Resolver;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.channel.BitChannel;
import org.codehaus.preon.el.Expression;

import net.sourcecrumbs.refimpl.dwarf.types.LEB128;
import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * Codec to decode a LEB128 value
 *
 * @author mcnulty
 */
public class LEB128Codec implements Codec<LEB128> {

    private final boolean signed;

    /**
     * Constructor.
     *
     * @param signed true if the signed representation should be encoded/decoded
     */
    public LEB128Codec(boolean signed) {
        this.signed = signed;
    }

    @Override
    public LEB128 decode(BitBuffer buffer, Resolver resolver, Builder builder) throws DecodingException {
        return new LEB128(buffer, signed);
    }

    @Override
    public void encode(LEB128 value, BitChannel channel, Resolver resolver) throws IOException {
        // TODO
    }

    @Override
    public Expression<Integer, Resolver> getSize() {
        return null;
    }

    @Override
    public CodecDescriptor getCodecDescriptor() {
                return new CodecDescriptor() {

            @Override
            public <C extends ParaContents<?>> Documenter<C> summary() {
                return new Documenter<C>() {

                    @Override
                    public void document(C c) {
                        c.text(String.format("A %s representation.", this.getClass().getSimpleName()));
                    }
                };
            }

            @Override
            public <C extends ParaContents<?>> Documenter<C> reference(final Adjective adjective, boolean startWithCapital) {
                return new Documenter<C>() {

                    @Override
                    public void document(C c) {
                        c.text(adjective == Adjective.A ? "a" : "the ");
                        c.text(String.format("%s value", this.getClass().getSimpleName()));
                    }
                };
            }

            @Override
            public <C extends SimpleContents<?>> Documenter<C> details(String bufferReference) {
                return new Documenter<C>() {

                    @Override
                    public void document(C c) {
                    }
                };
            }

            @Override
            public boolean requiresDedicatedSection() {
                return false;
            }

            @Override
            public String getTitle() {
                return null;
            }
        };
    }

    @Override
    public Class<?>[] getTypes() {
        return new Class<?>[]{ LEB128.class };
    }

    @Override
    public Class<?> getType() {
        return LEB128.class;
    }
}
