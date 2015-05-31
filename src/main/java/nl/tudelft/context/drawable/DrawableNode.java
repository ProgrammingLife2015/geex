package nl.tudelft.context.drawable;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class DrawableNode extends DrawablePosition {

    /**
     * The current number of incoming nodes.
     */
    int currentIncoming = 0;

    /**
     * Increment current incoming and return.
     *
     * @return current incoming
     */
    public final int incrementIncoming() {

        return ++currentIncoming;

    }

    /**
     * Reset the current incoming.
     */
    public final void resetIncoming() {
        currentIncoming = 0;
    }

}
