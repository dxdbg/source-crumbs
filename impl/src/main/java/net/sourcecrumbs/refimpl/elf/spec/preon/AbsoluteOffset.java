/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.preon;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to indicate that a field begins at the absolute offset into the buffer. The value is a Limbo
 * expression.
 *
 * @author mcnulty
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AbsoluteOffset {

    /**
     * @return a Limbo expression for the absolute offset in bits
     */
    String value();

    /**
     * @return true if the bit stream passed to the codec should be adjusted and used to read the object
     */
    boolean adjustBitStream() default false;
}
