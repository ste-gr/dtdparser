package com.wutka.dtd;

/** Represents a Notation defined in a DTD
 *
 * @author Mark Wutka
 * @version 1.0 06/28/2000
 */
public class DTDNotation
{
    public String name;
    public DTDExternalID externalID;

    public DTDNotation()
    {
    }

    public DTDNotation(String aName)
    {
        name = aName;
    }
}
