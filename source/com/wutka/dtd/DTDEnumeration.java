package com.wutka.dtd;

import java.util.*;

/** Represents an enumeration of attribute values
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDEnumeration
{
    protected Vector items;

/** Creates a new enumeration */
    public DTDEnumeration()
    {
        items = new Vector();
    }

/** Adds a new value to the list of values */
    public void add(String item)
    {
        items.addElement(item);
    }

/** Removes a value from the list of values */
    public void remove(String item)
    {
        items.removeElement(item);
    }

/** Returns the values as an array */
    public String[] getItems()
    {
        String[] retval = new String[items.size()];
        items.copyInto(retval);

        return retval;
    }

/** Returns the values as a vector (not a clone!) */
    public Vector getItemsVec()
    {
        return items;
    }
}
