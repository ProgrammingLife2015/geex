package nl.tudelft.context.newick;

import nl.tudelft.context.drawable.DrawableNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class Node extends DrawableNode {
    private final String name;
    double weight;
    List<Node> children;

    public Node(String name, double weight) {
        this.name = name;
        this.weight = weight;
        children = new ArrayList<>(2);
    }

    public void addChild(Node n) {
        this.children.add(n);
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Node)) {
            return false;
        }
        Node that = (Node)other;
        return getName().equals(that.getName()) &&
                getWeight() == that.getWeight();
    }

    @Override
    public String toString() {
        return "Node<" + getName() + "," + getWeight() + ">";
    }
}
