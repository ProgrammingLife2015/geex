package nl.tudelft.context.model.annotation;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 17-06-2015
 */
public abstract class Annotation {

    /**
     * The start coordinates of the feature are given in positive 1-based integer coordinates,
     * relative to the landmark given in column one.
     * Start is always less than or equal to end.
     * For features that cross the origin of a circular feature
     * (e.g. most bacterial genomes, plasmids, and some viral genomes),
     * the requirement for start to be less than or equal to end is satisfied
     * by making end = the position of the end + the length of the landmark feature.
     * For zero-length features, such as insertion sites,
     * start equals end and the implied site is to the right of the indicated base in the direction of the landmark.
     */
    int start;

    /**
     * the end coordinate, see start.
     */
    int end;

    /**
     * Gets the start position of the annotation.
     *
     * @return The start position
     */
    public int getStart() {
        return start;
    }
}
