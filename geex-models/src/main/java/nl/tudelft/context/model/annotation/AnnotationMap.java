package nl.tudelft.context.model.annotation;

import java.util.HashMap;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationMap extends HashMap<String, Annotation> {

    /**
     * To string method for the AnnotationMap.
     *
     * @return String representing the values in the AnnotationMap, each annotation on a new line
     */
    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder();
        for (Annotation a : values()) {
            result.append(a.toString());
            result.append(System.getProperty("line.separator"));
        }
        return result.toString();

    }

}


