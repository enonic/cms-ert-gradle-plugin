package com.enonic.tools.ert.exception;

public class LocationConnectionException
    extends RuntimeException
{
    public LocationConnectionException( final String s, final Throwable throwable )
    {
        super( s, throwable );
    }
}
