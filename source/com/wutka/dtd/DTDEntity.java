package com.wutka.dtd;

/** Represents an Entity defined in a DTD
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDEntity
{
    public String name;
    public boolean isParsed;
    public String value;
    public DTDExternalID externalID;
    public String ndata;

    public DTDEntity()
    {
    }

    public DTDEntity(String aName)
    {
        name = aName;
    }
}
