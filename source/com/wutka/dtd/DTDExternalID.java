package com.wutka.dtd;

import java.io.*;

/** Represents an external ID in an entity declaration
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public abstract class DTDExternalID
{
    public String system;

    public DTDExternalID()
    {
    }

/** Writes out a declaration for this external ID */
    public abstract void write(PrintWriter out)
        throws IOException;
}
