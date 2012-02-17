package com.enonic.ert.tasks

import com.enonic.ert.PluginClientFactory
import com.enonic.ert.internal.DoubleLocationTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/15/11
 * Time: 10:33 AM
 */
class ERTCopy extends DoubleLocationTask
{
    def String fileName

    @TaskAction
    def copyResource()
    {
        if ( this.project.hasProperty("fileName") )
        {
            this.fileName = this.project.fileName
        }


        assert fileName, "Filename must be set"
        PluginClientFactory.getClient(this).copyFile(fileName)
    }

}

