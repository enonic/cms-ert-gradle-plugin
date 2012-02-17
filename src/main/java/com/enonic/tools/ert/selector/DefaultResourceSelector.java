package com.enonic.tools.ert.selector;

import java.util.List;

import com.enonic.tools.ert.client.ResourceLocation;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 12:13:33 PM
 */
public class DefaultResourceSelector
    extends ResourceSelector
{

    public DefaultResourceSelector( ResourceLocation source, List<String> excludePaths )
        throws Exception
    {
        appendExcludePatterns( FileNamePattern.HIDDEN_FILES, FileNamePattern.SVN_FILES );

        if ( excludePaths != null && source != null )
        {
            for ( String exclude : excludePaths )
            {
                appendExcludePattern( source.getRoot().getName().getPath() + exclude );
            }
        }

        setDefaultIncludeFiles( true );
    }
}
