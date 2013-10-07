package net.sourcecrumbs.api;

/**
 * Represents a native core file
 *
 * @author mcnulty
 */
public abstract class CoreFile implements Binary, SymbolContainer, TranslationUnitContainer
{
    @Override
    public BinaryType getBinaryType() {
        return BinaryType.CoreFile;
    }
}
