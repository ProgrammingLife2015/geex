package nl.tudelft.context.model.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationMap extends TreeMap<Integer, List<Annotation>> {

    /**
     * Add annotations to the AnnotationMap.
     *
     * @param annotation the annotation that should be added
     */
    public void addAnnotation(Annotation annotation) {
        int start = annotation.getStart();
        List<Annotation> annotationList = get(start);
        if (annotationList == null) {
            annotationList = new ArrayList<>();
            put(start, annotationList);
        }

        annotationList.add(annotation);

    }

    /**
     * To string method for the AnnotationMap.
     *
     * @return String representing the values in the AnnotationMap, each annotation on a new line
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


