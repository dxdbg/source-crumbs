package net.sourcecrumbs.api;

import java.nio.file.Path;

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
}
