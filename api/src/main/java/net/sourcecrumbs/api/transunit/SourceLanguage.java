/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.transunit;

/**
 * The language of a source file
 *
 * @author mcnulty
 */
public enum SourceLanguage
{
    /** C++ Source File */
    CXX,

    /** C Source */
    C,

    /** The language of the source is unknown */
    UNKNOWN;
}
