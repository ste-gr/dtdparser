# 1.22 / YYYY-MM-DD

* Add full history to `CHANGELOG`
* Add maven-wrapper
* Convert from ant-build to maven-build
* CVS-Import from
  [DTDParser for Java](http://dtdparser.cvs.sourceforge.net/cvsroot/dtdparser)

# 1.21 / 2003-04-02

DTDParser is now available under a dual license. You can either use it under the
terms of LGPL as usual or you can use an Apache-style license. Using one license
doesn't obligate you to the terms of the other.

# 1.20 / 2002-10-01

Okay, I was wrong about one with with the `PCDATA` - it CAN have `*` after it if
it appears by itself. Thanks to Steen Lehmann for pointing this out. This is
fixed in version 1.20.

# 1.19 / 2002-07-31

Changed expansion of entities to take the first definition of the entity
instead of the most recent (required by XML specification)

Modified scanner to parse identifiers and nmtokens correctly - allowed
identifiers to begin with `:` or `_`.

Fixed parsing of `#PCDATA` to conform to XML specification. If `#PCDATA` appears
by itself (i.e. `(#PCDATA)` ), don't allow `*` after it. If `#PCDATA` appears at
the beginning of a list (i.e. `(#PCDATA|foo|bar|baz)` ), require '`*`' to
follow it.

# 1.18 / 2002-07-29

Oops! I had uncommented a `println` while testing version 1.17 and forgot to
recomment it. Also, the Ant build wasn't including the Main-Class attribute for
the DTDParser JAR file so you weren't able to to the `-jar` option. That is
fixed as well now.

# 1.17 / 2002-07-28

Dmitriy Kulakov pointed out that although the parser can include entity
definitions from files using a relative path, this doesn't work when using a
URL. I fixed the entity class to work properly with URLs - it works with both
absolute and relative URLs.

Also, to help with testing the URLs, I changed the Tokenize program to look for
"`://`" in the filename and if so, uses a URL instead of a File.

While parsing the `DOCBOOK` DTD, I found that the `ATTLIST` parsing was
forgetting to consume the `>` at the end of the list. At the top-level, the
parser was ignoring unexpected tokens so this error never showed up before. I
fixed the `ATTLIST` bug, then change the main parsing loop to report an error
when it hits an unexpected token. This may result in the parser reporting errors
that it never reported before, but these errors should really be errors.

Also, since the parser now reports unexpected tokens, I had to change the
scanner because it was returning unresolveable entities as identifiers. This
wasn't a problem before because the parser would ignore them, but now the
behavior should be correct (that is, you expand `%foobar;` and if there is no
`foobar`, the scanner just keeps going rather than returning `%foobar;` as an
identifier.

Finally, I played with the Ant build for a while to get it to generate the ZIP
and TGZ files with a root of `dtdparser-m.n` so you don't have to create a new
directory before unpacking the files. I used to package the files this way, but
after switching to Ant, I wasn't.

# 1.16 / 2002-07-19

I finally got around to fixing some of the outstanding bugs. Here is a list of
the changes I made:
* If encountering an empty list, choose a sequence as the default instead of a
  choice (as per XML DTD spec)
* When creating a list of objects matching a particular type, was adding the
  item type to the vector instead of the item itself. This may have been fixed
  in 1.15, but it doesn't look like I got it checked back into CVS correctly.
* Fixed infinite loop in case of unterminated string
* Fixed parsing of notation in `ATTLIST`
* The parser can parse both the `DOCBOOK` and the `FIXML` DTDs, which it had
  trouble with before.

Thank you to everyone who sent in bug reports and potential fixes. Hopefully the
library should be pretty stable now.

# 1.15 / 2001-11-12

Thanks to Elian Carsenat for sending a fix for `getElementsByType`, which was
just returning a vector of types instead of elements. The fix is available in
version 1.15.

# 1.14 / 2001-07-06

* Patches to fix parsing of `NDATA`, also added restriction of only having one
  element definition and ignoring subsequent definitions of an entity.
    * NDATA patch supplied by Noah Fike, element/entity patches identified by
      Jags Krishnamurthy.
* Added `build.xml` and `project.properties` for Ant build. The Ant build was
  taken from the Ant build for the JOX project, and was created by Jeffrey
  Wescott.

# 1.13 / 2000-09-18

Thanks to Paul Libbrecht for pointing out the few minor places where the DTD
Parser used JDK 1.2 features where there was a JDK 1.1 equivalent. The DTD
Parser should now be JDK 1.1 compliant.

Also thanks to Peter Kriegesmann for pointing out a bug in the
`DTD.getItemsByType`.

# 1.12 / 2000-09-01

I have updated the DTD parser to allow you to specify a File or a URL as opposed
to a reader. When the includes an entity with a relative path, the parser can
then use the file or URL to figure out the path for the included entity.

# 1.11 / 2000-08-16

Thanks again to Bob Withers for his updates to keep track of the filenames for
external entities that allows the parsing exceptions to tell you which file had
the error.

Bill La Forge also made a correction to make `DTDMixed` print the same way as
sequences and choices.

I also added `getItemsByType` to the DTD class that lets you fetch all the items
of a specific type.

# 1.10 / 2000-08-11

Added support for reading external entities(!). Added get/set methods to make
the DTD data model properties work like Java Bean properties. The original
public fields are still directly accessible to maintain backwards compatibility.

Thanks to Bob Withers of BEA for pointing out a bug in the INCLUDE handling and
for providing a fix.

# 1.7 / 2000-08-08

Added line number and column to the parser exceptions thrown. Bill La Forge also
updated the `DTDEmpty` tag to make it print properly.

# 1.6 / 2000-08-07

Bill La Forge made a few changes to aid in the development of Quick.

# 1.5 / 2000-08-04

Probably the most requested feature in the DTD parser is to preserve the order
of all the items in the DTD, both for printing and for general examination. The
DTD class now includes a Vector called "items" that contains the items in the
original DTD file in the order they were read.

Also, you'll find that there are two new classes that may appear in the items
vector - `DTDComment` and `DTDProcessingInstruction`.

# 1.4.1 / 2000-07-29

Bill LaForge has kindly updated the DTD, DTDElement and DTDAttribute classes to
sort the DTD elements before writing them to a PrintWriter.

# 1.4 / 2000-07-26

The DTD object and its subordinate objects now have a write method that lets the
objects write themselves out to a PrintWriter. This allows you to write out DTDs
as well as read them.

Added code to guess the root element of the DTD by process of elimination. By
default, the `DTDParser.parse` method does NOT try to figure out the root.
Instead, you must call `DTDParser.parse(true)` to make it guess.

The `dtdparser14.jar` file includes a Main-Class manifest entry so you can now
run the tokenizer with:

    java -jar dtdparser14.jar somedtdfile.dtd


# 1.3 / 2000-07-13

Fixed the parsing of enumerations to match what the specification calls
"Nmtoken". Basically, an identifier must start with a letter and contain certain
characters, an Nmtoken contains only valid identifier characters but doesn't
need to start with a letter. that the parser will recognize more errors instead
of ignoring them.

# 1.2 / 2000-07-07

Fixed the parser and scanner to interpret `?>` as two separate tokens. The only
downsize to this is that the scanner may accept:

    <? blah blah blah ? >

Also fixed the element parser to explicitly look for `>` at the end of the
element definition. This shouldn't affect anything, except that the parser will
recognize more errors instead of ignoring them.

# 1.0 / 2000-07-04

After some great feedback from Bill La Forge and Pankaj Kamthan, I have been
able to fix several parsing bugs and expand the parser to include other DTD
items. Bill spent a lot time trying out things and giving me feedback about the
object model. Thanks, Bill!

The newly supported features are:
* `<!NOTATION>`
* `<![ INCLUDE ]]>` and `<![ IGNORE ]]>`
* Automatic expansion of parsed entities (as long as they appear before they are
  used. I still need to look to see if that's a requirement for them or not.
* Better support for international character - identifiers may now contain any
  of the characters specified in the XML specification.

I ran the parser against every DTD on my system and it only failed on two of
them - both of which had legitimate errors.