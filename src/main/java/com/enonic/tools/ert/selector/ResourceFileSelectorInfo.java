package com.enonic.tools.ert.selector;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Nov 3, 2010
 * Time: 10:30:48 PM
 */
public class ResourceFileSelectorInfo
    implements FileSelectInfo
{
    private FileObject baseFolder;

    private FileObject file;

    private int depth;

    public FileObject getBaseFolder()
    {
        return baseFolder;
    }

    public void setBaseFolder( final FileObject baseFolder )
    {
        this.baseFolder = baseFolder;
    }

    public FileObject getFile()
    {
        return file;
    }

    public void setFile( final FileObject file )
    {
        this.file = file;
    }

    public int getDepth()
    {
        return depth;
    }

    public void setDepth( final int depth )
    {
        this.depth = depth;
    }

}
