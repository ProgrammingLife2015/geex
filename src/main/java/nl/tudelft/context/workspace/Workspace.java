package nl.tudelft.context.workspace;

import nl.tudelft.context.service.LoadGraphService;
import nl.tudelft.context.service.LoadNewickService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * The list of graphs in the workspace.
     */
    List<LoadGraphService> graphList;

    /**
     * The list of Newick graphs in the workspace.
     */
    List<LoadNewickService> nwkList;

    /**
     * The results of the finder.
     */
    List<Path> matches;

    /**
     * Create a new workspace on the directory.
     * @param directory The workspace root
     */
    public Workspace(final File directory) {
        if (directory == null) {
            return;
        }
        this.directory = directory;
    }

    /**
     * Walk the workspace directory.
     */
    public final void walk() {
        Finder finder = new Finder("*.{edge.graph,node.graph,nwk}");

        try {
            Files.walkFileTree(this.directory.toPath(), finder);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.matches = finder.getMatches();
    }

    /**
     * Load graphs and newick files from the loaded directory.
     */
    public final void load() {
        Stream<File> edges = matches.stream()
                .map(Path::toFile)
                .filter(path -> path.toString()
                        .toLowerCase()
                        .endsWith(".edge.graph"));
        List<File> nodes = matches.stream()
                .map(Path::toFile)
                .filter(path -> path.toString()
                        .toLowerCase()
                        .endsWith(".node.graph"))
                .collect(Collectors.toList());

        graphList = edges.map(
                edgeFile -> {
                    File nodeFile = nodes.stream()
                            .filter(file ->
                                    file.getAbsolutePath()
                                            .replaceFirst("\\.node\\.graph$", "")
                                            .equals(edgeFile.getAbsolutePath()
                                                    .replaceFirst("\\.edge\\.graph$", "")))
                            .findFirst().orElse(null);

                    if (nodeFile == null) {
                        return null;
                    }

                    return new LoadGraphService(nodeFile, edgeFile);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        nwkList = matches.stream()
                .filter(path -> path.toString().toLowerCase().endsWith(".nwk"))
                .map(path -> new LoadNewickService(path.toFile()))
                .collect(Collectors.toList());
    }

    /**
     * Get the list of graphs in the workspace.
     * @return List of graphs
     */
    public List<LoadGraphService> getGraphList() {
        return graphList;
    }

    /**
     * Get the list of newick graphs in the workspace.
     * @return List of Newick graphs
     */
    public List<LoadNewickService> getNewickList() {
        return nwkList;
    }
}
