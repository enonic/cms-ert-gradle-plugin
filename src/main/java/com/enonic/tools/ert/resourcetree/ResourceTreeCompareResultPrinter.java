package com.enonic.tools.ert.resourcetree;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.enonic.tools.ert.client.ResourceLocation;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 8/12/11
 * Time: 9:30 AM
 */
public class ResourceTreeCompareResultPrinter
{

    private ResourceLocation source;

    private ResourceLocation target;

    private final static int HYPHEN_PADDING = 35;

    private ResourceTreeCompareResult result;

    public ResourceTreeCompareResultPrinter( ResourceLocation source, ResourceLocation target, ResourceTreeCompareResult result )
    {
        this.source = source;
        this.target = target;
        this.result = result;
    }


    public void printCompareResult()
    {
        printHyphens();
        System.out.println( "Differences found: " + result.getTotalNumberOfDifferences() );
        System.out.println( "Common entries found: " + result.getNumberOfCommonEntries() );
        printHyphens();
        printMapContent( "Only in " + source.getName() + ": ", result.getEntriesOnlyInSource() );
        printMapContent( "Only in " + target.getName() + ": ", result.getEntriesOnlyInTarget() );

        printContentDifferences();
    }

    private void printHyphens()
    {
        System.out.println( getHyphensString() );
    }

    private void printContentDifferences()
    {
        if ( result.getDifferingEntries().size() == 0 )
        {
            return;
        }

        printHyphens();
        System.out.println( "Entries with different content: " + result.getNumberOfDifferingEntries() + " entries" );
        printHyphens();

        for ( String key : result.getDifferingEntries().keySet() )
        {
            System.out.println( key );
        }

        printHyphens();
    }

    private void printMapContent( final String message, Map<String, String> map )
    {
        if ( map.isEmpty() )
        {
            return;
        }

        printHyphens();
        System.out.println( message + map.size() + " entries" );
        printHyphens();
        for ( String resource : map.keySet() )
        {
            System.out.println( resource );
        }
    }

    private String getHyphensString()
    {
        return StringUtils.repeat( "-", HYPHEN_PADDING +
            ( source.getName().length() > target.getName().length() ? source.getName().length() : target.getName().length() ) );
    }

}
