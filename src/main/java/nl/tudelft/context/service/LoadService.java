package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.model.Parser;

import java.io.File;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 24-5-2015
 * @param <T> The type of class to Load.
 */
public class LoadService<T> extends Service<T> {
    /**
     * The class used for parsing the files.
     */
    Class<? extends Parser<T>> parserClass;
    /**
     * The files to parse.
     */
    private File[] files;

    /**
     * Create a new Loader service.
     * @param parserClass Class used for parsing the files.
     * @param files Files to parse.
     */
    public LoadService(final Class<? extends Parser<T>> parserClass, final File... files) {
        this.parserClass = parserClass;
        this.files = files;
    }

    @Override
    protected Task<T> createTask() {
        return new Task<T>() {
            @Override
            protected T call() throws Exception {
                Parser<T> parser = parserClass.newInstance();
                parser.setReader(files);
                return parser.parse();
            }
        };
    }
}
