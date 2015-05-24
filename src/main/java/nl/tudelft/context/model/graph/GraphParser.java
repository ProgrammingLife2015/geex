package nl.tudelft.context.model.graph;

import nl.tudelft.context.model.MultiParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * GraphParser.
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public final class GraphParser extends MultiParser<GraphMap> {


    /**
     * Create a new parser of type T.
     *
     * @param nodeFile node file source.
     * @param edgeFile edge file source.
     * @throws FileNotFoundException        The file is not found.
     * @throws UnsupportedEncodingException The file contains an unsupported encoding (not UTF-8).
     */
    public GraphParser(final File nodeFile, final File edgeFile) throws FileNotFoundException, UnsupportedEncodingException {
        super(nodeFile, edgeFile);
    }

    @Override
    protected GraphMap parse(final BufferedReader... reader) {
        BufferedReader nodeReader = reader[0],
                edgeReader = reader[1];

        GraphMap graphMap = new GraphMap();

        List<Node> nodeList = parseNodes(nodeReader, graphMap);
        parseEdges(edgeReader, graphMap, nodeList);

        return graphMap;
    }

    /**
     * Parse the nodes from the node file.
     *
     * @param nodeReader location of node file
     * @param graphMap graph map to add nodes to
     * @return nodes added to graph
     */
    private List<Node> parseNodes(final BufferedReader nodeReader, final GraphMap graphMap) {
        Scanner sc = new Scanner(nodeReader);
        NodeParser nodeParser = new NodeParser();
        List<Node> nodes = new ArrayList<>();

        while (sc.hasNext()) {
            Node n = nodeParser.getNode(sc);
            nodes.add(n);
            graphMap.addVertex(n);
        }

        sc.close();
        return nodes;

    }

    /**
     * Parse the edges from the edge file.
     *
     * @param edgeReader location of edge file
     * @param graphMap graph map to add edges to
     * @param nodeList nodes used to get edges from
     */
    private void parseEdges(final BufferedReader edgeReader, final GraphMap graphMap, final List<Node> nodeList) {

        Scanner sc = new Scanner(edgeReader);

        while (sc.hasNext()) {
            graphMap.addEdge(nodeList.get(sc.nextInt()), nodeList.get(sc.nextInt()));
        }

        sc.close();

    }
}
