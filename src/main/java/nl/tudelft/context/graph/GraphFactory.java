package nl.tudelft.context.graph;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
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
     * Parses the node id from the scanner.
     *
     * @param sc scanner
     * @return node id
     */
    protected int getNodeId(Scanner sc) {

        return Integer.parseInt(sc.next().substring(1));

    }

    /**
     * Parses the node from the scanner.
     *
     * @param sc scanner
     * @return node
     */
    protected Node getNode(Scanner sc) {

        int id = getNodeId(sc);
        sc.next(); // Skip pipe
        sc.next(); // Skip source for now
        sc.next(); // Skip pipe
        int refStartPosition = sc.nextInt();
        sc.next(); // Skip pipe
        int refEndPosition = sc.nextInt();
        String content = sc.next();

        return new Node(id, refStartPosition, refEndPosition, content);

    }

    /**
     * Parse the nodes from the node file.
     *
     * @param nodeFile location of node file
     * @param graph    graph to add nodes to
     * @return nodes added to graph
     */
    protected List<Node> parseNodes(String nodeFile, UndirectedGraph<Node, DefaultEdge> graph) {

        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(nodeFile))));
        List<Node> nodes = new ArrayList<>();

        while (sc.hasNext()) {
            Node n = getNode(sc);
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
    protected void parseEdges(String edgeFile, UndirectedGraph<Node, DefaultEdge> graph, List<Node> nodeList) {

        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(edgeFile))));

        while (sc.hasNext()) {
            graph.addEdge(nodeList.get(sc.nextInt() - 1), nodeList.get(sc.nextInt() - 1));
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
    public UndirectedGraph<Node, DefaultEdge> getGraph(String nodeFile, String edgeFile) throws FileNotFoundException {

        UndirectedGraph<Node, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        List<Node> nodeList = parseNodes(nodeFile, graph);
        parseEdges(edgeFile, graph, nodeList);

        return graph;

    }

}
