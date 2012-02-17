package com.enonic.ert

import com.enonic.ert.domain.LocationConvention
import com.enonic.ert.domain.PropertiesConvention
import com.enonic.ert.internal.BaseTask
import com.enonic.tools.ert.client.ResourceClient
import com.enonic.tools.ert.client.ResourceClientFactory
import com.enonic.tools.ert.client.ResourceClientProperties

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/25/11
 * Time: 11:02 AM
 */
class PluginClientFactory
{

    public static ResourceClient getClient(BaseTask task)
    {

        LocationConvention locationConvention = task.locationConvention
        PropertiesConvention propertiesConvention = task.propertiesConvention

        ResourceClientFactory factory = new ResourceClientFactory();

        ResourceClientProperties properties = new ResourceClientProperties();
        properties.setLocalBackupDirRoot(propertiesConvention.backupUrl);
        properties.setLocalCacheDirRoot(propertiesConvention.cacheUrl);

        ResourceClient client

        if ( !task.target )
        {
            client = factory.createResourceClient(locationConvention.getLocation(task.source), null, properties)
        }
        else
        {
            client = factory.createResourceClient(locationConvention.getLocation(task.source), locationConvention.getLocation(task.target), properties)
        }

        return client
    }

}
