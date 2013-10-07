package net.sourcecrumbs.api;

/**
 * Represents a native library
 *
 * @author mcnulty
 */
public abstract class Library implements SymbolContainer, TranslationUnitContainer, Binary
{
    @Override
    public BinaryType getBinaryType() {
        return BinaryType.Library;
    }
}
