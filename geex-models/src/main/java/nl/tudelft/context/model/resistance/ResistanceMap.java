package nl.tudelft.context.model.resistance;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class ResistanceMap extends TreeMap<Integer, List<Resistance>> {


    /**
     * To string method for the ResistanceMap.
     *
     * @return String representing the values in the ResistanceMap, each annotation on a new line
     */
    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry a : entrySet()) {
            result.append(a.getValue().toString());
            result.append(System.getProperty("line.separator"));
        }
        return result.toString();

    }

}


