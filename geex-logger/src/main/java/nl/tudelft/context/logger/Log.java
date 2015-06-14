package nl.tudelft.context.logger;

import nl.tudelft.context.logger.message.Message;
import nl.tudelft.context.logger.message.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 14-6-2015
 */
public class Log implements ObservableLog {

    private static volatile Log instance;

    /**
     * Get or Create the logger.
     *
     * @return The logger for this application.
     */
    public static Log instance() {
        if (instance == null) {
            synchronized (Log.class) {
                if (instance == null) {
                    instance = new Log();
                }
            }
        }
        return instance;
    }

    private Log() {
        listeners = new ArrayList<>();
    }

    List<Logger> listeners;

    public void addLogger(Logger listener) {
        listeners.add(listener);
    }

    public void removeLogger(Logger listener) {
        listeners.remove(listener);
    }

    public static void info(Message message) {
        instance().message(message, MessageType.INFO);
    }

    public static void warning(Message message) {
        instance().message(message, MessageType.WARNING);
    }

    public static void debug(Message message) {
        instance().message(message, MessageType.DEBUG);
    }

    public void message(Message message, MessageType type) {
        listeners.stream()
                .filter(logger -> logger.getLevel().getLevel() <= type.getLevel())
                .forEach(logger -> logger.log(message, type));
    }
}
