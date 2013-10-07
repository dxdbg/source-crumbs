package net.sourcecrumbs.api;

import java.util.List;

/**
 * Represents a container for TranslationUnits
 *
 * @author mcnulty
 */
public interface TranslationUnitContainer
{
    /**
     * @return the translation units in this container
     */
    List<TranslationUnit> getTranslationUnits();
}
