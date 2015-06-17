package nl.tudelft.context.workspace;

import javafx.beans.property.ReadOnlyObjectProperty;
import nl.tudelft.context.model.annotation.coding_sequence.AnnotationMap;
import nl.tudelft.context.model.annotation.coding_sequence.AnnotationParser;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.GraphParser;
import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.NewickParser;
import nl.tudelft.context.model.annotation.resistance.ResistanceMap;
import nl.tudelft.context.model.annotation.resistance.ResistanceParser;
import nl.tudelft.context.service.LoadService;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 8-5-2015
 */
public class Workspace {
    /**
     * The workspace directory.
     */
    File directory;

    /**
     * Files in the directory.
     */
    File[] files;

    /**
     * The edge file in the workspace.
     */
    File edgeFile;

    /**
     * The node file in the workspace.
     */
    File nodeFile;

    /**
     * The Newick file in the workspace.
     */
    File nwkFile;

    /**
     * The annotation file in the workspace.
     */
    File annotationFile;

    /**
     * The resistance file in the workspace.
     */
    File resistanceFile;

    /**
     * The service used for parsing a Newick.
     */
    LoadService<Newick> loadNewickService;

    /**
     * The service used for parsing a Graph.
     */
    LoadService<GraphMap> loadGraphService;

    /**
     * The service used for parsing an Annotation.
     */
    LoadService<AnnotationMap> loadAnnotationService;

    /**
     * The service used for parsing the Resistance.
     */
    LoadService<ResistanceMap> loadResistanceService;

    /**
     * Create a new workspace on the directory.
     *
     * @param directory The workspace root
     * @throws FileNotFoundException When the directory is null or doesn't exist
     */
    public Workspace(final File directory) throws FileNotFoundException {
        this.directory = directory;
        if (this.directory == null || !this.directory.exists()) {
            throw new FileNotFoundException();
        }
        files = this.directory.listFiles();
    }

    /**
     * Find a file in files with a certain extension.
     *
     * @param files     Files to search in
     * @param extension Extension to end with
     * @return The found file, null if no file is found.
     * @throws FileNotFoundException Thrown if file not found.
     */
    private File findFile(final File[] files, final String extension) throws FileNotFoundException {
        return Arrays.stream(files)
                .filter(file -> file
                        .toString()
                        .toLowerCase()
                        .endsWith(extension))
                .findFirst()
                .orElseThrow(FileNotFoundException::new);
    }

    /**
     * Load graphs and newick files from the loaded directory.
     *
     * @throws FileNotFoundException If any of the files is not found.
     */
    public final void load() throws FileNotFoundException {
        edgeFile = findFile(files, ".edge.graph");
        nodeFile = findFile(files, ".node.graph");
        nwkFile = findFile(files, ".nwk");
        annotationFile = findFile(files, ".gff");
        resistanceFile = findFile(files, ".txt");
    }

    /**
     * Save the current workspace to the database.
     *
     * @throws SqlJetException Saving failed.
     */
    public void save() throws SqlJetException {
        Database.instance().replace("workspace", this.directory.getAbsolutePath(), this.directory.getName());
    }

    /**
     * Preload the workspace, makes sure all the services are started.
     */
    public final void preload() {
        loadNewickService = new LoadService<>(NewickParser.class, nwkFile);
        loadAnnotationService = new LoadService<>(AnnotationParser.class, annotationFile);
        loadGraphService = new LoadService<>(GraphParser.class, nodeFile, edgeFile);
        loadResistanceService = new LoadService<>(ResistanceParser.class, resistanceFile);

        loadNewickService.start();
        loadAnnotationService.start();
        loadGraphService.start();
        loadResistanceService.start();
    }

    /**
     * Get the Newick Property.
     *
     * @return A ReadOnlyObjectProperty containing, or not yet containing a Newick.
     */
    public ReadOnlyObjectProperty<Newick> getNewick() {
        return loadNewickService.valueProperty();
    }

    /**
     * Get the AnnotationMap Property.
     *
     * @return A ReadOnlyObjectProperty containing, or not yet containing an AnnotationMap.
     */
    public ReadOnlyObjectProperty<AnnotationMap> getAnnotation() {
        return loadAnnotationService.valueProperty();
    }

    /**
     * Get the GraphMap Property.
     *
     * @return A ReadOnlyObjectProperty containing, or not yet containing a GraphMap.
     */
    public ReadOnlyObjectProperty<GraphMap> getGraph() {
        return loadGraphService.valueProperty();
    }

    /**
     * Get the ResistanceMap Property.
     *
     * @return A ReadOnlyObjectProperty containing, or not yet containing a ResistanceMap.
     */
    public ReadOnlyObjectProperty<ResistanceMap> getResistance() {
        return loadResistanceService.valueProperty();
    }

    /**
     * Close this workspace.
     *
     * Cancel all the running services, in order to clean up all the threads.
     */
    public void close() {
        loadAnnotationService.cancel();
        loadGraphService.cancel();
        loadNewickService.cancel();
        loadResistanceService.cancel();
    }
}
