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
 * Thrown when there is an error inspecting an execution context
 *
 * @author mcnulty
 */
public class ContextInspectionException extends Exception
{

    public ContextInspectionException()
    {
    }

    public ContextInspectionException(String message)
    {
        super(message);
    }

    public ContextInspectionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ContextInspectionException(Throwable cause)
    {
        super(cause);
    }
}
