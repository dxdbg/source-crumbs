package net.sourcecrumbs.api.transunit;

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
    Iterable<TranslationUnit> getTranslationUnits();
}
