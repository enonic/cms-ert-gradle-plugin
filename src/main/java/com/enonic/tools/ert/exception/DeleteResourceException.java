package com.enonic.tools.ert.exception;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/18/11
 * Time: 3:40 PM
 */
public class DeleteResourceException
    extends RuntimeException
{

    public DeleteResourceException( String message )
    {
        super( message );

    }

    public DeleteResourceException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}
