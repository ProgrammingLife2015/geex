package nl.tudelft.context.logger;

import nl.tudelft.context.logger.message.MessageType;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 14-6-2015
 */
public interface Logger {
    /**
     * Function used when a log message is to be shown.
     *
     * @param message Message to show
     * @param messageType Type of this message
     */
    void log(String message, MessageType messageType);

    /**
     * The ObservableLogger looks at this to choose if a log message is relevant for this Logger.
     *
     * This level and levels worse are included.
     *
     * @return The level relevant for this logger.
     */
    MessageType getLevel();
}
