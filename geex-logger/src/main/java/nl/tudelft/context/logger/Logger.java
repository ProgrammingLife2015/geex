package nl.tudelft.context.logger;

import nl.tudelft.context.logger.message.Message;
import nl.tudelft.context.logger.message.MessageType;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 14-6-2015
 */
public interface Logger {
    MessageType getLevel();
    void log(Message message, MessageType messageType);
}
