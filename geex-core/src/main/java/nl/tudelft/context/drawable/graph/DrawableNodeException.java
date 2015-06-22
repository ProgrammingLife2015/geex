package nl.tudelft.context.drawable.graph;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 22-6-2015
 */
public class DrawableNodeException extends RuntimeException {
    private String message;

    public DrawableNodeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
