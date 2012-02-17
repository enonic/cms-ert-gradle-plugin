package com.enonic.tools.ert.exception;

import org.apache.commons.vfs2.FileObject;

import com.enonic.tools.ert.client.ResourceLocation;


/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 12/29/10
 * Time: 11:21 AM
 */
public class ResourceNotFoundException
    extends RuntimeException
{

    public ResourceNotFoundException( ResourceLocation location )
    {
        super( "Location not found on location: " + location.getUrl() );
    }


    public ResourceNotFoundException( ResourceLocation location, String fileName )
    {
        super( "Resource: " + fileName + " not found on location: " + location.getUrl() );
    }

    public ResourceNotFoundException( FileObject sourceRoot, String fileName )
    {
        super( "Resource: " + fileName + " not found on location: " + sourceRoot.getName().getURI() );
    }
}
