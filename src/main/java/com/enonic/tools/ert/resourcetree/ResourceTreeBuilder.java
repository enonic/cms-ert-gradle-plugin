package com.enonic.tools.ert.resourcetree;


import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;

import com.enonic.tools.ert.DiffDetailLevel;
import com.enonic.tools.ert.ProgressCounter;
import com.enonic.tools.ert.selector.ResourceFileSelectorInfo;
import com.enonic.tools.ert.selector.ResourceSelector;
import com.enonic.tools.ert.utils.ResourceHashCreator;
import com.enonic.tools.ert.utils.ResourceRelativeNameCreator;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Nov 1, 2010
 * Time: 8:34:35 PM
 */
public class ResourceTreeBuilder
{
    private FileObject root;

    private String rootName;

    private ResourceSelector selector;

    private DiffDetailLevel diffDetailLevel = DiffDetailLevel.CONTENT;

    private ProgressCounter processCounter;

    private ResourceRelativeNameCreator relativeNameCreator;

    public ResourceTreeBuilder( FileObject root, ResourceSelector selector )
    {
        this.root = root;
        this.selector = selector;
        relativeNameCreator = new ResourceRelativeNameCreator( root );

        processCounter = new ProgressCounter( 100 );
        processCounter.setOperation( "Added nodes" );
    }

    public ResourceTree getResourceTree()
        throws Exception
    {
        ResourceTree resourceTree = new ResourceTree();

        for ( FileObject child : root.getChildren() )
        {
            final ResourceFileSelectorInfo info = createSelectorInfo( child, 0 );

            if ( isFolder( child ) && selector.traverseDescendents( info ) )
            {
                addNodes( resourceTree, child, info );
            }
            else if ( !isFolder( child ) && selector.includeFile( info ) )
            {
                createNode( resourceTree, child );
            }
        }

        return resourceTree;
    }

    private void createNode( ResourceTree resourceTree, FileObject child )
        throws Exception
    {
        resourceTree.addNode( relativeNameCreator.createRelativePath( child ), ResourceHashCreator.createHash( child ) );
    }

    private ResourceFileSelectorInfo createSelectorInfo( FileObject child, int depth )
    {
        final ResourceFileSelectorInfo info = new ResourceFileSelectorInfo();
        info.setBaseFolder( root );
        info.setDepth( depth );
        info.setFile( child );
        return info;
    }

    private void addNodes( ResourceTree resourceTree, FileObject parent, ResourceFileSelectorInfo parentFileInfo )
        throws Exception
    {

        createNode( resourceTree, parent );

        if ( parent.getType() != FileType.FOLDER )
        {
            return;
        }

        for ( FileObject child : parent.getChildren() )
        {
            final ResourceFileSelectorInfo childInfo = createSelectorInfo( child, parentFileInfo.getDepth() + 1 );

            if ( isFolder( child ) && selector.traverseDescendents( childInfo ) )
            {
                addNodes( resourceTree, child, childInfo );
            }
            else if ( !isFolder( child ) && selector.includeFile( childInfo ) )
            {
                createNode( resourceTree, child );
                processCounter.addElement();
            }
        }
    }

    private boolean isFolder( FileObject fileObject )
        throws FileSystemException
    {
        return fileObject.getType() == FileType.FOLDER;
    }

    public void setDiffDetailLevel( DiffDetailLevel diffDetailLevel )
    {
        this.diffDetailLevel = diffDetailLevel;
    }
}
