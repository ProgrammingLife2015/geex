package nl.tudelft.context.model.annotation;

import java.util.List;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 17-06-2015
 */
public class ResistanceMap extends AnnotationMap<Resistance> {

    /**
     * Create resistance map based on the resistance indexed by ref start and ref end.
     *
     * @param resistances List containing resistances.
     */
    public ResistanceMap(List<Resistance> resistances) {
        super(resistances);
    }

}
