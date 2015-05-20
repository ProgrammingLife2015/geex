package nl.tudelft.context.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
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
public final class GraphParser {

    /**
     * Creates an undirected graph based on Node objects that represents a genome for each source.
     *
     * @param nodeFile location of node file
     * @param edgeFile location of edge file
     * @return a graph based on Node objects
     * @throws FileNotFoundException        if nodeFile or edgeFile not found
     * @throws UnsupportedEncodingException if the encoding of nodeFile or edgeFile is not supported
     */
    public GraphMap getGraphMap(final File nodeFile, final File edgeFile)
            throws FileNotFoundException, UnsupportedEncodingException {

        GraphMap graphMap = new GraphMap();

        List<Node> nodeList = parseNodes(nodeFile, graphMap);
        parseEdges(edgeFile, graphMap, nodeList);

        return graphMap;

    }

    /**
     * Parse the nodes from the node file.
     *
     * @param nodeFile location of node file
     * @param graphMap graph map to add nodes to
     * @return nodes added to graph
     * @throws FileNotFoundException        if nodeFile or edgeFile not found
     * @throws UnsupportedEncodingException if the encoding of nodeFile or edgeFile is not supported
     */
    private List<Node> parseNodes(final File nodeFile, final GraphMap graphMap)
            throws FileNotFoundException, UnsupportedEncodingException {

        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(nodeFile), "UTF-8")));
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
     * @param edgeFile location of edge file
     * @param graphMap graph map to add edges to
     * @param nodeList nodes used to get edges from
     * @throws FileNotFoundException        if nodeFile or edgeFile not found
     * @throws UnsupportedEncodingException if the encoding of nodeFile or edgeFile is not supported
     */
    private void parseEdges(final File edgeFile, final GraphMap graphMap, final List<Node> nodeList)
            throws FileNotFoundException, UnsupportedEncodingException {

        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(edgeFile), "UTF-8")));

        while (sc.hasNext()) {
            graphMap.addEdge(nodeList.get(sc.nextInt()), nodeList.get(sc.nextInt()));
        }

        sc.close();

    }


}
