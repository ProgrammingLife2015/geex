package nl.tudelft.context.model.resistance;

import nl.tudelft.context.model.Parser;

import java.io.BufferedReader;
import java.util.Scanner;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 26-5-2015
 */
public class ResistanceParser extends Parser<ResistanceMap> {

    /**
     * Parse the file into an ResistanceMap.
     *
     * @param readerList Reader to read.
     * @return A parsed resistanceMap.
     */
    @Override
    protected ResistanceMap parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        Scanner sc = new Scanner(reader);
        ResistanceMap resistanceMap = new ResistanceMap();
        String line = "";
        String cvsSplitBy = ",";
        int index = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] splitLine = line.split(cvsSplitBy);
            resistanceMap.put(index, getResistance(splitLine));
            index++;
        }

        return resistanceMap;
    }

    /**
     * Read a splitted line and generate an resistance.
     *
     * @param splitLine the line with information for the resistance.
     * @return Resistance
     * @throws NumberFormatException when the data isn't correct
     */
    public final Resistance getResistance(final String[] splitLine) throws NumberFormatException {
        String geneName = splitLine[0];
        String typeOfMutation = splitLine[1];
        String change = splitLine[2];
        int genomePosition = Integer.parseInt(splitLine[3]);
        String drugName = splitLine[4];
        Resistance resistance = new Resistance(geneName, typeOfMutation, change, genomePosition, drugName);
        return resistance;
    }

}
