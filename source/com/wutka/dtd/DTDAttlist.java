package com.wutka.dtd;

import java.util.*;
import java.io.*;

/** Represents an ATTLIST declaration in the DTD. Although attributes are
 *  associated with elements, the ATTLIST is here to allow the DTD object
 *  to write out the DTD in roughly the original form. Because the ATTLIST
 *  may appear somewhere other than immediately after the ELEMENT, this object
 *  is used to keep track of where it is.
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDAttlist implements DTDOutput
{
/** The name of the element */
    public String name;

/** The attlist's attributes */
    public Vector attributes;

    public DTDAttlist()
    {
        attributes = new Vector();
    }

    public DTDAttlist(String aName)
    {
        name = aName;

        attributes = new Vector();
    }

/** Writes out an ATTLIST declaration */
    public void write(PrintWriter out)
        throws IOException
    {
        out.print("<!ATTLIST ");
        out.println(name);

        Iterator itr = attributes.iterator();

        while (itr.hasNext())
        {
            out.print("           ");
            DTDAttribute attr = (DTDAttribute) itr.next();
            attr.write(out);
            if (itr.hasNext())
            {
                out.println();
            }
            else
            {
                out.println(">");
            }
        }
    }
}
