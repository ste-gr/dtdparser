package com.wutka.dtd;

import java.util.*;
import java.io.*;

/** Represents an element defined with the ELEMENT DTD tag
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDElement
{
/** The name of the element */
    public String name;

/** The element's attributes */
    public Hashtable attributes;

/** The element's content */
    public DTDItem content;

    public DTDElement()
    {
        attributes = new Hashtable();
    }

    public DTDElement(String aName)
    {
        name = aName;

        attributes = new Hashtable();
    }

/** Writes out an element declaration and an attlist declaration (if necessary)
    for this element */
    public void write(PrintWriter out)
        throws IOException
    {
        out.print("<!ELEMENT ");
        out.print(name);
        out.print(" ");
        if (content != null)
        {
            content.write(out);
        }
        else
        {
            out.print("ANY");
        }
        out.println(">");
        out.println();

        if (attributes.size() > 0)
        {
            out.print("<!ATTLIST ");
            out.println(name);

            Enumeration e = attributes.elements();

            while (e.hasMoreElements())
            {
                out.print("           ");
                DTDAttribute attr = (DTDAttribute) e.nextElement();
                attr.write(out);
                out.println();
            }
            out.println();
        }
    }
}
