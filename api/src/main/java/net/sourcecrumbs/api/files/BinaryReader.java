package net.sourcecrumbs.api.files;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Abstract factory for reading native binaries
 *
 * @author mcnulty
 */
public interface BinaryReader
{
    /**
     * Opens the binary at the specified path
     *
     * @param path the path
     * @return the binary
     *
     * @throws IOException on failure to open the binary
     * @throws UnknownFormatException if the binary is in a format that is unknown or unsupported
     */
    Binary open(Path path) throws IOException, UnknownFormatException;
}
