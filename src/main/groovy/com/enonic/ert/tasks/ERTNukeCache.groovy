package com.enonic.ert.tasks

import com.enonic.ert.PluginClientFactory
import com.enonic.ert.internal.SingleLocationTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/25/11
 * Time: 1:51 PM
 */
class ERTNukeCache extends SingleLocationTask
{

    @TaskAction
    def nukeCache()
    {
        PluginClientFactory.getClient(this).nukeCache()
    }

}
