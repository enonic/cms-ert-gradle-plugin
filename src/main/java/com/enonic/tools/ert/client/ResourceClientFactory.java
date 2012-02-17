package com.enonic.tools.ert.client;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.CacheStrategy;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.cache.DefaultFilesCache;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs2.provider.webdav.WebdavFileProvider;

import com.enonic.tools.ert.ResourceCache;
import com.enonic.tools.ert.exception.ResourceNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Oct 22, 2010
 * Time: 10:10:19 AM
 */
public class ResourceClientFactory
{

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

        FileObject sourceRoot = fileSystemManager.resolveFile( sourceLocation.getUrl() );

        if ( !sourceRoot.exists() )
        {
            throw new ResourceNotFoundException( sourceLocation );
        }

        sourceLocation.setRoot( sourceRoot );

        FileObject targetRoot = null;

        if ( targetLocation != null && StringUtils.isNotBlank( targetLocation.getUrl() ) )
        {
            targetRoot = fileSystemManager.resolveFile( targetLocation.getUrl() );

            if ( !targetRoot.exists() )
            {
                System.out.println( "Target root does not exist, creating" );
            }

            targetLocation.setRoot( targetRoot );
        }

        String cacheDirRoot = properties.getLocalCacheDirRoot();
        String backupDirRoot = properties.getLocalBackupDirRoot();

        FileObject localCacheRoot = fileSystemManager.resolveFile( cacheDirRoot );
        FileObject backupRoot = fileSystemManager.resolveFile( backupDirRoot );

        ResourceCache resourceCache = new ResourceCache( localCacheRoot, fileSystemManager );

        ResourceClient client = new ResourceClient( sourceLocation, targetLocation, resourceCache, properties );
        client.setBackuproot( backupRoot );

        return client;
    }

}
