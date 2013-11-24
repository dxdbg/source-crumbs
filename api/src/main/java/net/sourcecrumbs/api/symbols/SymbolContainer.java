package net.sourcecrumbs.api.symbols;


/**
 * Represents a container for Symbols
 *
 * @author mcnulty
 */
public interface SymbolContainer
{

    /**
     * @return the symbols in this container
     */
    Iterable<Symbol> getSymbols();
}
