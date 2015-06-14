package nl.tudelft.context.model;

import nl.tudelft.context.service.Loadable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @param <T> The filetype this parser should load to.
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 24-5-2015
 */
public abstract class Parser<T> implements Loadable<T> {
    /**
     * Reader used for reading the file.
     */
    BufferedReader[] readerList;

    protected boolean cancelled = false;

    /**
     * Empty constructor for child classes which extend functionality.
     */
    protected Parser() {

    }

    /**
     * Set the readers for this parser.
     *
     * @param files The files to read from
     * @return this
     * @throws FileNotFoundException        If the file is not found.
     * @throws UnsupportedEncodingException If the file contains an unsupported encoding.
     */
    public Parser<T> setFiles(final File... files) throws FileNotFoundException, UnsupportedEncodingException {
        readerList = new BufferedReader[files.length];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            readerList[i] = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        }

        return this;
    }

    public void cancelled() {
        this.cancelled = true;
    }

    /**
     * Parse the object in this file.
     *
     * @return Parsed object.
     */
    public T load() {

        return parse(readerList);
    }

    /**
     * Abstract method to which a file is given to load.
     *
     * @param file File to load.
     * @return Parsed object.
     */
    protected abstract T parse(BufferedReader... file);
}
