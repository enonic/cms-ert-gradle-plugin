package com.enonic.ert.tasks

import com.enonic.ert.PluginClientFactory
import com.enonic.ert.internal.SingleLocationTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 6/24/11
 * Time: 12:31 PM
 */
class ERTBackup extends SingleLocationTask
{

    @TaskAction
    def backup()
    {
        PluginClientFactory.getClient(this).backup()
    }


}
