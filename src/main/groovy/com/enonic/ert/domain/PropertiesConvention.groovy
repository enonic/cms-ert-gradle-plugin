package com.enonic.ert.domain

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 2/11/11
 * Time: 12:10 PM
 */
class PropertiesConvention
{
    String backupUrl;
    String cacheUrl;

    def ert(Closure closure)
    {
        closure.delegate = this
        closure()
    }


}
