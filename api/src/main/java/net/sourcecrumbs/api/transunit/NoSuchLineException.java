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
 * Thrown when no information can be determined about a line
 *
 * @author mcnulty
 */
public class NoSuchLineException extends Exception {

    private static final long serialVersionUID = -8680763958548676813L;

    public NoSuchLineException() {
    }

    public NoSuchLineException(String message) {
        super(message);
    }

    public NoSuchLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchLineException(Throwable cause) {
        super(cause);
    }
}
