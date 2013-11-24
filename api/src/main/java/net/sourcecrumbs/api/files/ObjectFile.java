package net.sourcecrumbs.api.files;

import net.sourcecrumbs.api.machinecode.MachineCodeSource;
import net.sourcecrumbs.api.symbols.SymbolContainer;
import net.sourcecrumbs.api.transunit.TranslationUnitContainer;

/**
 * Represents a native object file
 *
 * @author mcnulty
 */
public abstract class ObjectFile implements SymbolContainer, TranslationUnitContainer, Binary, MachineCodeSource
{
    @Override
    public BinaryType getBinaryType() {
        return BinaryType.ObjectFile;
    }
}
