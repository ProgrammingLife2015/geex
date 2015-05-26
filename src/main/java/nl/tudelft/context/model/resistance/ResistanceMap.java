package nl.tudelft.context.model.resistance;

import java.util.HashMap;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class ResistanceMap extends HashMap<String, Resistance> {


    /**
     * To string method for the ResistanceMap.
     *
     * @return String representing the values in the ResistanceMap, each annotation on a new line
     */
    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder();
        for (Resistance a : values()) {
            result.append(a.toString());
            result.append(System.getProperty("line.separator"));
        }
        return result.toString();

    }

}


