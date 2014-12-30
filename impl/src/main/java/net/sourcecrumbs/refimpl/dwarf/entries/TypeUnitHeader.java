package net.sourcecrumbs.refimpl.dwarf.entries;

import org.codehaus.preon.annotation.BoundNumber;

/**
 * Header for a type in a DWARF file
 *
 * @author dmcnulty
 */
public class TypeUnitHeader extends CompilationUnitHeader
{
    @BoundNumber(size = "64")
    private long typeSignature;

    @BoundNumber(size = "unitLength.offsetLength")
    private long typeOffset;

    public long getTypeSignature()
    {
        return typeSignature;
    }

    public void setTypeSignature(long typeSignature)
    {
        this.typeSignature = typeSignature;
    }

    public long getTypeOffset()
    {
        return typeOffset;
    }

    public void setTypeOffset(long typeOffset)
    {
        this.typeOffset = typeOffset;
    }
}
