package nl.tudelft.context.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 24-5-2015
 * @param <T> The filetype this parser should parse to.
 */
public abstract class Parser<T> {
    /**
     * Reader used for reading the file.
     */
    BufferedReader reader;

    /**
     * Empty constructor for child classes which extend functionality.
     */
    protected Parser() {

    }

    /**
     * Create a new parser of type T.
     * @param file File source.
     * @throws FileNotFoundException The file is not found.
     * @throws UnsupportedEncodingException The file contains an unsupported encoding (not UTF-8).
     */
    public Parser(final File file) throws FileNotFoundException, UnsupportedEncodingException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
    }

    /**
     * Parse the object in this file.
     * @return Parsed object.
     */
    public T parse() {
        return parse(reader);
    }

    /**
     * Abstract method to which a file is given to parse.
     * @param file File to parse.
     * @return Parsed object.
     */
    protected abstract T parse(BufferedReader file);
}
