package com.enonic.tools.ert.client;


import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.CacheStrategy;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.cache.DefaultFilesCache;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs2.provider.webdav.WebdavFileProvider;

import com.enonic.tools.ert.ResourceCache;
import com.enonic.tools.ert.exception.LocationConnectionException;
import com.enonic.tools.ert.exception.ResourceNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Oct 22, 2010
 * Time: 10:10:19 AM
 */
public class ResourceClientFactory
{
    private Logger LOG = Logger.getLogger( this.getClass().getName() );

    public static final String LINEBREAK = System.getProperty( "line.separator" );

    final DefaultFileSystemManager fileSystemManager;

    public ResourceClientFactory()
        throws Exception
    {

        fileSystemManager = new DefaultFileSystemManager();

        fileSystemManager.addProvider( "webdav", new WebdavFileProvider() );
        fileSystemManager.addProvider( "file", new DefaultLocalFileProvider() );

        fileSystemManager.setCacheStrategy( CacheStrategy.ON_RESOLVE );
        fileSystemManager.setFilesCache( new DefaultFilesCache() );

    }

    public ResourceClient createResourceClient( ResourceLocation sourceLocation, ResourceLocation targetLocation,
                                                ResourceClientProperties properties )
        throws Exception
    {
        if ( sourceLocation == null || StringUtils.isBlank( sourceLocation.getUrl() ) )
        {
            throw new IllegalArgumentException( "Source-url cannot be empty" );
        }
        sourceLocation.setRoot( getLocationRoot( sourceLocation.getUrl(), false ) );

        if ( targetLocation != null && StringUtils.isNotBlank( targetLocation.getUrl() ) )
        {
            targetLocation.setRoot( getLocationRoot( targetLocation.getUrl(), true ) );
        }

        String cacheDirRoot = properties.getLocalCacheDirRoot();
        String backupDirRoot = properties.getLocalBackupDirRoot();

        FileObject localCacheRoot = getLocationRoot( cacheDirRoot, true );
        ResourceCache resourceCache = new ResourceCache( localCacheRoot, fileSystemManager );

        ResourceClient client = new ResourceClient( sourceLocation, targetLocation, resourceCache, properties );

        FileObject backupRoot = getLocationRoot( backupDirRoot, true );
        client.setBackuproot( backupRoot );

        return client;
    }

    private FileObject getLocationRoot( final String url, boolean createIfNotExists )
        throws FileSystemException
    {
        FileObject locationRoot = null;

        try
        {
            locationRoot = fileSystemManager.resolveFile( url );
            locationRoot.getType();
        }
        catch ( FileSystemException e )
        {
            doHandleFileSystemException( e, url );
        }

        if ( !locationRoot.exists() )
        {
            if ( createIfNotExists )
            {
                System.out.println( "Target root: " + locationRoot + " does not exist, will be created" );
            }
            else
            {
                throw new ResourceNotFoundException( "Root resource not found on location " + url );
            }
        }

        return locationRoot;
    }


    private void doHandleFileSystemException( FileSystemException e, String url )
    {
        StringBuffer buf = new StringBuffer();

        Throwable cause = e.getCause();

        while ( cause != null )
        {
            buf.append( LINEBREAK + "Caused by: " + cause.getMessage() );
            cause = cause.getCause();
        }

        throw new LocationConnectionException( "Not able to connect to source location: " + url + buf.toString(), e );

    }

}
