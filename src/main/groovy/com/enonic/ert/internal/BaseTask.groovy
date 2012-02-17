package com.enonic.ert.internal

import com.enonic.ert.domain.LocationConvention
import com.enonic.ert.domain.PropertiesConvention
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/25/11
 * Time: 8:41 AM
 */
abstract class BaseTask extends DefaultTask
{
    def LocationConvention locationConvention
    def PropertiesConvention propertiesConvention

    String source;
    String target;

    @TaskAction
    def initBaseTask()
    {
        propertiesConvention = this.project.convention.plugins.properties
        locationConvention = this.project.convention.plugins.locations

    }
}
