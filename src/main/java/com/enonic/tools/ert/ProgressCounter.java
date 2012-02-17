package com.enonic.tools.ert;

/**
 * Created by IntelliJ IDEA.
 * User: rmh
 * Date: 12/29/10
 * Time: 12:52 PM
 */
public class ProgressCounter
{
    private int countThreshold = 100;

    private int totalWorkedElements = 0;

    private int currentWorkedElements = 0;

    private Integer totalNumberOfElements;

    private String operation = "Processed";

    private boolean showAsPercent = false;

    public ProgressCounter( int countThreshold, Integer totalNumberOfElements )
    {
        this.countThreshold = countThreshold;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public ProgressCounter()
    {
    }

    public ProgressCounter( int countThreshold )
    {
        this.countThreshold = countThreshold;
    }

    public void addElement()
    {
        totalWorkedElements++;
        currentWorkedElements++;

        if ( currentWorkedElements >= countThreshold )
        {
            if ( showAsPercent )
            {
                printProgressPercent();
            }
            else
            {
                printProgress();
            }

            currentWorkedElements = 0;
        }

    }

    private void printProgress()
    {
        StringBuffer buf = new StringBuffer();

        buf.append( operation + ": " + totalWorkedElements );

        if ( totalNumberOfElements != null )
        {
            buf.append( " of " + totalNumberOfElements );
        }

        System.out.println( buf.toString() );
    }

    private void printProgressPercent()
    {
        StringBuffer buf = new StringBuffer();

        int percentComplete = totalWorkedElements * 100 / totalNumberOfElements;

        buf.append( operation + ": " + percentComplete + "%" );

        System.out.println( buf.toString() );
    }

    public void setOperation( String operation )
    {
        this.operation = operation;
    }

    public void setShowAsPercent( boolean showAsPercent )
    {
        this.showAsPercent = showAsPercent;
    }
}
