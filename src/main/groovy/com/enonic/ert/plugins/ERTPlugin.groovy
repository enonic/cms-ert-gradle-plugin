package com.enonic.ert.plugins

import com.enonic.ert.domain.LocationConvention
import com.enonic.ert.domain.PropertiesConvention
import com.enonic.ert.internal.BaseTask
import com.enonic.ert.tasks.ERTListLocations
import com.enonic.tools.ert.client.ResourceLocation
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/11/11
 * Time: 11:02 AM
 */


public class ERTPlugin implements Plugin<Project>
{

    def void apply(Project project)
    {

        project.convention.plugins.properties = new PropertiesConvention()
        LocationConvention locationConvention = setUpLocations(project)
        project.convention.plugins.locations = locationConvention

        project.task('listLocations', description: 'Shows all defined locations', type: ERTListLocations)

        project.tasks.withType(BaseTask.class).all {
            group = "Enonic Resource Tool"
        }

        project.ERTSync = com.enonic.ert.tasks.ERTSync
        project.ERTDiff = com.enonic.ert.tasks.ERTDiff
        project.ERTNukeCache = com.enonic.ert.tasks.ERTNukeCache
        project.ERTCopy = com.enonic.ert.tasks.ERTCopy
        project.ERTBackup = com.enonic.ert.tasks.ERTBackup
    }

    private LocationConvention setUpLocations(Project project)
    {
        def locations = project.container(ResourceLocation) {name -> new ResourceLocation(name)}
        LocationConvention locationConvention = new LocationConvention(locations)

        if ( project.hasProperty("source") )
        {
            locationConvention.source = project.source;
        }


        if ( project.hasProperty("target") )
        {
            locationConvention.target = project.target;
        }

        return locationConvention
    }


}

