package com.wutka.dtd;

import java.util.*;

/** Represents a parsed Document Type Definition
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */


public class DTD
{
/** Contains all the elements defined in the DTD */
    public Hashtable elements;

/** Contains all the entities defined in the DTD */
    public Hashtable entities;

/** Contains all the notations defined in the DTD */
    public Hashtable notations;

/** Contains the element that is most likely the root element  or null
    if the root element can't be determined.  */
    public DTDElement rootElement;

/** Creates a new DTD */
    public DTD()
    {
        elements = new Hashtable();
        entities = new Hashtable();
        notations = new Hashtable();
    }
}
