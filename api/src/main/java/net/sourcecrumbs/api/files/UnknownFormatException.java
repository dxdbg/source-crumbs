/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.files;

/**
 * Thrown when a binary file is opened and its format is unknown or unsupported.
 *
 * @author mcnulty
 */
public class UnknownFormatException extends Exception
{
    public UnknownFormatException(String message)
    {
        super(message);
    }

    public UnknownFormatException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UnknownFormatException(Throwable cause)
    {
        super(cause);
    }
}
