package com.wutka.dtd;

import java.util.*;

/** Represents an element defined with the ELEMENT DTD tag
 *
 * @author Mark Wutka
 * @version 1.0 06/28/2000
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
}
