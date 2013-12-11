package net.sourcecrumbs.api.symbols;

/**
 * A Symbol is a label for a memory address.
 *
 * @author mcnulty
 */
public interface Symbol
{
    String getName();

    long getAddress();
}
