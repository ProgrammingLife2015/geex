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
public interface IParser<T> {
    T parse();
    IParser<T> setReader(final File... files) throws FileNotFoundException, UnsupportedEncodingException;
}
