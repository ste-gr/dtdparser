package com.wutka.dtd;

import java.io.*;

/** Represents a Notation defined in a DTD
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDNotation
{
    public String name;
    public DTDExternalID externalID;

    public DTDNotation()
    {
    }

    public DTDNotation(String aName)
    {
        name = aName;
    }

/** Writes out a declaration for this notation */
    public void write(PrintWriter out)
        throws IOException
    {
        out.print("<!NOTATION ");
        out.print(name);
        out.print(" ");
        externalID.write(out);
        out.println(">");
    }
}
