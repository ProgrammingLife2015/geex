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
 * GraphFactory
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class GraphFactory {

    /**
     * Creates an undirected graph based on Node objects that represents a genome.
     *
     * @param nodeFile location of node file
     * @param edgeFile location of edge file
     * @return a graph based on Node objects
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public Graph getGraph(final File nodeFile, final File edgeFile)
            throws FileNotFoundException, UnsupportedEncodingException {

        Graph graph = new Graph();

        List<Node> nodeList = parseNodes(nodeFile, graph);
        parseEdges(edgeFile, graph, nodeList);

        return graph;

    }

    /**
     * Parse the nodes from the node file.
     *
     * @param nodeFile location of node file
     * @param graph    graph to add nodes to
     * @return nodes added to graph
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private List<Node> parseNodes(final File nodeFile, final Graph graph)
            throws FileNotFoundException, UnsupportedEncodingException {

        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(nodeFile), "UTF-8")));
        NodeFactory nodeFactory = new NodeFactory();
        List<Node> nodes = new ArrayList<>();

        while (sc.hasNext()) {
            Node n = nodeFactory.getNode(sc);
            nodes.add(n);
            graph.addVertex(n);
        }

        sc.close();
        return nodes;

    }

    /**
     * Parse the edges from the edge file.
     *
     * @param edgeFile location of edge file
     * @param graph    graph to add edges to
     * @param nodeList nodes used to get edges from
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private void parseEdges(final File edgeFile, final Graph graph, final List<Node> nodeList)
            throws FileNotFoundException, UnsupportedEncodingException {

        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(edgeFile), "UTF-8")));

        while (sc.hasNext()) {
            graph.addEdge(nodeList.get(sc.nextInt()), nodeList.get(sc.nextInt()));
        }

        sc.close();

    }



}
