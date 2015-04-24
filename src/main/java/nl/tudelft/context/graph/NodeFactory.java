package nl.tudelft.context.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 24-4-2015
 */
public class NodeFactory {

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
    public Node getNode(Scanner sc) {

        final int id = getNodeId(sc);
        sc.next(); // Skip pipe
        final Set<String> sources = new HashSet<>(Arrays.asList(sc.next().split(",")));
        sc.next(); // Skip pipe
        final int refStartPosition = sc.nextInt();
        sc.next(); // Skip pipe
        final int refEndPosition = sc.nextInt();
        String content = sc.next();

        return new Node(id, sources, refStartPosition, refEndPosition, content);

    }

}
