package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.annotation.AnnotationParser;

import java.io.File;

/**
 * Service for loading Annotations.
 *
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class LoadAnnotationService extends Service<AnnotationMap> {

    /**
     * The file containing the newick tree.
     */
    File annotationFile;

    /**
     * Set annotation file.
     *
     * @param annotationFile annotation file location
     */
    public LoadAnnotationService(final File annotationFile) {

        this.annotationFile = annotationFile;

    }

    /**
     * Create a task that loads the newick.
     *
     * @return the Task to execute
     */
    @Override
    protected final Task<AnnotationMap> createTask() {

        return new Task<AnnotationMap>() {
            @Override
            protected AnnotationMap call() throws Exception {
                AnnotationParser annotationParser = new AnnotationParser(annotationFile);
                return annotationParser.parse();
            }
        };

    }
}


