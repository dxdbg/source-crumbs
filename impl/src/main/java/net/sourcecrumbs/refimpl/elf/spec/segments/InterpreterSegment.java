/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.segments;

import java.nio.charset.StandardCharsets;

/**
 * Holds information about the Interpreter used for dynamic linking and loading
 *
 * @author mcnulty
 */
public class InterpreterSegment extends GenericSegment implements SegmentContent {

    public String getInterpreterPath() {
        int end = 0;
        for (; data[end] != 0x00; ++end);
        return new String(data, 0, end, StandardCharsets.US_ASCII);
    }
}
