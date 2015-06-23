package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;

/**
 * @param <T> The type of class to Load.
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 24-5-2015
 */
public class LoadService<T> extends Service<T> {
    /**
     * The class used for parsing the files.
     */
    private Class<? extends Loadable<T>> parserClass;
    /**
     * The files to load.
     */
    private File[] files;

    /**
     * Create a new Loader service.
     *
     * @param parserClass Class used for parsing the files.
     * @param files       Files to load.
     */
    public LoadService(final Class<? extends Loadable<T>> parserClass, final File... files) {
        this.parserClass = parserClass;
        this.files = files;

        ready();
    }

    @Override
    protected Task<T> createTask() {
        return new Task<T>() {
            private Loadable<T> parser;

            @Override
            protected T call() throws Exception {
                parser = parserClass.newInstance();
                parser.setFiles(files);
                return parser.load();
            }

            @Override
            protected void cancelled() {
                parser.cancel();
            }
        };
    }
}
