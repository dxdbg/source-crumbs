/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries.lnp.sm;

/**
 * Represents a row in a line number matrix
 *
 * @author mcnulty
 */
public class LineNumberRow extends LineNumberRegisters {

    private LineNumberRow previous = null;

    private LineNumberRow next = null;

    public LineNumberRow getPrevious() {
        return previous;
    }

    public void setPrevious(LineNumberRow previous) {
        this.previous = previous;
    }

    public LineNumberRow getNext() {
        return next;
    }

    public void setNext(LineNumberRow next) {
        this.next = next;
    }
}
