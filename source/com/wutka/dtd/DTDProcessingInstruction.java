package com.wutka.dtd;

import java.io.*;

/** Represents a processing instruction in the DTD
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDProcessingInstruction implements DTDOutput
{
/** The processing instruction text */
    public String text;

    public DTDProcessingInstruction()
    {
    }

    public DTDProcessingInstruction(String theText)
    {
        text = theText;
    }

    public void write(PrintWriter out)
        throws IOException
    {
        out.print("<? ");
        out.print(text);
        out.println("?>");
    }
}
