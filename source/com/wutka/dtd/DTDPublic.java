package com.wutka.dtd;

import java.io.*;

/** Represents an external Public ID in an entity declaration
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */

public class DTDPublic extends DTDExternalID
{
    public String pub;

    public DTDPublic()
    {
    }

/** Writes out a public external ID declaration */
    public void write(PrintWriter out)
        throws IOException
    {
        out.print("PUBLIC \"");
        out.print(pub);
        out.print("\"");
        if (system != null)
        {
            out.print(" \"");
            out.print(system);
            out.print("\"");
        }
    }
}
