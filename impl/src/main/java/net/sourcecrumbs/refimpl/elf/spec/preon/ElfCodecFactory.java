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

import java.lang.reflect.AnnotatedElement;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.ResolverContext;
import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.codec.ObjectCodecFactory;

import net.sourcecrumbs.refimpl.elf.spec.Address;
import net.sourcecrumbs.refimpl.elf.spec.Offset;
import net.sourcecrumbs.refimpl.elf.spec.WordField;
import net.sourcecrumbs.refimpl.elf.spec.sym.ElfSymbol;

/**
 * Codec factory for creating ElfCodecs
 *
 * @author mcnulty
 */
public class ElfCodecFactory implements CodecFactory {

    private final int classLength;

    private final ByteOrder classByteOrder;

    public ElfCodecFactory(int classLength, ByteOrder classByteOrder) {
        this.classLength = classLength;
        this.classByteOrder = classByteOrder;
    }

    @Override
    public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type, ResolverContext context) {
        if (metadata == null || metadata.isAnnotationPresent(Bound.class)) {
            if (Address.class.equals(type)) {
                return (Codec<T>) new AddressCodec(classLength, classByteOrder);
            }else if (Offset.class.equals(type)) {
                return (Codec<T>) new OffsetCodec(classLength, classByteOrder);
            }else if (WordField.class.equals(type)) {
                return (Codec<T>) new WordFieldCodec(classLength, classByteOrder);
            }
        }

        return null;
    }
}
