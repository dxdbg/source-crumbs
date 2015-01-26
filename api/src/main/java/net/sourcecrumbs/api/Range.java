/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api;

/**
 * A sequence of consecutive numbers
 *
 * @author mcnulty
 */
public class Range<T extends Number> {

    private final T start;

    private final T end;

    public Range(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    public boolean isSingleValue() {
        return start.equals(end);
    }

    public int getIntLength() {
        return end.intValue() - start.intValue();
    }
}
