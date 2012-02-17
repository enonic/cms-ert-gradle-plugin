package com.enonic.tools.ert.selector;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSelector;

import com.google.common.collect.Lists;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Nov 2, 2010
 * Time: 9:41:14 PM
 */
public class ResourceSelector
    implements FileSelector
{
    int minDepth = 0;

    int maxDepth = Integer.MAX_VALUE;

    private boolean defaultIncludeFiles = true;

    private List<Pattern> includePatterns = Lists.newArrayList();

    private List<Pattern> excludePatterns = Lists.newArrayList();


    public ResourceSelector()
    {
    }

    public ResourceSelector( int minDepth, int maxDepth )
    {
        this.minDepth = minDepth;
        this.maxDepth = maxDepth;
    }

    public boolean includeFile( FileSelectInfo fileInfo )
        throws Exception
    {
        final String fileName = fileInfo.getFile().getName().getBaseName();
        final String filePath = fileInfo.getFile().getName().getPath();

        for ( Pattern matchPattern : excludePatterns )
        {
            if ( matches( matchPattern, filePath ) )
            {
                return false;
            }

            if ( matches( matchPattern, fileName ) )
            {
                return false;
            }
        }

        for ( Pattern matchPattern : includePatterns )
        {
            if ( matches( matchPattern, fileName ) )
            {
                return true;
            }
        }

        final int depth = fileInfo.getDepth();
        if ( !( minDepth <= depth && depth <= maxDepth ) )
        {
            return false;
        }

        return defaultIncludeFiles;
    }

    private boolean matches( Pattern matchPattern, String fileName )
    {
        Matcher m = matchPattern.matcher( fileName );

        return m.matches();
    }


    public boolean traverseDescendents( FileSelectInfo fileInfo )
        throws Exception
    {
        boolean traverseFolder = true;

        final String folderName = fileInfo.getFile().getName().getBaseName();
        final String folderPath = fileInfo.getFile().getName().getPath();

        if ( folderName.startsWith( "." ) )
        {
            return false;
        }

        if ( folderName.equals( ".svn" ) )
        {
            return false;
        }

        if ( fileInfo.getDepth() >= maxDepth )
        {
            return false;
        }

        for ( Pattern matchPattern : excludePatterns )
        {
            if ( matches( matchPattern, folderPath ) )
            {
                System.out.println( "Excluded: " + folderPath );
                return false;
            }
        }

        return traverseFolder;
    }

    public int getMinDepth()
    {
        return minDepth;
    }

    public void setMinDepth( int minDepth )
    {
        this.minDepth = minDepth;
    }

    public int getMaxDepth()
    {
        return maxDepth;
    }

    public void setMaxDepth( int maxDepth )
    {
        this.maxDepth = maxDepth;
    }

    public void appendIncludePatterns( FileNamePattern... fileNamePatterns )
    {
        for ( FileNamePattern fileNamePattern : fileNamePatterns )
        {
            includePatterns.add( fileNamePattern.pattern );
        }
    }

    public void appendIncludePatterns( Pattern... patterns )
    {
        includePatterns.addAll( Lists.newArrayList( patterns ) );
    }

    public void appendIncludePattern( String includePattern )
    {
        includePatterns.add( Pattern.compile( includePattern ) );
    }

    public void appendExcludePattern( String excludePattern )
    {
        excludePatterns.add( Pattern.compile( excludePattern ) );
    }

    public void appendExcludePatterns( FileNamePattern... fileNamePatterns )
    {
        for ( FileNamePattern fileNamePattern : fileNamePatterns )
        {
            excludePatterns.add( fileNamePattern.pattern );
        }
    }

    public void appendExcludePatterns( Pattern... patterns )
    {
        excludePatterns.addAll( Lists.newArrayList( patterns ) );
    }

    public List<Pattern> getIncludePatterns()
    {
        return includePatterns;
    }

    public List<Pattern> getExcludePatterns()
    {
        return excludePatterns;
    }

    public void setDefaultIncludeFiles( boolean defaultIncludeFiles )
    {
        this.defaultIncludeFiles = defaultIncludeFiles;
    }
}
