/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec.sections;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A string table contained in an ELF section. Provides methods to interpret the raw binary content.
 *
 * @author mcnulty
 */
public class StringTable extends GenericSection {

    public String getString(int index) {
        return getString(index, StandardCharsets.US_ASCII);
    }

    public String getString(int index, Charset charset) {
        if (index >= 0 && index <= data.length) {
            int end = index;
            for (; data[end] != 0x00 ; end++);
            return new String(data, index, end-index, charset);
        }

        return "";
    }
}
