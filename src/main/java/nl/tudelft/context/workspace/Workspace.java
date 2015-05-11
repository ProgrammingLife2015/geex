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
    File directory;

    private List<LoadGraphService> graphList;
    private List<LoadNewickService> nwkList;

    List<Path> matches;

    public Workspace(File directory) {
        if (directory == null) {
            return;
        }
        this.directory = directory;
    }

    /**
     * Walk the workspace directory.
     */
    public void walk() {
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
    public void load() {
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

                    if (nodeFile == null)
                        return null;

                    return new LoadGraphService(nodeFile, edgeFile);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        nwkList = matches.stream()
                .filter(path -> path.toString().toLowerCase().endsWith(".nwk"))
                .map(path -> new LoadNewickService(path.toFile()))
                .collect(Collectors.toList());
    }

    public List<LoadGraphService> getGraphList() {
        return graphList;
    }

    public List<LoadNewickService> getNewickList() {
        return nwkList;
    }
}
