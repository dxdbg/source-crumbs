/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.machinecode;

import net.sourcecrumbs.api.Range;
import net.sourcecrumbs.api.transunit.TranslationUnit;

/**
 * Bean describing a source code line range in a translation unit
 *
 * @author mcnulty
 */
public class SourceLineRange {

    private TranslationUnit translationUnit;

    private Range<Integer> lineRange;

    public TranslationUnit getTranslationUnit() {
        return translationUnit;
    }

    public void setTranslationUnit(TranslationUnit translationUnit) {
        this.translationUnit = translationUnit;
    }

    public Range<Integer> getLineRange() {
        return lineRange;
    }

    public void setLineRange(Range<Integer> lineRange) {
        this.lineRange = lineRange;
    }
}
