package nl.tudelft.context.model.annotation.coding_sequence;

import nl.tudelft.context.model.annotation.CodingSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class CodingSequenceMap extends TreeMap<Integer, List<CodingSequence>> {

    /**
     * Add annotations to the CodingSequenceMap.
     *
     * @param codingSequence the codingSequence that should be added
     */
    public void addAnnotation(final CodingSequence codingSequence) {
        int start = codingSequence.getStart();
        List<CodingSequence> codingSequenceList = get(start);
        if (codingSequenceList == null) {
            codingSequenceList = new ArrayList<>();
            put(start, codingSequenceList);
        }

        codingSequenceList.add(codingSequence);

    }

    /**
     * Inclusive subMap.
     *
     * @param fromKey From which key (inclusive)
     * @param toKey   To which key (inclusive)
     * @return        The created subMap
     */
    public NavigableMap<Integer, List<CodingSequence>> subMap(final Integer fromKey, final Integer toKey) {
        return subMap(fromKey, true, toKey, true);
    }


    /**
     * To string method for the CodingSequenceMap.
     *
     * @return String representing the values in the CodingSequenceMap, each annotation on a new line
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


