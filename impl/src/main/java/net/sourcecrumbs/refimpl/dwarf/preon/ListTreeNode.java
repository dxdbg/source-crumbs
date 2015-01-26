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
 * Interface for a tree node represented as an element of a list
 *
 * @author mcnulty
 */
public interface ListTreeNode extends ListTerminator {

    ListTreeNode getParent();

    void setParent(ListTreeNode parent);

    boolean hasChildren();
}
