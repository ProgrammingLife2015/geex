package nl.tudelft.context.model;

import nl.tudelft.context.model.resistance.ResistanceFormatException;

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
    BufferedReader[] readerList;

    /**
     * Empty constructor for child classes which extend functionality.
     */
    protected Parser() {

    }

    /**
     * Set the readers for this parser.
     * @param files The files to read from
     * @return this
     * @throws FileNotFoundException If the file is not found.
     * @throws UnsupportedEncodingException If the file contains an unsupported encoding.
     */
    public Parser<T> setReader(final File... files) throws FileNotFoundException, UnsupportedEncodingException {
        readerList = new BufferedReader[files.length];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            readerList[i] = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        }

        return this;
    }

    /**
     * Parse the object in this file.
     * @return Parsed object.
     */
    public T parse() {
        try {
            return parse(readerList);
        } catch (ResistanceFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Abstract method to which a file is given to parse.
     * @param file File to parse.
     * @return Parsed object.
     */
    protected abstract T parse(BufferedReader... file) throws ResistanceFormatException;
}
