package com.wutka.dtd;

import java.util.*;

/** Represents an item that may contain other items (such as a
 * DTDChoice or a DTDSequence)
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public abstract class DTDContainer extends DTDItem
{
    protected Vector items;

/** Creates a new DTDContainer */
    public DTDContainer()
    {
        items = new Vector();
    }

/** Adds an element to the container */
    public void add(DTDItem item)
    {
        items.addElement(item);
    }

/** Removes an element from the container */
    public void remove(DTDItem item)
    {
        items.removeElement(item);
    }

/** Returns the elements as a vector (not a clone!) */
    public Vector getItemsVec()
    {
        return items;
    }

/** Returns the elements as an array of items */
    public DTDItem[] getItems()
    {
        DTDItem[] retval = new DTDItem[items.size()];
        items.copyInto(retval);
        return retval;
    }
}
