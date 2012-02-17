package com.enonic.tools.ert.resourcetree;

import java.util.Map;

import com.google.common.collect.MapDifference;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 9:59:30 PM
 */
public class ResourceTreeCompareResult
{
    MapDifference<String, String> diffMap;

    Map<String, String> sourceResources;

    Map<String, String> targetResources;

    public ResourceTreeCompareResult( MapDifference<String, String> diffMap, Map<String, String> sourceResources,
                                      Map<String, String> targetResources )
    {
        this.diffMap = diffMap;
        this.sourceResources = sourceResources;
        this.targetResources = targetResources;
    }

    public MapDifference<String, String> getDiffMap()
    {
        return diffMap;
    }

    public void setDiffMap( MapDifference<String, String> diffMap )
    {
        this.diffMap = diffMap;
    }


    public boolean noDifferences()
    {
        return diffMap.areEqual();
    }

    public Map<String, String> getEntriesOnlyInSource()
    {
        return diffMap.entriesOnlyOnLeft();
    }

    public Map<String, String> getEntriesOnlyInTarget()
    {
        return diffMap.entriesOnlyOnRight();
    }

    public int getNumberOfDifferingEntries()
    {
        return diffMap.entriesDiffering().size();
    }

    public int getNumberOfCommonEntries()
    {
        return diffMap.entriesInCommon().size();
    }

    public Map<String, MapDifference.ValueDifference<String>> getDifferingEntries()
    {
        return diffMap.entriesDiffering();
    }

    public int getTotalNumberOfDifferences()
    {
        return getNumberOfDifferingEntries() + getEntriesOnlyInSource().size() + getEntriesOnlyInTarget().size();
    }
}
