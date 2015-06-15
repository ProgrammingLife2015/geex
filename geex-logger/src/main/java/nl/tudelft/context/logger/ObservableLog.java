package nl.tudelft.context.logger;

/**
 * Interface for the Log class.
 *
 * Any log implementation should implement this interface.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 14-6-2015
 */
public interface ObservableLog {
    /**
     * Add a logger to be called when a log occurs.
     *
     * @param logger Logger to add
     */
    void addLogger(Logger logger);

    /**
     * Remove a logger from the current loggers.
     *
     * @param logger Logger to remove
     */
    void removeLogger(Logger logger);
}
