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
import java.nio.ByteOrder;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.ResolverContext;
import org.codehaus.preon.annotation.Bound;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.elf.spec.Address;
import net.sourcecrumbs.refimpl.elf.spec.ElfIdent;
import net.sourcecrumbs.refimpl.elf.spec.Offset;
import net.sourcecrumbs.refimpl.elf.spec.WordField;

/**
 * Codec factory for creating ElfCodecs
 *
 * @author mcnulty
 */
public class ElfCodecFactory implements CodecFactory {

    private final int classLength;

    private final ByteOrder classByteOrder;

    private final org.codehaus.preon.buffer.ByteOrder preonOrder;

    public ElfCodecFactory(ElfIdent ident) throws UnknownFormatException {
        classLength = ident.getClassLength();
        classByteOrder = ident.getByteOrder();
        if (classByteOrder == ByteOrder.BIG_ENDIAN) {
            preonOrder = org.codehaus.preon.buffer.ByteOrder.BigEndian;
        }else{
            preonOrder = org.codehaus.preon.buffer.ByteOrder.LittleEndian;
        }
    }

    @Override
    public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type, ResolverContext context) {
        if (metadata == null || metadata.isAnnotationPresent(Bound.class)) {
            if (Address.class.equals(type)) {
                return (Codec<T>) new AddressCodec(classLength, preonOrder);
            }else if (Offset.class.equals(type)) {
                return (Codec<T>) new OffsetCodec(classLength, preonOrder);
            }else if (WordField.class.equals(type)) {
                return (Codec<T>) new WordFieldCodec(classLength, preonOrder);
            }
        }

        return null;
    }
}
