package com.enonic.tools.ert.resourcetree;

import java.util.Map;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 1:57:34 PM
 */
public class ResourceTreeComparer
{
    public static ResourceTreeCompareResult compareTrees( ResourceTree source, ResourceTree target )
    {

        Map<String, String> sourceResources = source.getResourceNodeMap();
        Map<String, String> targetResources = target.getResourceNodeMap();

        MapDifference<String, String> diff = Maps.difference( sourceResources, targetResources );

        return new ResourceTreeCompareResult( diff, sourceResources, targetResources );
    }

}
