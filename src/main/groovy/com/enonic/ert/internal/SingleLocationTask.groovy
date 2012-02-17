package com.enonic.ert.internal

import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/25/11
 * Time: 10:02 AM
 */
abstract class SingleLocationTask extends BaseTask
{
    String source;

    @TaskAction
    def initTask()
    {
        if ( !source )
        {
            source = locationConvention.source
        }

        assert source, "Source has to be specified in project or task definition for task '${this.name}'"
    }


}
