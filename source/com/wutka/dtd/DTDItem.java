package com.wutka.dtd;

/** Represents any item in the DTD
 *
 * @author Mark Wutka
 * @version 1.0 06/28/2000
 */
public abstract class DTDItem
{
/** Indicates how often the item may occur */
    public DTDCardinal cardinal;

    public DTDItem()
    {
        cardinal = DTDCardinal.NONE;
    }

    public DTDItem(DTDCardinal aCardinal)
    {
        cardinal = aCardinal;
    }
}
