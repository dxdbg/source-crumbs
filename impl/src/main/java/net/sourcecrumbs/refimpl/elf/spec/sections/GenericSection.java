/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sections;

import org.codehaus.preon.annotation.BoundList;

/**
 * A generic section that just returns the bytes from the section
 *
 * @author mcnulty
 */
public class GenericSection implements SectionContent {

    @BoundList(size = "outer.sectionHeader.size.value", type=Byte.class)
    protected byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
