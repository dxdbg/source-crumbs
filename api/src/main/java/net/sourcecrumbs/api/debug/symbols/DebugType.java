/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.debug.symbols;

/**
 * A DebugType describes the form of data associated with a debugging symbol
 *
 * @author mcnulty
 */
public interface DebugType
{
    /**
     * @return the name of the type
     */
    String getName();

    /**
     * @return true if the type is immutable
     */
    boolean isImmutable();

    /**
     * @return ture if the type is a pointer
     */
    boolean isPointer();

    boolean isMemberStructure();

    DebugType[] getMemberTypes();

    /**
     * This is relevant for typedefs.
     *
     * @return the base type, if applicable.
     */
    DebugType getBaseType();
}
