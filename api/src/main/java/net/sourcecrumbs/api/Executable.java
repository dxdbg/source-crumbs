package net.sourcecrumbs.api;

import java.util.List;

/**
 * Represents a native executable
 *
 * @author mcnulty
 */
public abstract class Executable implements SymbolContainer, TranslationUnitContainer, Binary
{
    public abstract List<Library> getLibraries();

    @Override
    public BinaryType getBinaryType() {
        return BinaryType.Executable;
    }
}
