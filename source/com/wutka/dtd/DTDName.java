package com.wutka.dtd;

import java.io.*;

/** Represents a named item in the DTD
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDName extends DTDItem
{
    public String value;

    public DTDName()
    {
    }

    public DTDName(String aValue)
    {
        value = aValue;
    }

/** Writes out the value of this name */
    public void write(PrintWriter out)
        throws IOException
    {
        out.print(value);
        cardinal.write(out);
    }
}
