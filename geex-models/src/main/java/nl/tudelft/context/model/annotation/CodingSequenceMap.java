package nl.tudelft.context.model.annotation;

import java.util.List;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 17-06-2015
 */
public class CodingSequenceMap extends AnnotationMap<CodingSequence> {

    /**
     * Create a coding sequence map based on the resistance indexed by ref start and ref end.
     *
     * @param codingSequences List containing coding sequences.
     */
    public CodingSequenceMap(List<CodingSequence> codingSequences) {
        super(codingSequences);
    }

}
