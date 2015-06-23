package nl.tudelft.context.controller.graphlist;

/**
 * Interface for showing that it is possible to destroy an object.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 22-6-2015
 */
public interface Destroyable {
    /**
     * After this function is called, there should be zero pointers to this object.
     */
    void destroy();
}
