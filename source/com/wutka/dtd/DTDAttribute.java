package com.wutka.dtd;

/** Represents a DTD Attribute in an ATTLIST declaration
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */

public class DTDAttribute
{
/** The name of the attribute */
    public String name;

/** The type of the attribute (either String, DTDEnumeration or
    DTDNotationList) */
    public Object type;

/** The attribute's declaration (required, fixed, implied) */
    public DTDDecl decl;

/** The attribute's default value (null if not declared) */
    public String defaultValue;

    public DTDAttribute()
    {
    }

    public DTDAttribute(String aName)
    {
        name = aName;
    }
}
