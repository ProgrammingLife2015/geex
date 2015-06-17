package nl.tudelft.context.model.annotation.coding_sequence;

import nl.tudelft.context.model.Parser;

import java.io.BufferedReader;
import java.util.Scanner;


/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class CodingSequenceParser extends Parser<CodingSequenceMap> {

    /**
     * Parse the file into an CodingSequenceMap.
     *
     * @param readerList Reader to read.
     * @return A parsed annotationmap.
     */
    public CodingSequenceMap parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        Scanner sc = new Scanner(reader);
        CodingSequenceMap codingSequenceMap = new CodingSequenceMap();
        String line;
        String fileSplitBy = "\\t";
        while (sc.hasNextLine() && !isCancelled()) {
            line = sc.nextLine();
            String[] splitLine = line.split(fileSplitBy);
            codingSequenceMap.addAnnotation(getAnnotation(splitLine));
        }

        return codingSequenceMap;
    }

    /**
     * Read a splitted line and generate an annotation.
     *
     * @param splitLine the line with information for the annotation
     * @return CodingSequence
     * @throws NumberFormatException when the data isn't correct
     */
    public final CodingSequence getAnnotation(final String[] splitLine) throws NumberFormatException {
        int index = 0;
        String seqId = splitLine[index];
        String source = splitLine[++index];
        String type = splitLine[++index];
        int start = Integer.parseInt(splitLine[++index]);
        int end = Integer.parseInt(splitLine[++index]);
        float score = Float.parseFloat(splitLine[++index]);
        char strand = splitLine[++index].charAt(0);
        char phase = splitLine[++index].charAt(0);
        String attributes = splitLine[++index];

        return new CodingSequence(seqId, source, type, start, end, score, strand, phase, attributes);
    }
}
