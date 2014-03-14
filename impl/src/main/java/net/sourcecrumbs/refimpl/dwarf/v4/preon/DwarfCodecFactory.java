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

package net.sourcecrumbs.refimpl.dwarf.v4.preon;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDecorator;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.DefaultCodecFactory;
import org.codehaus.preon.ResolverContext;
import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.binding.BindingDecorator;
import org.codehaus.preon.codec.CompoundCodecFactory;

import net.sourcecrumbs.refimpl.dwarf.v4.types.LEB128;

/**
 * Codec factory used to create custom codecs used to parse DWARF data structures
 *
 * @author mcnulty
 */
public class DwarfCodecFactory implements CodecFactory {

    private final DefaultCodecFactory actualFactory;
    private final CodecDecorator[] codecDecorators;

    public DwarfCodecFactory(CodecDecorator[] codecDecorators) {
        this.actualFactory = new DefaultCodecFactory();
        this.codecDecorators = codecDecorators;
    }

    @Override
    public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type, ResolverContext context) {
        if (metadata == null || metadata.isAnnotationPresent(Bound.class)) {
            if (LEB128.class.equals(type)) {
                return (Codec<T>) createLEB128Codec(metadata);
            }else if (List.class.equals(type)) {
                return (Codec<T>) createTerminatedListCodec(metadata);
            }
        }

        return null;
    }

    private LEB128Codec createLEB128Codec(AnnotatedElement metadata) {
        if (metadata != null && metadata.isAnnotationPresent(LEBSigned.class)) {
            return new LEB128Codec(metadata.getAnnotation(LEBSigned.class).value());
        }

        // return null to force explicit specification of whether the LEB value is unsigned or signed
        return null;
    }

    private ElementTerminatedListCodec<?> createTerminatedListCodec(AnnotatedElement metadata) {
        if (metadata != null && metadata.isAnnotationPresent(ElementTerminatedList.class)) {
            ElementTerminatedList annotation = metadata.getAnnotation(ElementTerminatedList.class);
            return new ElementTerminatedListCodec<>(actualFactory.create(metadata, annotation.elementType(),
                    new CodecFactory[] {this}, codecDecorators, new BindingDecorator[0]));
        }

        return null;
    }
}
