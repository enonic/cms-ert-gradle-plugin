package com.enonic.tools.ert.selector;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: Dec 3, 2010
 * Time: 11:58:01 AM
 */
public enum FileNamePattern
{
    NORMAL_FILES( Pattern.compile( ".*" ) ),
    XSLT_FILES( Pattern.compile( ".*\\.xsl" ) ),
    JS_FILES( Pattern.compile( ".*\\.js" ) ),
    CSS_FILES( Pattern.compile( ".*\\.css" ) ),
    XML_FILES( Pattern.compile( ".*\\.xml" ) ),
    HIDDEN_FILES( Pattern.compile( "\\..*" ) ),
    SVN_FILES( Pattern.compile( "\\.svn" ) );

    public Pattern pattern;

    FileNamePattern( Pattern pattern )
    {
        this.pattern = pattern;
    }
}
