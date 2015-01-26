/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.preon;

import java.lang.reflect.AnnotatedElement;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDecorator;
import org.codehaus.preon.ResolverContext;
import org.codehaus.preon.el.Expressions;

/**
 * Similar to the {@link org.codehaus.preon.codec.SlicingCodecDecorator}, this codec decorator uses an {@link AbsoluteOffset}
 * annotation to "jump" a BitBuffer ahead to a predetermined location and continue decoding the object at that
 * point.
 *
 * @author mcnulty
 */
public class AbsoluteOffsetCodecDecorator implements CodecDecorator {

    @Override
    public <T> Codec<T> decorate(Codec<T> decorated, AnnotatedElement metadata, Class<T> type, ResolverContext context) {
        AbsoluteOffset absoluteOffset = getOffsetAnnotation(type, metadata);
        if (absoluteOffset != null) {
            return new AbsoluteOffsetCodec<T>(decorated, Expressions.create(context, absoluteOffset.value()), absoluteOffset.adjustBitStream());
        }
        return decorated;
    }

    private <T> AbsoluteOffset getOffsetAnnotation(Class<T> type, AnnotatedElement metaData) {

        if (type.isAnnotationPresent(AbsoluteOffset.class)) {
            return type.getAnnotation(AbsoluteOffset.class);
        }

        if (metaData != null && metaData.isAnnotationPresent(AbsoluteOffset.class)) {
            return metaData.getAnnotation(AbsoluteOffset.class);
        }

        return null;
    }
}
