package nl.tudelft.context.model.graph;

import nl.tudelft.context.model.annotation.Annotation;
import nl.tudelft.context.model.annotation.AnnotationMap;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.1
 * @since 23-4-2015
 */
public class Node extends DefaultNode {

    /**
     * The identifier of the current Node.
     */
    int id;
    /**
     * Start position in reference genome.
     */
    int refStartPosition;
    /**
     * End position in reference genome.
     */
    int refEndPosition;
    /**
     * The Counter for the number of ACTG.
     */
    BaseCounter baseCounter;
    /**
     * The annotations that belong to this node.
     */
    List<Annotation> annotations = Collections.emptyList();

    /**
     * Create a node.
     *
     * @param id               id
     * @param refStartPosition start position in reference genome
     * @param sources          genomes that contain this node
     * @param refEndPosition   end position in reference genome
     * @param content          DNA sequence
     */
    public Node(final int id,
                final Set<String> sources,
                final int refStartPosition,
                final int refEndPosition,
                final String content) {

        this.id = id;
        this.sources = sources;
        this.refStartPosition = refStartPosition;
        this.refEndPosition = refEndPosition;
        this.content = content;
        this.baseCounter = new BaseCounter(content);
    }

    /**
     * Getter for id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    @Override
    public Set<String> getSources() {
        return sources;
    }

    @Override
    public int getRefStartPosition() {
        return refStartPosition;
    }

    @Override
    public int getRefEndPosition() {
        return refEndPosition;
    }

    @Override
    public void setAnnotations(final AnnotationMap annotationMap) {
        if (sources.contains("TKK_REF")) {
            annotations = annotationMap.subMap(refStartPosition, refEndPosition).values().stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Annotation> getAnnotations() {
        return annotations;
    }

    /**
     * Retrieve the text for the annotations in order, without surrounding brackets and with line seperators.
     *
     * @return String representing the Annotation list
     */
    public String getAnnotationText() {
        StringBuilder result = new StringBuilder();
        for (Annotation annotation : annotations) {
            result.append(annotation.toString());
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    /**
     * Getter for baseCounter.
     *
     * @return baseCounter
     */
    public BaseCounter getBaseCounter() {
        return baseCounter;
    }

    /**
     * Checks if node is equal to an other node.
     * <p>
     * It checks only on id, because of performance issues.
     * </p>
     *
     * @return if node is equal to an other node
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            return id == that.id;
        }
        return false;
    }

    /**
     * Creates an hashCode.
     *
     * @return unique hashCode by id
     */
    @Override
    public int hashCode() {
        return this.id;
    }
}
