package com.wutka.dtd;

/** Represents the possible values for an attribute declaration
 *
 * @author Mark Wutka
 * @version 1.0 06/28/2000
 */
public class DTDDecl
{
    public static final DTDDecl FIXED = new DTDDecl(0, "FIXED");
    public static final DTDDecl REQUIRED = new DTDDecl(1, "REQUIRED");
    public static final DTDDecl IMPLIED = new DTDDecl(2, "IMPLIED");
    public static final DTDDecl VALUE = new DTDDecl(3, "VALUE");

    public int type;
    public String name;

    public DTDDecl(int aType, String aName)
    {
            type = aType;
            name = aName;
    }

    public boolean equals(Object ob)
    {
        if (ob == this) return true;
        if (!(ob instanceof DTDDecl)) return false;

        DTDDecl other = (DTDDecl) ob;
        if (other.type == type) return true;
        return false;
    }
}
