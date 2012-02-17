package com.enonic.ert.domain

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 7/1/11
 * Time: 9:27 AM
 */
class CopyConvention
{
    String fileName;

    def copyConfig(Closure closure)
    {
        closure.delegate = this
        closure()
    }


}
