package com.wutka.dtd;

import java.io.*;

/** Represents a comment in the DTD
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDComment implements DTDOutput
{
/** The comment text */
    public String text;

    public DTDComment()
    {
    }

    public DTDComment(String theText)
    {
        text = theText;
    }

    public void write(PrintWriter out)
        throws IOException
    {
        out.print("<!-- ");
        out.print(text);
        out.println("-->");
    }
}
