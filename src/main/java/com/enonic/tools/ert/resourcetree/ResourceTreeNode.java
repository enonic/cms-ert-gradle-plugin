package com.enonic.tools.ert.resourcetree;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileType;

import com.enonic.tools.ert.DiffDetailLevel;


/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Nov 1, 2010
 * Time: 8:46:37 PM
 */
public class ResourceTreeNode
{
    private Long timestamp;

    private Long size;

    byte[] md5Hash;

    SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss z" );

    public ResourceTreeNode( FileObject fileObject, DiffDetailLevel detailLevel )
        throws Exception
    {
        switch ( detailLevel )
        {
            case TIMESTAMP:
            {
                this.timestamp = fileObject.getContent().getLastModifiedTime();
                break;
            }

            case SIZE:
            {
                this.timestamp = fileObject.getContent().getLastModifiedTime();

                if ( fileObject.getType() == FileType.FILE )
                {
                    this.size = fileObject.getContent().getSize();
                }
                break;
            }

            case CONTENT:
            {
                this.timestamp = fileObject.getContent().getLastModifiedTime();

                if ( fileObject.getType() == FileType.FILE )
                {
                    this.size = fileObject.getContent().getSize();

                    MessageDigest md = MessageDigest.getInstance( "MD5" );
                    InputStream is = fileObject.getContent().getInputStream();
                    try
                    {
                        is = new DigestInputStream( is, md );
                        is.read();
                    }
                    finally
                    {
                        is.close();
                    }
                    byte[] digest = md.digest();

                    this.md5Hash = digest;
                }
                break;
            }
        }
    }

    public String toString()
    {
        // return path + name + " (" + timestamp + ")";
        StringBuffer buf = new StringBuffer();

        if ( timestamp != null )
        {
            buf.append( " (" + timestamp + ")" );
        }

        if ( size != null )
        {
            String sizeKb = size > 0 ? size / 1024 + "kb" : "";

            buf.append( " size: " + sizeKb );
        }

        if ( md5Hash != null && md5Hash.length > 0 )
        {
            buf.append( ", md5Hash : created" );
        }

        return buf.toString();
    }

}
