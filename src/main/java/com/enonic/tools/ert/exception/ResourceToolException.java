package com.enonic.tools.ert.exception;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 10:38:39 PM
 */
public class ResourceToolException
    extends RuntimeException
{
    public ResourceToolException( String message )
    {
        super( message );
    }

    public ResourceToolException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
