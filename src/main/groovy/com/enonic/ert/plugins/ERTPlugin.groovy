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
        /*
        project.task('copy', description: 'Copy resources', type: ERTCopy)

        project.task('diff', description: 'Diff between source and target', type: ERTDiff)

        project.task('nukeCache', description: 'Nukes the cache for source', type: ERTNukeCache)

        project.task('backup', description: 'Backup source to backup-root', type: ERTBackup)

        project.task('sync', description: 'sync changes between source to target', type: ERTSync)
        */

        project.tasks.withType(BaseTask.class).all {
            group = "Enonic Resource Tool"
        }
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

