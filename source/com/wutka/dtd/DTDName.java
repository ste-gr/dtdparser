package com.wutka.dtd;

/** Represents a named item in the DTD
 *
 * @author Mark Wutka
 * @version 1.0 06/28/2000
 */
public class DTDName extends DTDItem
{
    public String value;

    public DTDName()
    {
    }

    public DTDName(String aValue)
    {
        value = aValue;
    }
}
