package com.enonic.tools.ert.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;

public class BackupNameCreator
{
    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat( "_dd.MM.yyyy_HH:mm" );

    public static String createBackupName( FileObject backupRoot, FileObject sourceRoot )
        throws Exception
    {
        Date now = Calendar.getInstance().getTime();

        String datePostFix = dateFormat.format( now );

        final String backupName =
            backupRoot.getName().getPath() + "/" + convertUrlToPathName( sourceRoot.getName().getFriendlyURI() ) + "_" + datePostFix;

        return backupName;
    }

    private static String convertUrlToPathName( String path )
    {
        path = path.replace( "/", "_" );
        path = path.replace( ":", "_" );
        return StringUtils.substringAfter( path, "@" );
    }


}
