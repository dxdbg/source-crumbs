package net.sourcecrumbs.api.files;


/**
 * Represents a native core file
 *
 * @author mcnulty
 */
public abstract class CoreFile implements Binary
{
    @Override
    public BinaryType getBinaryType() {
        return BinaryType.CoreFile;
    }
}
