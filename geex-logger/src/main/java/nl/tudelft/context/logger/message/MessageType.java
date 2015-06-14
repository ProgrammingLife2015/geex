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
    WARNING(3, "warning"),
    INFO(2, "info"),
    DEBUG(1, "debug"),
    ;

    private int level;
    private String type;

    MessageType(int level, String type) {
        this.level = level;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public static List<String> types() {
        return Arrays.stream(values()).map(MessageType::getType).collect(toList());
    }

    @Override
    public String toString() {
        return type;
    }
}
