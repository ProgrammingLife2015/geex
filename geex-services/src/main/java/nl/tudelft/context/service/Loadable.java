package nl.tudelft.context.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Interface for parsers which can be loaded with LoadService.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 11-6-2015
 */
public interface Loadable<T> {
    T load();
    Loadable<T> setFiles(final File... files) throws FileNotFoundException, UnsupportedEncodingException;
}
