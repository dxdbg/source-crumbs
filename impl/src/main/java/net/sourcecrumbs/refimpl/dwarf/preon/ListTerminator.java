/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.preon;

/**
 * Interface that provides a way for the decoding process to determine if this element terminates a list
 *
 * @author mcnulty
 */
public interface ListTerminator {

    /**
     * @return true, if this instance terminates a list
     */
    boolean terminatesList();
}
