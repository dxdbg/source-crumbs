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

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;

/**
 * A codec to decode/encode a tree stored in prefix order as a list
 *
 * @author mcnulty
 */
public class ListTreeNodeCodec<T extends ListTreeNode> implements Codec<T> {

    private final Codec<T> elementCodec;

    public ListTreeNodeCodec(Codec<T> elementCodec) {
        this.elementCodec = elementCodec;
    }

    @Override
    public T decode(BitBuffer buffer, Resolver resolver, Builder builder) throws DecodingException {
        // To build the tree, track the previous node.
        // - If the previous node does not have children, the previous node is the sibling of the current node (i.e.,
        //   they share a parent
        // - If the previous node does have children, the previous node is the parent of the current node

        // The root of the tree, this will be returned
        T root = elementCodec.decode(buffer, resolver, builder);
        root.setParent(null);

        T previous = root;
        while (!previous.terminatesList()) {
            T current = elementCodec.decode(buffer, resolver, builder);

            // The list terminator is not actually an element so don't add it to the list
            if (!current.terminatesList()) {
                if (!previous.hasChildren()) {
                    current.setParent(previous.getParent());
                }else{
                    current.setParent(previous);
                }
            }
            previous = current;
        }

        return root;
    }

    @Override
    public void encode(T value, BitChannel channel, Resolver resolver) throws IOException {
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
        return elementCodec.getTypes();
    }

    @Override
    public Class<?> getType() {
        return ListTreeNode.class;
    }
}
