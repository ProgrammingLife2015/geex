package nl.tudelft.context.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


/**
 * Interface for parsers which can be loaded with LoadService.
 *
 * @param <T>
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 11-6-2015
 */
public interface Loadable<T> {

    /**
     * Function call to a loading thread.
     *
     * @return Async loaded object.
     */
    T load();

    /**
     * Set filesiles used for loading.
     *
     * @param files Files used for loading.
     * @return This object for chaining.
     * @throws FileNotFoundException        File not found
     * @throws UnsupportedEncodingException Unsupported encoding
     */
    Loadable<T> setFiles(final File... files) throws FileNotFoundException, UnsupportedEncodingException;
}
