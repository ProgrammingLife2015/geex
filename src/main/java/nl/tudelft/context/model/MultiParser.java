package nl.tudelft.context.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * MultiParser<T> accepts a list of files to simultaniously parse into one object.
 *
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 24-5-2015
 * @param <T> Type of this multiparser.
 */
public abstract class MultiParser<T> extends Parser<T> {

    /**
     * List of readers for this parser.
     */
    BufferedReader[] readerList;
    /**
     * Create a new parser of type T.
     *
     * @param files File source list.
     * @throws FileNotFoundException        The file is not found.
     * @throws UnsupportedEncodingException The file contains an unsupported encoding (not UTF-8).
     */
    public MultiParser(final File... files) throws FileNotFoundException, UnsupportedEncodingException {
        super();
        readerList = new BufferedReader[files.length];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            readerList[i] = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        }
    }

    /**
     * Parse using a list of files.
     * @return The parsed object of type T.
     */
    public T parse() {
        return parse(readerList);
    }

    /**
     * Parse function for multiple files.
     * @param reader1 The input files.
     * @return The parsed object of type T.
     */
    protected abstract T parse(BufferedReader... reader1);

    /**
     * Prevent this function from use.
     * @param reader First parameter
     * @return Nothing, this function is unused.
     */
    @Override
    protected final T parse(final BufferedReader reader) {
        return null;
    }
}
