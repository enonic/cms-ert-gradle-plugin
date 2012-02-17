package com.enonic.tools.ert.client;

import java.util.List;

import org.apache.commons.vfs2.FileObject;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 10:34:16 PM
 */
public class ResourceLocation
{
    private String name;

    private String url;

    private List<String> excludes;

    private boolean readOnly = false;

    private boolean cacheLocal = false;

    private FileObject root;

    private boolean deleteMissingOnSync = true;

    private long cacheValidSeconds = 3600;

    private boolean forceCacheRefresh = false;

    public ResourceLocation( String name )
    {
        this.name = name;
    }

    public ResourceLocation( String name, String url )
    {
        this.name = name;
        this.url = url;
    }

    public ResourceLocation( String name, String url, boolean readOnly, boolean cacheLocal )
    {
        this.name = name;
        this.url = url;
        this.readOnly = readOnly;
        this.cacheLocal = cacheLocal;
    }

    public FileObject getRoot()
        throws Exception
    {
        return root;
    }

    public void setRoot( FileObject root )
    {
        this.root = root;
    }


    public boolean isForceCacheRefresh()
    {
        return forceCacheRefresh;
    }


    public boolean cacheExpired( long cacheTimestamp )
    {
        long now = System.currentTimeMillis();

        return ( now - cacheTimestamp ) / 1000 > cacheValidSeconds;
    }

    public boolean deleteMissingEntriesOnSync()
    {
        return deleteMissingOnSync;
    }

    public void setDeleteMissingOnSync( boolean deleteMissingOnSync )
    {
        this.deleteMissingOnSync = deleteMissingOnSync;
    }


    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public boolean isReadOnly()
    {
        return readOnly;
    }

    public void setReadOnly( boolean readOnly )
    {
        this.readOnly = readOnly;
    }

    public boolean isCacheLocal()
    {
        return cacheLocal;
    }

    public void setCacheLocal( boolean cacheLocal )
    {
        this.cacheLocal = cacheLocal;
    }

    public long getCacheValidSeconds()
    {
        return cacheValidSeconds;
    }

    public void setCacheValidSeconds( long cacheValidSeconds )
    {
        this.cacheValidSeconds = cacheValidSeconds;
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append( name );
        buf.append( ": " );
        buf.append( url );

        return buf.toString();
    }

    public List<String> getExcludes()
    {
        return excludes;
    }

    public void setExcludes( List<String> excludes )
    {
        this.excludes = excludes;
    }
}
