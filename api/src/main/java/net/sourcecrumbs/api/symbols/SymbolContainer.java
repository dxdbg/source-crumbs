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

    /**
     * @param name the name of the symbol
     *
     * @return the symbol or null if no symbol with the specified name exists
     */
    Symbol getSymbol(String name);
}
