package com.wutka.dtd;

import java.util.*;

/** Represents a parsed Document Type Definition
 *
 * @author Mark Wutka
 * @version 1.0 06/28/2000
 */


public class DTD
{
/** Contains all the elements defined in the DTD */
    public Hashtable elements;

/** Contains all the entities defined in the DTD */
    public Hashtable entities;

/** Contains all the notations defined in the DTD */
    public Hashtable notations;

/** Creates a new DTD */
    public DTD()
    {
        elements = new Hashtable();
        entities = new Hashtable();
        notations = new Hashtable();
    }
}
