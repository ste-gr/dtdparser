package com.wutka.dtd;

public class DTDParseException extends java.io.IOException
{
    public int lineNumber;
    public int column;

    public DTDParseException()
    {
        lineNumber=-1;
        column=-1;
    }

    public DTDParseException(String message)
    {
        super(message);
        lineNumber=-1;
        column=-1;
    }

    public DTDParseException(String message, int line, int col)
    {
        super("At line "+line+", column "+col+": "+message);
        lineNumber = line;
        column = col;
    }

    public int getLineNumber() { return lineNumber; }
    public int getColumn() { return column; }
}
