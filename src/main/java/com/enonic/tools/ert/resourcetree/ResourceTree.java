package com.enonic.tools.ert.resourcetree;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Nov 3, 2010
 * Time: 10:22:24 PM
 */
public class ResourceTree
{

    Map<String, String> resourceNodeMap = Maps.newTreeMap();

    public ResourceTree()
    {

    }

    public void addNode( String path, String hash )
    {
        resourceNodeMap.put( path, hash );
    }

    public void printTree()
    {
        System.out.println( "Tree size: " + resourceNodeMap.size() );

        for ( String key : resourceNodeMap.keySet() )
        {
            System.out.println( key + " : " + resourceNodeMap.get( key ) );

        }
    }

    public int size()
    {
        return resourceNodeMap.size();
    }

    public Map<String, String> getResourceNodeMap()
    {
        return resourceNodeMap;
    }

    public int getSize()
    {
        return resourceNodeMap.size();
    }
}
