package com.enonic.ert.tasks

import com.enonic.ert.PluginClientFactory
import com.enonic.ert.internal.DoubleLocationTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 6/24/11
 * Time: 12:27 PM
 */
class ERTSync extends DoubleLocationTask
{
    def boolean testRun = false

    @TaskAction
    def sync()
    {
        if ( this.project.hasProperty("testRun") )
        {
            this.testRun = "true".equals(this.project.testRun)
        }

        PluginClientFactory.getClient(this).sync(testRun)
    }


}
