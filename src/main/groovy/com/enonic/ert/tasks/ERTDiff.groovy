package com.enonic.ert.tasks

import com.enonic.ert.PluginClientFactory
import com.enonic.ert.internal.DoubleLocationTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/25/11
 * Time: 1:44 PM
 */
class ERTDiff extends DoubleLocationTask
{

    @TaskAction
    def diff()
    {
        PluginClientFactory.getClient(this).diff()
    }
}
