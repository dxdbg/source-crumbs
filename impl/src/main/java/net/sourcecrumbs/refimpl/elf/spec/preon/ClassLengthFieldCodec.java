/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
