package com.wutka.dtd;

import java.util.*;

/** Represents a notation declaration for an attribute
 *
 * @author Mark Wutka
 * @version 1.0 06/28/2000
 */
public class DTDNotationList
{
    protected Vector items;

/** Creates a new notation */
    public DTDNotationList()
    {
        items = new Vector();
    }

/** Adds a item to the list of notation values */
    public void add(String item)
    {
        items.addElement(item);
    }

/** Removes an item from the list of notation values */
    public void remove(String item)
    {
        items.removeElement(item);
    }

/** Returns the list of notation values as an array */
    public String[] getItems()
    {
        String[] retval = new String[items.size()];
        items.copyInto(retval);

        return retval;
    }

/** Returns the list of notation values as a vector */
    public Vector getItemsVec()
    {
        return items;
    }
}
