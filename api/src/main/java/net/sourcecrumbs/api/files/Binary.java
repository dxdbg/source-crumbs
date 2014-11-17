package net.sourcecrumbs.api.files;

import net.sourcecrumbs.api.debug.symbols.DebugSymbolContainer;
import net.sourcecrumbs.api.machinecode.MachineCodeSource;
import net.sourcecrumbs.api.symbols.SymbolContainer;
import net.sourcecrumbs.api.transunit.TranslationUnitContainer;

/**
 * Marker interface for all native binaries. Allows all types of binaries to be treated generically in certain situations.
 *
 * @author dmcnulty
 */
public interface Binary extends MachineCodeSource, SymbolContainer, TranslationUnitContainer, DebugSymbolContainer
{
    BinaryType getBinaryType();
}
