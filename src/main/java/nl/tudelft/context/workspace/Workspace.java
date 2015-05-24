package nl.tudelft.context.workspace;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.controller.MessageController;
import nl.tudelft.context.controller.NewickController;

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
            mainController.displayMessage(MessageController.SUCCESS_LOAD_WORKSPACE);

            mainController.setWorkspace(workspace);
            mainController.setBaseView(new NewickController(mainController));
        } catch (FileNotFoundException e) {
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
    private File findFile(final File[] files, final String extension) throws FileNotFoundException{
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
     * @return If all files are loaded.
     * @throws FileNotFoundException If any of the files is not found.
     */
    public final void load() throws FileNotFoundException{
        edgeFile = findFile(files, ".edge.graph");
        nodeFile = findFile(files, ".node.graph");
        nwkFile = findFile(files, ".nwk");
        annotationFile = findFile(files, ".ann.csv");
    }

    /**
     * Get Node file in the workspace.
     *
     * @return Node file
     */
    public File getNodeFile() {
        return nodeFile;
    }

    /**
     * Get the Edge file in the workspace.
     *
     * @return Edge file
     */
    public File getEdgeFile() {
        return edgeFile;
    }

    /**
     * Get the newick graph in the workspace.
     *
     * @return Newick file
     */
    public File getNwkFile() {
        return nwkFile;
    }

    /**
     * Get the annotation file in the workspace.
     *
     * @return annotation file
     */
    public File getAnnotationFile() {
        return annotationFile;
    }

}
