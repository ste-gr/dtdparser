package com.wutka.dtd;

/** Represents the various cardinality values for a DTD item.
 * <bl>
 * <li>NONE indicates no cardinality</li>
 * <li>OPTIONAL indicates an optional value (specified by ?)</li>
 * <li>ZEROMANY indicates zero-to-many values (specified by *)</li>
 * <li>ONEMANY indicates an one-to-many values (specified by +)</li>
 * </bl>
 *
 * @author Mark Wutka
 * @version 1.0 06/28/2000
 */
public class DTDCardinal
{
/** Indicates no cardinality (implies a single object) */
    public static final DTDCardinal NONE = new DTDCardinal(0, "NONE");

/** Indicates that an item is optional (zero-to-one) */
    public static final DTDCardinal OPTIONAL = new DTDCardinal(1, "OPTIONAL");

/** Indicates that there can be zero-to-many occurrances of an item */
    public static final DTDCardinal ZEROMANY = new DTDCardinal(2, "ZEROMANY");

/** Indicates that there can be one-to-many occurrances of an item */
    public static final DTDCardinal ONEMANY = new DTDCardinal(3, "ONEMANY");

    public int type;
    public String name;

    public DTDCardinal(int aType, String aName)
    {
            type = aType;
            name = aName;
    }

    public boolean equals(Object ob)
    {
        if (ob == this) return true;
        if (!(ob instanceof DTDCardinal)) return false;

        DTDCardinal other = (DTDCardinal) ob;
        if (other.type == type) return true;
        return false;
    }
}
