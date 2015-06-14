package nl.tudelft.context.logger.message;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 13-6-2015
 */
public enum MessageType implements Comparable<MessageType> {
    /**
     * Used when showing a warning message.
     *
     * A warning message implies that the software had to stop.
     */
    WARNING(3, "warning"),
    /**
     * Used when showing an info message.
     *
     * Info messages are used to tell the user that something happened.
     */
    INFO(2, "info"),
    /**
     * Used when showing a debug message.
     *
     * Debug messages are used when something goes wrong and is must be debugged.
     */
    DEBUG(1, "debug");

    /**
     * Loglevel of this MessageType.
     */
    private int level;
    /**
     * Type of this MessageType.
     */
    private String type;

    /**
     * Create a new MessageType.
     *
     * @param level LogLevel of this MessageType
     * @param type Type name of this MessageType
     */
    MessageType(final int level, final String type) {
        this.level = level;
        this.type = type;
    }

    /**
     * Get the log-level of this MessageType.
     *
     * @return log-level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get a list of available types.
     *
     * @return List of types in MessageType
     */
    public static List<String> types() {
        return Arrays.stream(values()).map(MessageType::toString).collect(toList());
    }

    @Override
    public String toString() {
        return type;
    }
}
