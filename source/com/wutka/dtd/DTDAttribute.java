package com.wutka.dtd;

import java.io.*;

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

/** Writes this attribute to an output stream */
    public void write(PrintWriter out)
        throws IOException
    {
        out.print(name+" ");
        if (type instanceof String)
        {
            out.print(type);
        }
        else if (type instanceof DTDEnumeration)
        {
            DTDEnumeration dtdEnum = (DTDEnumeration) type;
            dtdEnum.write(out);
        }
        else if (type instanceof DTDNotationList)
        {
            DTDNotationList dtdnl = (DTDNotationList) type;
            dtdnl.write(out);
        }

        if (decl != null)
        {
            decl.write(out);
        }

        if (defaultValue != null)
        {
            out.print(" \"");
            out.print(defaultValue);
            out.print("\"");
        }
        //out.println(">");                            Bug!
    }
}
