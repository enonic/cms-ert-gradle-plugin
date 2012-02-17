package com.enonic.tools.ert.utils;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;


/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Nov 3, 2010
 * Time: 10:11:41 PM
 */
public class ResourceFileUtils
{
    public static String getRelativePath( FileObject source, FileObject srcFile )
        throws FileSystemException
    {
        return source.getName().getRelativeName( srcFile.getName() );
    }


}
