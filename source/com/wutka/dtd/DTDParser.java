package com.wutka.dtd;

import java.util.*;
import java.io.*;

/** Parses a DTD file and returns a DTD object
 *
 * @author Mark Wutka
 * @version $Revision$ $Date$ by $Author$
 */
public class DTDParser
{
    protected Scanner scanner;
    protected DTD dtd;

/** Creates a parser that will read from the specified Reader object */
    public DTDParser(Reader in)
    {
        scanner = new Scanner(in, false);
        dtd = new DTD();
    }

/** Creates a parser that will read from the specified Reader object
 * @param in The input stream to read
 * @param trace True if the parser should print out tokens as it reads them
 *  (used for debugging the parser)
 */
    public DTDParser(Reader in, boolean trace)
    {
        scanner = new Scanner(in, trace);
        dtd = new DTD();
    }

    public DTD parse()
        throws IOException
    {
        Token token;

        for (;;)
        {
            token = scanner.peek();

            if (token.type == Scanner.EOF) break;

            parseTopLevelElement();
        }

        return dtd;
    }

    protected void parseTopLevelElement()
        throws IOException
    {
        Token token = scanner.get();

// Is <? xxx ?> even valid in a DTD?  I'll ignore it just in case it's there
        if (token.type == Scanner.LTQUES)
        {
            for (;;)
            {
                skipUntil(Scanner.QUES);
                token = scanner.peek();
                if (token.type == Scanner.GT)
                {
                    scanner.get();
                    break;
                }
            }

            return;
        }
        else if (token.type == Scanner.CONDITIONAL)
        {
            token = expect(Scanner.IDENTIFIER);

            if (token.value.equals("IGNORE"))
            {
                scanner.skipConditional();
            }
            else if (!token.value.equals("INCLUDE"))
            {
                throw new IOException(
                    "Invalid token in conditional: "+token.value);
            }
        }
        else if (token.type == Scanner.ENDCONDITIONAL)
        {
            // Don't need to do anything for this token
        }
        else if (token.type == Scanner.LTBANG)
        {

            token = expect(Scanner.IDENTIFIER);

            if (token.value.equals("ELEMENT"))
            {
                parseElement();
            }
            else if (token.value.equals("ATTLIST"))
            {
                parseAttlist();
            }
            else if (token.value.equals("ENTITY"))
            {
                parseEntity();
            }
            else if (token.value.equals("NOTATION"))
            {
                parseNotation();
            }
            else
            {
                skipUntil(Scanner.GT);
            }
        }
    }

    protected void skipUntil(TokenType stopToken)
        throws IOException
    {
        Token token = scanner.get();

        while (token.type != stopToken)
        {
            token = scanner.get();
        }
    }

    protected Token expect(TokenType expected)
        throws IOException
    {
        Token token = scanner.get();

        if (token.type != expected)
        {
            if (token.value == null)
            {
                throw new IOException("Expected "+expected.name+
                    " instead of "+token.type.name);
            }
            else
            {
                throw new IOException("Expected "+expected.name+
                    " instead of "+ token.type.name+"("+token.value+")");
            }
        }

        return token;
    }

    protected void parseElement()
        throws IOException
    {
        Token name = expect(Scanner.IDENTIFIER);

        DTDElement element = (DTDElement) dtd.elements.get(name.value);

        if (element == null)
        {
            element = new DTDElement(name.value);
            dtd.elements.put(element.name, element);
        }

        parseContentSpec(scanner, element);

        expect(Scanner.GT);
    }

    protected void parseContentSpec(Scanner scanner, DTDElement element)
        throws IOException
    {
        Token token = scanner.get();

        if (token.type == Scanner.IDENTIFIER)
        {
            if (token.value.equals("EMPTY"))
            {
                element.content = new DTDEmpty();
            }
            else if (token.value.equals("ANY"))
            {
                element.content = new DTDAny();
            }
            else
            {
                throw new IOException(
                    "Invalid token in entity content spec "+
                        token.value);
            }
        }
        else if (token.type == Scanner.LPAREN)
        {
            token = scanner.peek();

            if (token.type == Scanner.IDENTIFIER)
            {
                if (token.value.equals("#PCDATA"))
                {
                    parseMixed(element);
                }
                else
                {
                    parseChildren(element);
                }
            }
            else if (token.type == Scanner.LPAREN)
            {
                parseChildren(element);
            }
        }
    }

