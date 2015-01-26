/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.machinecode;

import net.sourcecrumbs.api.transunit.TranslationUnit;

/**
 * Represents a source code line in a source file
 *
 * @author mcnulty
 */
public class SourceLine {

    private TranslationUnit translationUnit;

    private int line;

    public TranslationUnit getTranslationUnit() {
        return translationUnit;
    }

    public void setTranslationUnit(TranslationUnit translationUnit) {
        this.translationUnit = translationUnit;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
