package nl.tudelft.context.graph;

import java.io.*;
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
     * Parse the nodes from the node file.
     *
     * @param nodeFile location of node file
     * @param graph    graph to add nodes to
     * @return nodes added to graph
     */
    protected List<Node> parseNodes(File nodeFile, Graph graph) throws FileNotFoundException {

        Scanner sc = new Scanner(new BufferedReader(new FileReader(nodeFile)));
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
     */
    protected void parseEdges(File edgeFile, Graph graph, List<Node> nodeList) throws FileNotFoundException {

        Scanner sc = new Scanner(new BufferedReader(new FileReader(edgeFile)));

        while (sc.hasNext()) {
            graph.addEdge(nodeList.get(sc.nextInt()), nodeList.get(sc.nextInt()));
        }

        sc.close();

    }

    /**
     * Creates an undirected graph based on Node objects that represents a genome.
     *
     * @param nodeFile location of node file
     * @param edgeFile location of edge file
     * @return a graph based on Node objects
     * @throws FileNotFoundException
     */
    public Graph getGraph(File nodeFile, File edgeFile) throws FileNotFoundException {

        Graph graph = new Graph();

        List<Node> nodeList = parseNodes(nodeFile, graph);
        parseEdges(edgeFile, graph, nodeList);

        return graph;

    }

}