    protected void parseMixed(DTDElement element)
        throws IOException
    {
        DTDMixed mixed = new DTDMixed();

        mixed.add(new DTDPCData());

        scanner.get();

        element.content = mixed;

        for (;;)
        {
            Token token = scanner.get();

            if (token.type == Scanner.RPAREN)
            {
                token = scanner.peek();
                
                if (token.type == Scanner.ASTERISK)
                {
                    scanner.get();
                    mixed.cardinal = DTDCardinal.ZEROMANY;
                }
                else
                {
                    mixed.cardinal = DTDCardinal.NONE;
                }

                return;
            }
            else if (token.type == Scanner.PIPE)
            {
                token = scanner.get();

                mixed.add(new DTDName(token.value));
            }
            else
            {
                throw new IOException("Invalid token in Mixed content type: "+
                    token.type.name);
            }
        }
    }

    protected void parseChildren(DTDElement element)
        throws IOException
    {
        DTDContainer choiceSeq = parseChoiceSequence();

        Token token = scanner.peek();

        choiceSeq.cardinal = parseCardinality();

        if (token.type == Scanner.QUES)
        {
            choiceSeq.cardinal = DTDCardinal.OPTIONAL;
        }
        else if (token.type == Scanner.ASTERISK)
        {
            choiceSeq.cardinal = DTDCardinal.ZEROMANY;
        }
        else if (token.type == Scanner.PLUS)
        {
            choiceSeq.cardinal = DTDCardinal.ONEMANY;
        }
        else
        {
            choiceSeq.cardinal = DTDCardinal.NONE;
        }

        element.content = choiceSeq;
    }

    protected DTDContainer parseChoiceSequence()
        throws IOException
    {
        TokenType separator = null;

        DTDContainer cs = null;

        for (;;)
        {
            DTDItem item = parseCP();

            Token token = scanner.get();

            if ((token.type == Scanner.PIPE) ||
                (token.type == Scanner.COMMA))
            {
                if ((separator != null) && (separator != token.type))
                {
                    throw new IOException(
                        "Can't mix separators in a choice/sequence");
                }
                separator = token.type;

                if (cs == null)
                {
                    if (token.type == Scanner.PIPE)
                    {
                        cs = new DTDChoice();
                    }
                    else
                    {
                        cs = new DTDSequence();
                    }
                }
                cs.add(item);
            }
            else if (token.type == Scanner.RPAREN)
            {
                if (cs == null)
                {
                    cs = new DTDChoice();
                }
                cs.add(item);
                return cs;
            }
            else
            {
                throw new IOException("Found invalid token in sequence: "+
                    token.type.name);
            }
        }
    }

    protected DTDItem parseCP()
        throws IOException
    {
        Token token = scanner.get();

        DTDItem item = null;

        if (token.type == Scanner.IDENTIFIER)
        {
            item = new DTDName(token.value);
        }
        else if (token.type == Scanner.LPAREN)
        {
            item = parseChoiceSequence();
        }
        else
        {
            throw new IOException("Found invalid token in sequence: "+
                token.type.name);
        }

        item.cardinal = parseCardinality();

        return item;
    }

    protected DTDCardinal parseCardinality()
        throws IOException
    {
        Token token = scanner.peek();           

        if (token.type == Scanner.QUES)
        {
            scanner.get();
            return DTDCardinal.OPTIONAL;
        }
        else if (token.type == Scanner.ASTERISK)
        {
            scanner.get();
            return DTDCardinal.ZEROMANY;
        }
        else if (token.type == Scanner.PLUS)
        {
            scanner.get();
            return DTDCardinal.ONEMANY;
        }
        else
        {
            return DTDCardinal.NONE;
        }
    }

    protected void parseAttlist()
        throws IOException
    {
        Token token = expect(Scanner.IDENTIFIER);

        DTDElement element = (DTDElement) dtd.elements.get(token.value);

        if (element == null)
        {
            element = new DTDElement(token.value);
            dtd.elements.put(token.value, element);
        }

        token = scanner.peek();

        while (token.type != Scanner.GT)
        {
            parseAttdef(scanner, element);
            token = scanner.peek();
        }
    }

    protected void parseAttdef(Scanner scanner, DTDElement element)
        throws IOException
    {
        Token token = expect(Scanner.IDENTIFIER);

        DTDAttribute attr = new DTDAttribute(token.value);

        element.attributes.put(token.value, attr);

        token = scanner.get();

        if (token.type == Scanner.IDENTIFIER)
        {
            if (token.value.equals("NOTATION"))
            {
                attr.type = parseNotationList();
            }
            else
            {
                attr.type = token.value;
            }
        }
        else if (token.type == Scanner.LPAREN)
        {
            attr.type = parseEnumeration();
        }

        token = scanner.peek();

        if (token.type == Scanner.IDENTIFIER)
        {
            scanner.get();
            if (token.value.equals("#FIXED"))
            {
                attr.decl = DTDDecl.FIXED;

                token = scanner.get();
                attr.defaultValue = token.value;
            }
            else if (token.value.equals("#REQUIRED"))
            {
                attr.decl = DTDDecl.REQUIRED;
            }
            else if (token.value.equals("#IMPLIED"))
            {
                attr.decl = DTDDecl.IMPLIED;
            }
            else
            {
                throw new IOException(
                    "Invalid token in attribute declaration: "+
                    token.value);
            }
        }
        else if (token.type == Scanner.STRING)
        {
            scanner.get();
            attr.decl = DTDDecl.VALUE;
            attr.defaultValue = token.value;
        }
    }

