package com.enonic.tools.ert.client;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 1:18:52 PM
 */
public class ResourceClientProperties
{
    String localCacheDirRoot;

    String localBackupDirRoot;

    public String getLocalCacheDirRoot()
    {
        return localCacheDirRoot;
    }

    public void setLocalCacheDirRoot( String localCacheDirRoot )
    {
        this.localCacheDirRoot = localCacheDirRoot;
    }

    public String getLocalBackupDirRoot()
    {
        return localBackupDirRoot;
    }

    public void setLocalBackupDirRoot( String localBackupDirRoot )
    {
        this.localBackupDirRoot = localBackupDirRoot;
    }
}


