package com.wutka.dtd;

import java.io.*;

/** Represents the EMPTY keyword in an Element's content spec
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDEmpty extends DTDItem
{
    public DTDEmpty()
    {
    }

/** Writes out the keyword "EMPTY" */
    public void write(PrintWriter out)
        throws IOException
    {
        out.print("EMPTY");
        cardinal.write(out);
    }
}
