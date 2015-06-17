package nl.tudelft.context.model.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 17-06-2015
 * @param <T> The type of list, as values in the tree map.
 */
public abstract class AnnotationMap<T extends Annotation> extends TreeMap<Integer, List<T>> {
    /**
     * Add annotations to the AnnotationMap.
     *
     * @param annotation The annotation that should be added
     */
    public void addAnnotation(final T annotation) {
        int start = annotation.getStart();
        List<T> annotationList = get(start);
        if (annotationList == null) {
            annotationList = new ArrayList<>();
            put(start, annotationList);
        }

        annotationList.add(annotation);

    }

    /**
     * Inclusive subMap.
     *
     * @param fromKey From which key (inclusive)
     * @param toKey   To which key (inclusive)
     * @return        The created subMap
     */
    public NavigableMap<Integer, List<T>> subMap(final Integer fromKey, final Integer toKey) {
        return subMap(fromKey, true, toKey, true);
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
