package com.enonic.tools.ert.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 2:09:34 PM
 */
public class ResourceRelativeNameCreator
{

    String rootPath;

    public ResourceRelativeNameCreator( FileObject root )
    {
        rootPath = root.getName().getPath();
    }

    public String createRelativePath( FileObject file )
        throws Exception
    {

        String fullName = file.getName().getPath();
        String baseName = file.getName().getBaseName();

        return StringUtils.substringAfter( fullName, rootPath );
    }

}
