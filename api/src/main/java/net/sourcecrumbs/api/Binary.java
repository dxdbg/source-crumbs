package net.sourcecrumbs.api;

/**
 * Marker interface for all native binaries. Allows all types of binaries to be treated generically in certain situations.
 *
 * @author dmcnulty
 */
public interface Binary
{
    BinaryType getBinaryType();
}
