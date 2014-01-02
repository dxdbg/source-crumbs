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


import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDescriptor;
import org.codehaus.preon.Resolver;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.el.Expression;
import org.codehaus.preon.el.Expressions;

import net.sourcecrumbs.refimpl.elf.spec.ClassLengthField;
import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * Codec for variable length ELF fields that depend on the class of the containing ELF file
 *
 * @author mcnulty
 */
public abstract class ClassLengthFieldCodec<T extends ClassLengthField> implements Codec<T> {

    protected final int length;

    protected final ByteOrder byteOrder;

    /**
     * Constructor.
     *
     * @param length the length of a class-specific field in bits
     * @param byteOrder the byte order of the field
     */
    protected ClassLengthFieldCodec(int length, ByteOrder byteOrder) {
        this.length = length;
        this.byteOrder = byteOrder;
    }

    @Override
    public Expression<Integer, Resolver> getSize() {
        return Expressions.createInteger(length, Resolver.class);
    }

    @Override
    public CodecDescriptor getCodecDescriptor() {
        return new CodecDescriptor() {

            @Override
            public <C extends ParaContents<?>> Documenter<C> summary() {
                return new Documenter<C>() {

                    @Override
                    public void document(C c) {
                        c.text(String.format("A %d-bit %s representation.", this.getClass().getSimpleName(), length));
                    }
                };
            }

            @Override
            public <C extends ParaContents<?>> Documenter<C> reference(final Adjective adjective, boolean startWithCapital) {
                return new Documenter<C>() {

                    @Override
                    public void document(C c) {
                        c.text(adjective == Adjective.A ? "a" : "the ");
                        c.text(String.format("%d-bit %s value", this.getClass().getSimpleName(), length));
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
    public Class<?> getType() {
        return ClassLengthField.class;
    }
}
