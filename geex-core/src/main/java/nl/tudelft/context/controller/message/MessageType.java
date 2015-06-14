package nl.tudelft.context.controller.message;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 13-6-2015
 */
public enum MessageType {
    WARNING("warning"),
    INFO("info");

    private String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static List<String> types() {
        return Arrays.stream(values()).map(MessageType::getType).collect(toList());
    }
}
