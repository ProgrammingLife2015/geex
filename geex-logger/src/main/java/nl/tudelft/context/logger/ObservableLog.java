package nl.tudelft.context.logger;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 14-6-2015
 */
public interface ObservableLog {
    void addLogger(Logger logger);
    void removeLogger(Logger logger);
}
