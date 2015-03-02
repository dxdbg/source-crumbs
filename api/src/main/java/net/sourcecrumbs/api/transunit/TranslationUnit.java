/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.api.transunit;

import java.nio.file.Path;
import java.util.List;

import net.sourcecrumbs.api.Range;

/**
 * Represents a single translation unit -- usually a single source file input to a compiler
 *
 * @author mcnulty
 */
public interface TranslationUnit
{
    /**
     * @return the name of the translation unit
     */
    String getName();

    /**
     * @return the language of the translation unit
     */
    SourceLanguage getLanguage();

    /**
     * @return the directory in which the source file was compiled
     */
    Path getCompilationDirectory();

    /**
     * @return the complete path for the source file
     */
    Path getPath();

    /**
     * @return a list of scopes for this TranslationUnit
     */
    List<Range<Long>> getScopes();
}
