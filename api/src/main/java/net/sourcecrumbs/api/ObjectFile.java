package net.sourcecrumbs.api;

/**
 * Represents a native object file
 *
 * @author mcnulty
 */
public abstract class ObjectFile implements SymbolContainer, TranslationUnitContainer, Binary
{
    @Override
    public BinaryType getBinaryType() {
        return BinaryType.ObjectFile;
    }
}
