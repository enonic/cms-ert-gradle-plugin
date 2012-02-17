package com.enonic.tools.ert.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;

import com.enonic.tools.ert.DiffDetailLevel;
import com.enonic.tools.ert.ResourceCache;
import com.enonic.tools.ert.ResourceCopier;
import com.enonic.tools.ert.resourcetree.ResourceTree;
import com.enonic.tools.ert.resourcetree.ResourceTreeBuilder;
import com.enonic.tools.ert.resourcetree.ResourceTreeCompareResult;
import com.enonic.tools.ert.resourcetree.ResourceTreeCompareResultPrinter;
import com.enonic.tools.ert.resourcetree.ResourceTreeComparer;
import com.enonic.tools.ert.resourcetree.ResourceTreeDiffResolver;
import com.enonic.tools.ert.selector.DefaultResourceSelector;
import com.enonic.tools.ert.selector.ResourceFileTools;
import com.enonic.tools.ert.selector.ResourceSelector;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Oct 22, 2010
 * Time: 8:40:45 AM
 */
public class ResourceClient
{
    private Logger LOG = Logger.getLogger( this.getClass().getName() );

    ResourceLocation source;

    ResourceLocation target;

    FileObject backuproot;

    ResourceClientProperties properties;

    ResourceCache resourceCache;

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat( "_dd.MM.yyyy_HH:mm" );

    public ResourceClient( ResourceLocation source, ResourceLocation target, ResourceCache resourceCache,
                           ResourceClientProperties properties )
    {
        this.source = source;
        this.target = target;
        this.resourceCache = resourceCache;
        this.properties = properties;

        if ( source.getExcludes() == null )
        {
            System.out.println( "Excludes is null for source" );
        }
        else
        {
            for ( String exclude : source.getExcludes() )
            {
                System.out.println( "Exclude: " + exclude );
            }
        }

    }

    public void sync()
        throws Exception
    {
        ResourceTree sourceTree = getResourceTree( source );
        ResourceTree targetTree = getResourceTree( target );

        ResourceTreeCompareResult compareResult = compareResourceTrees( sourceTree, targetTree );

        ResourceTreeCompareResultPrinter printer = new ResourceTreeCompareResultPrinter( source, target, compareResult );
        printer.printCompareResult();

        ResourceTreeDiffResolver diffResolver = new ResourceTreeDiffResolver( target.deleteMissingEntriesOnSync(), true );
        diffResolver.resolveTreeDifferences( compareResult, source, target );

        resourceCache.updateCache( target, sourceTree );
    }

    public void sync( boolean testRun )
        throws Exception
    {
        if ( testRun )
        {
            System.out.println( " ## TestRun : No changes done ##" );
        }

        ResourceTree sourceTree = getResourceTree( source );
        ResourceTree targetTree = getResourceTree( target );

        ResourceTreeCompareResult compareResult = compareResourceTrees( sourceTree, targetTree );

        ResourceTreeCompareResultPrinter printer = new ResourceTreeCompareResultPrinter( source, target, compareResult );
        printer.printCompareResult();

        if ( compareResult.noDifferences() )
        {
            System.out.println( "No differences" );
        }
        else
        {
            ResourceTreeDiffResolver diffResolver = new ResourceTreeDiffResolver( target.deleteMissingEntriesOnSync(), testRun );
            diffResolver.resolveTreeDifferences( compareResult, source, target );

            if ( !testRun )
            {
                resourceCache.updateCache( target, sourceTree );
            }
        }
    }


    public void diff()
        throws Exception
    {
        ResourceTree sourceTree = getResourceTree( source );
        ResourceTree targetTree = getResourceTree( target );

        ResourceTreeCompareResult compareResult = compareResourceTrees( sourceTree, targetTree );

        ResourceTreeCompareResultPrinter printer = new ResourceTreeCompareResultPrinter( source, target, compareResult );
        printer.printCompareResult();
    }

    public void backup()
        throws Exception
    {
        ResourceCopier resourceCopier = new ResourceCopier( true );

        if ( backuproot == null )
        {
            System.out.println( "Backup root not specified" );
            return;
        }

        Date now = Calendar.getInstance().getTime();

        String datePostFix = dateFormat.format( now );

        final String backupName =
            backuproot.getName().getPath() + "/" + convertPathToFileName( source.getRoot().getName().getPath() ) + "_" + datePostFix;

        System.out.println( "Creating backup: " + backupName );

        FileObject backupLocation = backuproot.resolveFile( backupName );

        resourceCopier.copy( source, backupLocation );
    }

    public void copyAll()
        throws Exception
    {
        ResourceCopier resourceCopier = new ResourceCopier( true );

        if ( target.isReadOnly() )
        {
            System.out.println( "Target: " + target.getUrl() + " is marked as 'readOnly', not allowed to overwrite" );
            return;
        }

        resourceCopier.copy( source, target.getRoot() );
    }


    public void copy( ResourceSelector resourceSelector )
        throws Exception
    {
        ResourceCopier resourceCopier = new ResourceCopier( true );
        resourceCopier.copy( source.getRoot(), target.getRoot(), resourceSelector );
    }

    public void copyFile( String fileName )
        throws Exception
    {
        final String sourceRootPath = source.getRoot().getName().getPath();

        if ( fileName.contains( sourceRootPath ) )
        {
            fileName = StringUtils.substringAfter( fileName, sourceRootPath );
        }

        ResourceFileTools.copyFile( source, fileName, target, new DefaultResourceSelector( source, source.getExcludes() ) );
    }

    public void nukeCache()
        throws Exception
    {
        resourceCache.nukeCache( source );
    }

    private String convertPathToFileName( String path )
    {
        return path.replace( "/", "_" );
    }

    private ResourceTreeCompareResult compareResourceTrees( ResourceTree sourceTree, ResourceTree targetTree )
    {
        ResourceTreeCompareResult compareResult = ResourceTreeComparer.compareTrees( sourceTree, targetTree );
        return compareResult;
    }

    private ResourceTree getResourceTree( ResourceLocation location )
        throws Exception
    {
        return getResourceTree( location, new DefaultResourceSelector( location, location.getExcludes() ) );
    }

    private ResourceTree getResourceTree( ResourceLocation location, ResourceSelector resourceSelector )
        throws Exception
    {
        ResourceTree resourceTree;

        if ( location.isCacheLocal() )
        {
            resourceTree = resourceCache.getResourceTree( location );
        }
        else
        {
            System.out.println( "Local cache not enabled for " + location.getName() );
            resourceTree = doCreateResourceTree( location.getRoot(), resourceSelector );
        }

        return resourceTree;
    }

    private ResourceTree doCreateResourceTree( FileObject root, ResourceSelector resourceSelector )
        throws Exception
    {
        ResourceTreeBuilder resourceTreeBuilder = new ResourceTreeBuilder( root, resourceSelector );
        resourceTreeBuilder.setDiffDetailLevel( DiffDetailLevel.CONTENT );
        ResourceTree resourceTree = resourceTreeBuilder.getResourceTree();

        return resourceTree;
    }

    public ResourceLocation getSource()
    {
        return source;
    }

    public ResourceLocation getTarget()
    {
        return target;
    }

    public FileObject getBackuproot()
    {
        return backuproot;
    }

    public void setBackuproot( FileObject backuproot )
    {
        this.backuproot = backuproot;
    }
}
