package net.sourcecrumbs.api.files;

import java.util.List;

import net.sourcecrumbs.api.machinecode.MachineCodeSource;
import net.sourcecrumbs.api.symbols.SymbolContainer;
import net.sourcecrumbs.api.transunit.TranslationUnitContainer;

/**
 * Represents a native executable
 *
 * @author mcnulty
 */
public abstract class Executable implements SymbolContainer, TranslationUnitContainer, Binary, MachineCodeSource
{
    public abstract List<Library> getLibraries();

    @Override
    public BinaryType getBinaryType() {
        return BinaryType.Executable;
    }
}
