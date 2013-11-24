package net.sourcecrumbs.api.files;

/**
 * Thrown when a binary file is opened and its format is unknown or unsupported.
 *
 * @author mcnulty
 */
public class UnknownFormatException extends Exception
{
    public UnknownFormatException(String message)
    {
        super(message);
    }

    public UnknownFormatException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UnknownFormatException(Throwable cause)
    {
        super(cause);
    }
}
