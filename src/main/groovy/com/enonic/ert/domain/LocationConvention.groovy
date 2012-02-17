package com.enonic.ert.domain

import com.enonic.tools.ert.client.ResourceLocation
import org.gradle.api.NamedDomainObjectContainer

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/11/11
 * Time: 12:05 PM
 */
class LocationConvention
{
    NamedDomainObjectContainer<ResourceLocation> locations

    String source
    String target

    List<String> excludes;

    boolean testRun

    LocationConvention(NamedDomainObjectContainer<ResourceLocation> locations)
    {
        this.locations = locations
    }

    public ResourceLocation getLocation(String name)
    {
        return locations.find {it.name.equals(name)}
    }

    def locations(Closure closure)
    {
        locations.configure(closure)
    }

    def location(Map<String, String> values)
    {
        ResourceLocation resourceLocation = new ResourceLocation(values.name, values.url);
        resourceLocation.setExcludes(excludes);
        locations.add(resourceLocation);
    }

    def location(Closure closure)
    {
        ResourceLocation location = new ResourceLocation();

        closure.delegate = location;
        closure()

        locations.add(location);
    }

    def printLocations()
    {
        locations.each {println it}
    }
}
