/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.symbols;

/**
 * A Symbol is a label for a memory address.
 *
 * @author mcnulty
 */
public interface Symbol
{
    String getName();

    long getAddress();
}
