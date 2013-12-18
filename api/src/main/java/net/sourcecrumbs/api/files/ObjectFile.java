package net.sourcecrumbs.api.files;

/**
 * Represents a native object file
 *
 * @author mcnulty
 */
public abstract class ObjectFile implements Binary
{
    @Override
    public BinaryType getBinaryType() {
        return BinaryType.ObjectFile;
    }
}
