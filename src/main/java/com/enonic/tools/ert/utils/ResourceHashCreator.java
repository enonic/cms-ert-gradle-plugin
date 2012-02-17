package com.enonic.tools.ert.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileType;

import com.google.common.io.ByteStreams;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 2:42:39 PM
 */
public class ResourceHashCreator
{
    private final static char[] HEX = "0123456789abcdef".toCharArray();

    public static String createHash( FileObject fileObject )
        throws Exception
    {
        if ( fileObject.getType() == FileType.FILE )
        {
            MessageDigest md = MessageDigest.getInstance( "SHA" );

            InputStream in = fileObject.getContent().getInputStream();
            OutputStream out = new OutputStream()
            {
                @Override
                public void write( final int b )
                    throws IOException
                {
                    // Do nothing
                }
            };

            final DigestOutputStream digestOut = new DigestOutputStream( out, md );

            try
            {
                ByteStreams.copy( in, digestOut );
            }
            finally
            {
                digestOut.close();
                in.close();
            }

            byte[] digest = md.digest();

            return createValueString( digest );
        }

        return null;
    }

    private static String createValueString( final byte[] key )
    {
        char[] buffer = new char[key.length * 2];
        for ( int i = 0; i < key.length; i++ )
        {
            buffer[2 * i] = HEX[( key[i] >> 4 ) & 0x0f];
            buffer[2 * i + 1] = HEX[key[i] & 0x0f];
        }

        return new String( buffer );
    }
}
