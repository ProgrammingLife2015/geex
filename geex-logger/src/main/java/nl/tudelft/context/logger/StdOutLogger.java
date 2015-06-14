package nl.tudelft.context.logger;

import nl.tudelft.context.logger.message.Message;
import nl.tudelft.context.logger.message.MessageType;

import java.io.PrintStream;
import java.sql.Timestamp;

/**
 * Register as logger and write log messages to stdout.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 14-6-2015
 */
public class StdOutLogger implements Logger {
    /**
     * Create a new StdOutLogger.
     *
     * @param log Log to observe.
     */
    public StdOutLogger(final ObservableLog log) {
        log.addLogger(this);
    }

    @Override
    public void log(final Message message, final MessageType messageType) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        getStream(messageType).printf("%-23s [%s] %s%n", timestamp, messageType, message);
    }

    /**
     * Get a proper stream for each {@link MessageType}.
     * @param type MessageType which is logged
     * @return A PrintStream for this type of message
     */
    PrintStream getStream(final MessageType type) {
        if (type == MessageType.WARNING) {
            return System.err;
        }

        return System.out;
    }

    @Override
    public MessageType getLevel() {
        return MessageType.DEBUG;
    }
}
