package nl.tudelft.context.drawable.graph;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 22-6-2015
 */
public class DrawableNodeException extends RuntimeException {
    /**
     * Message belonging to this exception.
     */
    private final String message;

    /**
     * Create a new DrawableNodeException, used when trying to create a DrawableNdoe from a Node fails.
     *
     * @param message What exactly failed.
     */
    public DrawableNodeException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
