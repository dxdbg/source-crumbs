/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.segments;

import org.codehaus.preon.annotation.BoundList;

/**
 * A generic segment that just returns the bytes from the segment
 *
 * @author mcnulty
 */
public class GenericSegment implements SegmentContent {

    @BoundList(size = "outer.programHeader.fileSize.value", type=byte.class)
    protected byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
