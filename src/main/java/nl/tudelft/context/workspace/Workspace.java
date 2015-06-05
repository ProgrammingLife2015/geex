package nl.tudelft.context.workspace;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.controller.MessageController;
import nl.tudelft.context.controller.NewickController;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.annotation.AnnotationParser;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.GraphParser;
import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.NewickParser;
import nl.tudelft.context.service.LoadService;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
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
     * Create a new workspace on the directory.
     *
     * @param directory The workspace root
     */
    public Workspace(final File directory) {
        this.directory = directory;
        if (this.directory == null) {
            files = new File[0];

            return;
        }
        files = this.directory.listFiles();
    }

    /**
     * Choose a workspace with a directorychooser.
     *
     * @param mainController The application to choose a workspace for.
     */
    public static void chooseWorkspace(final MainController mainController) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Workspace Folder");
        Window window = mainController.getRoot().getScene().getWindow();
        File workspaceDirectory = directoryChooser.showDialog(window);

        Workspace workspace = new Workspace(workspaceDirectory);
        try {
            workspace.load();
            workspace.save();
            mainController.displayMessage(MessageController.SUCCESS_LOAD_WORKSPACE);

            mainController.setWorkspace(workspace);
            mainController.setBaseView(new NewickController(mainController,
                    mainController.getMenuController().getLoadGenomeGraph(), workspace.getNewick()));
        } catch (FileNotFoundException | SqlJetException e) {
            mainController.displayMessage(MessageController.FAIL_LOAD_WORKSPACE);
        }
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
        annotationFile = findFile(files, ".ann.csv");
    }

    /**
     * Save the current workspace to the database.
     */
    public void save() throws SqlJetException{
        Database.instance().replace("workspace", this.directory.getAbsolutePath(), this.directory.getName());
    }

    /**
     * Preload the workspace, makes sure all the services are started.
     */
    public final void preload() {
        loadNewickService = new LoadService<>(NewickParser.class, nwkFile);
        loadAnnotationService = new LoadService<>(AnnotationParser.class, annotationFile);
        loadGraphService = new LoadService<>(GraphParser.class, nodeFile, edgeFile);

        loadNewickService.start();
        loadAnnotationService.start();
        loadGraphService.start();
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
}
