package net.sourcecrumbs.api.files;

import net.sourcecrumbs.api.machinecode.MachineCodeSource;
import net.sourcecrumbs.api.symbols.SymbolContainer;
import net.sourcecrumbs.api.transunit.TranslationUnitContainer;

/**
 * Represents a native core file
 *
 * @author mcnulty
 */
public abstract class CoreFile implements Binary, SymbolContainer, TranslationUnitContainer, MachineCodeSource
{
    @Override
    public BinaryType getBinaryType() {
        return BinaryType.CoreFile;
    }
}
