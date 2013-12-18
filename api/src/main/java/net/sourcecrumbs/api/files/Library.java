package net.sourcecrumbs.api.files;

/**
 * Represents a native library
 *
 * @author mcnulty
 */
public abstract class Library implements Binary
{
    @Override
    public BinaryType getBinaryType() {
        return BinaryType.Library;
    }
}
