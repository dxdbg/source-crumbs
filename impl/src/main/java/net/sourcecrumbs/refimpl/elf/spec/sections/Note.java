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

package net.sourcecrumbs.refimpl.elf.spec.sections;

import java.util.List;

import static net.sourcecrumbs.refimpl.elf.spec.constants.DataTypeSizes.*;

import org.codehaus.preon.annotation.BoundBuffer;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;

/**
 * Note information in an ELF section
 *
 * @author mcnulty
 */
public class Note implements SectionContent {

    @BoundList(type=NoteEntry.class)
    private List<NoteEntry> entries;

    public List<NoteEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<NoteEntry> entries) {
        this.entries = entries;
    }

    /**
     * An entry in a Note section
     */
    public static class NoteEntry {

        @BoundNumber(size = ElfWord)
        private int nameSize;

        @BoundNumber(size = ElfWord)
        private int descriptorSize;

        @BoundNumber(size = ElfWord)
        private int type;

        @BoundList(size="nameSize")
        private byte[] name;

        @BoundList(size="nameSize - ((nameSize)/4)*4)")
        private byte[] namePadding;

        @BoundList(size="descriptorSize")
        private byte[] descriptor;

        @BoundList(size="descriptorSize - ((descriptorSize)/4)*4)")
        private byte[] descriptorPadding;

        public int getNameSize() {
            return nameSize;
        }

        public void setNameSize(int nameSize) {
            this.nameSize = nameSize;
        }

        public int getDescriptorSize() {
            return descriptorSize;
        }

        public void setDescriptorSize(int descriptorSize) {
            this.descriptorSize = descriptorSize;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public byte[] getName() {
            return name;
        }

        public void setName(byte[] name) {
            this.name = name;
        }

        public byte[] getDescriptor() {
            return descriptor;
        }

        public void setDescriptor(byte[] descriptor) {
            this.descriptor = descriptor;
        }
    }
}
