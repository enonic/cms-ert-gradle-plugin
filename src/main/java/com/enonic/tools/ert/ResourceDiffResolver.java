package com.enonic.tools.ert;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileType;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Oct 29, 2010
 * Time: 10:21:13 PM
 */
public class ResourceDiffResolver
{
    private LinkedHashSet<String> topDirsSourceOnly = Sets.newLinkedHashSet();

    private LinkedHashSet<String> topDirsTargetOnly = Sets.newLinkedHashSet();

    private FileObject sourceDir, targetDir;

    private DiffDetailLevel diffDetailLevel;

    int comparedFiles = 0;

    public ResourceDiffResolver( FileObject sourceDir, FileObject targetDir )
        throws Exception
    {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
    }


    private void diff()
    {
        // Approach 1:

        // Create tree for both, with detail level
        // Compare tree

        // Pros: Kan lagres?
        // Ulemper: Må lagre resultat

        // Approach 2:

        // Get root children
        // For each folder, check if in both
        // For files not in both ->

        // Pros: Ingen lagring underveis
        // Cons: Ikke noe resultat som kan lagres?
    }


    public void compareDirs( FileObject sourceDir, FileObject targetDir )
        throws Exception
    {
        if ( comparedFiles > 0 && comparedFiles % 10 == 0 )
        {
            System.out.println( "Compared " + comparedFiles + " files" );
        }

        Set<String> sourceChildrenNames = createLinkedHashSet( Lists.newArrayList( sourceDir.getChildren() ) );
        Set<String> targetChildrenNames = createLinkedHashSet( Lists.newArrayList( targetDir.getChildren() ) );

        Sets.SetView<String> onlyInSource = Sets.difference( sourceChildrenNames, targetChildrenNames );
        Sets.SetView<String> onlyInTarget = Sets.difference( targetChildrenNames, sourceChildrenNames );
        Sets.SetView<String> inBothSets = Sets.intersection( sourceChildrenNames, targetChildrenNames );

        onlyInSource.copyInto( topDirsSourceOnly );
        onlyInTarget.copyInto( topDirsTargetOnly );

        comparedFiles = comparedFiles + sourceChildrenNames.size();

        for ( FileObject child : sourceDir.getChildren() )
        {
            if ( child.getName().getBaseName().equals( ".svn" ) )
            {
                continue;
            }

            final boolean isFolder = child.getType() == FileType.FOLDER;

            final boolean containedInBothSets = inBothSets.contains( child.getName().getBaseName() );

            if ( isFolder && containedInBothSets )
            {
                compareDirs( child, findFileByName( child.getName().getBaseName(), targetDir ) );
            }
        }
    }

    private FileObject findFileByName( String name, FileObject parentFolder )
        throws Exception
    {
        for ( FileObject child : parentFolder.getChildren() )
        {
            if ( child.getName().getBaseName().equals( name ) )
            {
                return child;
            }
        }

        return null;
    }


    private LinkedHashSet<String> createLinkedHashSet( List<FileObject> sourceChildrenList )
    {
        LinkedHashSet<String> sourceChildren = Sets.newLinkedHashSet();

        for ( FileObject child : sourceChildrenList )
        {
            sourceChildren.add( child.getName().getBaseName() );
        }

        return sourceChildren;
    }

    public LinkedHashSet<String> getTopDirsSourceOnly()
    {
        return topDirsSourceOnly;
    }

    public LinkedHashSet<String> getTopDirsTargetOnly()
    {
        return topDirsTargetOnly;
    }

}
