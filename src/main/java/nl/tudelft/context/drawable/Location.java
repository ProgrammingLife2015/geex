package nl.tudelft.context.drawable;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 5-6-2015
 */
public class Location {

    /**
     * Start location.
     */
    int start;

    /**
     * End location.
     */
    int end;

    /**
     * Create a location.
     *
     * @param start Start location
     * @param end   End location
     */
    public Location(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Get start location.
     *
     * @return Start location
     */
    public int getStart() {
        return start;
    }

    /**
     * Get end location.
     *
     * @return End location.
     */
    public int getEnd() {
        return end;
    }

}
