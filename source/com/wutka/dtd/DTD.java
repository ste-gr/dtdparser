package com.wutka.dtd;

import java.util.*;
import java.io.*;

/** Represents a parsed Document Type Definition
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */


public class DTD implements DTDOutput
{
/** Contains all the elements defined in the DTD */
    public Hashtable elements;

/** Contains all the entities defined in the DTD */
    public Hashtable entities;

/** Contains all the notations defined in the DTD */
    public Hashtable notations;

/** Contains all the items defined in the DTD in their original order */
    public Vector items;

/** Contains the element that is most likely the root element  or null
    if the root element can't be determined.  */
    public DTDElement rootElement;

/** Creates a new DTD */
    public DTD()
    {
        elements = new Hashtable();
        entities = new Hashtable();
        notations = new Hashtable();
        items = new Vector();
    }

/** Writes the DTD to an output writer in standard DTD format (the format
 *  the parser normally reads).
 *  @param outWriter The writer where the DTD will be written
 */
    public void write(PrintWriter outWriter)
        throws IOException
    {
        Enumeration e = items.elements();

        while (e.hasMoreElements())
        {
            DTDOutput item = (DTDOutput) e.nextElement();

            item.write(outWriter);
        }
    }
}
