package nl.tudelft.context.model.annotation;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @param <T> The type of list, as values in the tree map.
 * @author Jasper Boot
 * @author René Vennik
 * @version 1.0
 * @since 17-06-2015
 */
public abstract class AnnotationMap<T extends Annotation> {

    /**
     * Annotations grouped by ref start.
     */
    TreeMap<Integer, List<T>> annotationsByStart;

    /**
     * Annotations grouped by ref end.
     */
    TreeMap<Integer, List<T>> annotationsByEnd;

    /**
     * Create annotation map based on the annotations indexed by ref start and ref end.
     *
     * @param annotations List containing annotations.
     */
    public AnnotationMap(final List<T> annotations) {

        annotationsByStart = groupBy(annotations, Annotation::getStart);
        annotationsByEnd = groupBy(annotations, Annotation::getEnd);

    }

    /**
     * Group the annotations by start or end position.
     *
     * @param annotations Annotations to group
     * @param position    Position to group by
     * @return Grouped annotations in tree map
     */
    private TreeMap<Integer, List<T>> groupBy(final List<T> annotations, final Function<Annotation, Integer> position) {

        return annotations.stream().collect(Collectors.groupingBy(
                position,
                TreeMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())
        ));

    }

    /**
     * Get annotations between ref start and end position.
     *
     * @param refStart From which ref start (inclusive)
     * @param refEnd   To which ref end (inclusive)
     * @return Annotations between ref start and end position
     */
    public List<T> annotationsBetween(final Integer refStart, final Integer refEnd) {
        return Stream.concat(
                annotationsByStart.subMap(refStart, true, refEnd, true).values().stream(),
                annotationsByEnd.subMap(refStart, true, refEnd, true).values().stream())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }


}