    protected DTDNotationList parseNotationList()
        throws IOException
    {
        DTDNotationList notation = new DTDNotationList();

        for (;;)
        {
            Token token = scanner.get();

            if (token.type != Scanner.IDENTIFIER)
            {
                throw new IOException("Invalid token in notation: "+
                    token.type.name);
            }

            notation.add(token.value);

            token = scanner.peek();

            if (token.type == Scanner.RPAREN)
            {
                scanner.get();
                return notation;
            }
            else if (token.type != Scanner.PIPE)
            {
                throw new IOException("Invalid token in notation: "+
                    token.type.name);
            }
            scanner.get(); // eat the pipe
        }
    }

    protected DTDEnumeration parseEnumeration()
        throws IOException
    {
        DTDEnumeration enumeration = new DTDEnumeration();

        for (;;)
        {
            Token token = scanner.get();

            if ((token.type != Scanner.IDENTIFIER) &&
                (token.type != Scanner.NMTOKEN))
            {
                throw new IOException("Invalid token in enumeration: "+
                    token.type.name);
            }

            enumeration.add(token.value);

            token = scanner.peek();

            if (token.type == Scanner.RPAREN)
            {
                scanner.get();
                return enumeration;
            }
            else if (token.type != Scanner.PIPE)
            {
                throw new IOException("Invalid token in enumeration: "+
                    token.type.name);
            }
            scanner.get(); // eat the pipe
        }
    }

    protected void parseEntity()
        throws IOException
    {
        boolean isParsed = false;

        Token name = scanner.get();

        if (name.type == Scanner.PERCENT)
        {
            isParsed = true;
            name = expect(Scanner.IDENTIFIER);
        }
        else if (name.type != Scanner.IDENTIFIER)
        {
            throw new IOException("Invalid entity declaration");
        }

        DTDEntity entity = (DTDEntity) dtd.entities.get(name.value);

        if (entity == null)
        {
            entity = new DTDEntity(name.value);
            dtd.entities.put(entity.name, entity);
        }

        entity.isParsed = isParsed;

        parseEntityDef(entity);

        if (entity.isParsed && (entity.value != null))
        {
            scanner.addEntity(entity.name, entity.value);
        }
    }

    protected void parseEntityDef(DTDEntity entity)
        throws IOException
    {
        Token token = scanner.get();

        if (token.type == Scanner.STRING)
        {
            entity.value = token.value;
        }
        else if (token.type == Scanner.IDENTIFIER)
        {
            if (token.value.equals("SYSTEM"))
            {
                DTDSystem sys = new DTDSystem();
                token = expect(Scanner.STRING);

                sys.system = token.value;
                entity.externalID = sys;
            }
            else if (token.value.equals("PUBLIC"))
            {
                DTDPublic pub = new DTDPublic();

                token = expect(Scanner.STRING);
                pub.pub = token.value;
                token = expect(Scanner.STRING);
                pub.system = token.value;
            }
            else
            {
                throw new IOException("Invalid External ID specification");
            }

            if (entity.isParsed)
            {
                token = scanner.peek();
                if (token.type == Scanner.IDENTIFIER)
                {
                    if (!token.value.equals("NDATA"))
                    {
                        throw new IOException(
                            "Invalid NData declaration");
                    }
                    token = expect(Scanner.IDENTIFIER);
                    entity.ndata = token.value;
                }
            }
        }
        else
        {
            throw new IOException("Invalid entity definition");
        }

        expect(Scanner.GT);
    }

    protected void parseNotation()
        throws java.io.IOException
    {
        DTDNotation notation = new DTDNotation();

        Token token = expect(Scanner.IDENTIFIER);

        notation.name = token.value;

        dtd.notations.put(notation.name, notation);

        token = expect(Scanner.IDENTIFIER);

        if (token.value.equals("SYSTEM"))
        {
            DTDSystem sys = new DTDSystem();
            token = expect(Scanner.STRING);

            sys.system = token.value;
            notation.externalID = sys;
        }
        else if (token.value.equals("PUBLIC"))
        {
            DTDPublic pub = new DTDPublic();
            token = expect(Scanner.STRING);

            pub.pub = token.value;
            pub.system = null;

// For <!NOTATION>, you can have PUBLIC PubidLiteral without
// a SystemLiteral
            token = scanner.peek();
            if (token.type == Scanner.STRING)
            {
                token = scanner.get();
                pub.system = token.value;
            }

            notation.externalID = pub;
        }
        expect(Scanner.GT);
    }
}
