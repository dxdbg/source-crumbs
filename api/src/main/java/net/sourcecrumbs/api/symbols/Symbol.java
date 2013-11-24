package net.sourcecrumbs.api.symbols;

import com.google.common.primitives.UnsignedLong;

/**
 * A Symbol is a label for a memory address.
 *
 * @author mcnulty
 */
public interface Symbol
{
    String getName();

    UnsignedLong getAddress();
}
