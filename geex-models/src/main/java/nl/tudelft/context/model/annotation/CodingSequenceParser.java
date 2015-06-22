package nl.tudelft.context.model.annotation;

import nl.tudelft.context.model.Parser;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class CodingSequenceParser extends Parser<CodingSequenceMap> {

    /**
     * Set offset for iterating through array.
     */
    public static final int SEQ_ID_INDEX = 0;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int SOURCE_INDEX = 1;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int TYPE_INDEX = 2;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int START_INDEX = 3;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int END_INDEX = 4;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int SCORE_INDEX = 5;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int STRAND_INDEX = 6;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int PHASE_INDEX = 7;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int ATTRIBUTES_INDEX = 8;


    /**
     * Parse the file into an CodingSequenceMap.
     *
     * @param readerList Reader to read.
     * @return A parsed annotationmap.
     */
    @Override
    public CodingSequenceMap parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        Scanner sc = new Scanner(reader);
        List<CodingSequence> codingSequences = new ArrayList<>();
        String line;
        String fileSplitBy = "\\t";
        while (sc.hasNextLine() && !isCancelled()) {
            line = sc.nextLine();
            String[] splitLine = line.split(fileSplitBy);
            codingSequences.add(getCodingSequence(splitLine));
        }

        return new CodingSequenceMap(codingSequences);
    }

    /**
     * Read a splitted line and generate an annotation.
     *
     * @param splitLine the line with information for the annotation
     * @return CodingSequence
     * @throws NumberFormatException when the data isn't correct
     */
    public final CodingSequence getCodingSequence(final String[] splitLine) {
        String seqId = splitLine[SEQ_ID_INDEX];
        String source = splitLine[SOURCE_INDEX];
        String type = splitLine[TYPE_INDEX];
        int start = Integer.parseInt(splitLine[START_INDEX]);
        int end = Integer.parseInt(splitLine[END_INDEX]);
        float score = Float.parseFloat(splitLine[SCORE_INDEX]);
        char strand = splitLine[STRAND_INDEX].charAt(0);
        char phase = splitLine[PHASE_INDEX].charAt(0);
        String attributes = splitLine[ATTRIBUTES_INDEX];

        return new CodingSequence(seqId, source, type, start, end, score, strand, phase, attributes);
    }
}
