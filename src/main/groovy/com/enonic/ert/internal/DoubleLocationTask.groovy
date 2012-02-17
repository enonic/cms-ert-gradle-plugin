package com.enonic.ert.internal

import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/25/11
 * Time: 10:25 AM
 */
abstract class DoubleLocationTask extends BaseTask
{

    @TaskAction
    def initTask()
    {
        if ( !source )
        {
            println "no task-property 'source' set, check for project-properties"
            source = locationConvention.source
        }

        if ( !target )
        {
            println "no task-property 'target' set, check for project-properties"
            target = locationConvention.target
        }

        assert source, "Source has to be specified in task definition for task '${this.name}'"
        assert target, "Target has to be specified in task definition for task '${this.name}'"
    }


}
