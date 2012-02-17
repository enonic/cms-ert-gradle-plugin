package com.enonic.ert.tasks

import com.enonic.ert.internal.BaseTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/25/11
 * Time: 9:49 AM
 */
class ERTListLocations extends BaseTask
{
    @TaskAction
    def listLocations()
    {
        println "Execute listLocations"
        locationConvention.printLocations();

    }


}
