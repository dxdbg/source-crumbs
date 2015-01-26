/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sections;

import static net.sourcecrumbs.refimpl.elf.spec.constants.DataTypeSizes.*;

import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;

/**
 * An entry in a Note section
 *
 * @author mcnulty
 */
public class NoteEntry {

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
