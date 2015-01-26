/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.preon;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDecorator;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.DefaultCodecFactory;
import org.codehaus.preon.ResolverContext;
import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.binding.BindingDecorator;

import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

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
