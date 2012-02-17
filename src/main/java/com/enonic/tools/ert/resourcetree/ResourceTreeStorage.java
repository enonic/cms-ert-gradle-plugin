package com.enonic.tools.ert.resourcetree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 4, 2010
 * Time: 11:09:43 PM
 */
public class ResourceTreeStorage
{
    private static final String DELIMITER = "##_##";

    public static ResourceTree getResourceTree( FileContent resourceTreeFile )
        throws Exception
    {
        ResourceTree resourceTree = new ResourceTree();

        InputStream is = resourceTreeFile.getInputStream();

        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader( new InputStreamReader( is ) );

            String line = null;

            while ( ( line = reader.readLine() ) != null )
            {
                StringTokenizer tokenizer = new StringTokenizer( line, DELIMITER );

                String key = tokenizer.nextToken();

                String value = null;

                if ( tokenizer.hasMoreTokens() )
                {
                    value = tokenizer.nextToken();
                }

                String decodedString = new String( decodeKey( key ) );

                resourceTree.addNode( decodedString, value );
            }
        }
        catch ( FileNotFoundException ex )
        {
            ex.printStackTrace();
        }
        catch ( IOException ex )
        {
            ex.printStackTrace();
        }
        finally
        {
            //Close the BufferedReader
            try
            {
                if ( reader != null )
                {
                    reader.close();
                }
            }
            catch ( IOException ex )
            {
                ex.printStackTrace();
            }
        }

        return resourceTree;
    }

    public static FileObject storeResourceTree( FileObject root, String fileName, ResourceTree resourceTree )
        throws Exception
    {
        FileObject file = root.resolveFile( fileName );

        if ( !file.exists() )
        {
            file.createFile();
        }

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bout.write( fileName.getBytes() );

        final OutputStream fileOutputStream = file.getContent().getOutputStream();

        BufferedWriter ps = new BufferedWriter( new OutputStreamWriter( fileOutputStream ) );

        try
        {
            final Map<String, String> resourceTreeMap = resourceTree.getResourceNodeMap();

            for ( String key : resourceTreeMap.keySet() )
            {
                String encodedString = encodeString( key );

                String value = resourceTreeMap.get( key ) == null ? "" : resourceTreeMap.get( key );

                String line = encodedString + DELIMITER + value;

                ps.write( line );
                ps.newLine();

            }
        }
        catch ( IOException e )
        {
            System.out.println( "Could not write to file" );
        }
        finally
        {
            System.out.println( "File: " + fileName + " saved successfully" );
            ps.close();
            fileOutputStream.close();
        }

        return file;
    }

    private static String encodeString( String key )
    {
        return Base64.encode( key.getBytes() );
    }

    private static byte[] decodeKey( String key )
    {
        return Base64.decode( key );
    }

    public static boolean deleteResourceTree( FileObject root, String fileName )
        throws Exception
    {
        FileObject file = root.resolveFile( fileName );

        if ( !file.exists() )
        {

            System.out.println( "File doesnt exist, nothing to delete" );
            return false;
        }

        return file.delete();
    }

}
