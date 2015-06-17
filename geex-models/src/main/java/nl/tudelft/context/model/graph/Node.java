package nl.tudelft.context.model.graph;

import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.annotation.CodingSequence;
import nl.tudelft.context.model.annotation.Resistance;
import org.apache.commons.lang.StringUtils;

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
     * The codingSequences that belong to this node.
     */
    List<CodingSequence> codingSequences = Collections.emptyList();
    /**
     * The resistance mutations that belong to this node.
     */
    List<Resistance> resistance = Collections.emptyList();

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
    public int getSize() {
        return 1;
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
    public void setCodingSequences(final AnnotationMap<CodingSequence> codingSequenceMap) {
        if (sources.contains("TKK_REF")) {
            codingSequences = codingSequenceMap.subMap(refStartPosition, refEndPosition).values().stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<CodingSequence> getCodingSequences() {
        return codingSequences;
    }

    @Override
    public void setResistance(final AnnotationMap<Resistance> resistanceMap) {
        resistance = resistanceMap.subMap(refStartPosition, refEndPosition).values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Resistance> getResistance() {
        return resistance;
    }

    /**
     * Retrieve the text for the codingSequences in order, without surrounding brackets and with line seperators.
     *
     * @return String representing the CodingSequence list
     */
    public String getAnnotationText() {
        List<String> annotationList = codingSequences.stream().map(CodingSequence::toString).collect(Collectors.toList());
        if (annotationList.isEmpty()) {
            return "None";
        } else {
            return StringUtils.join(annotationList, System.lineSeparator());
        }
    }

    @Override
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
