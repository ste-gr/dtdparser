package com.wutka.dtd;

import java.io.*;

/** Represents the ANY keyword in an Element's content spec
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDAny extends DTDItem
{
    public DTDAny()
    {
    }

/** Writes "ANY" to a print writer */
    public void write(PrintWriter out)
        throws IOException
    {
        out.print("ANY");
        cardinal.write(out);
    }
}
