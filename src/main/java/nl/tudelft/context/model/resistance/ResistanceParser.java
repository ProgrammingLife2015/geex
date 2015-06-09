package nl.tudelft.context.model.resistance;

import nl.tudelft.context.model.Parser;

import java.io.BufferedReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jasper Nieuwdorp
 * @version 1.1
 * @since 08-6-2015
 */
public class ResistanceParser extends Parser<ResistanceMap> {

    /**
     * Parse the file into an ResistanceMap.
     *
     * @param readerList Reader to read.
     * @return A parsed resistanceMap.
     */
    @Override
    protected ResistanceMap parse(final BufferedReader... readerList) throws ResistanceFormatException {
        BufferedReader reader = readerList[0];
        Scanner sc = new Scanner(reader);
        ResistanceMap resistanceMap = new ResistanceMap();
        String line;
        int index = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            while (line.matches("^##.*$")) {
                line = sc.nextLine();
            }
            resistanceMap.put(index, getResistance(line));
            index++;
        }

        return resistanceMap;
    }

    /**
     * Read a splitted line and generate an resistance.
     *
     * @param line the line with information for the resistance.
     * @return Resistance
     * @throws NumberFormatException when the data isn't correct
     */
    public final Resistance getResistance(final String line) throws NumberFormatException, ResistanceFormatException {
        Pattern p = Pattern.compile("(^.*):(.*),(.*),(.*),(\\d+)\\t([A-Z])");
        Matcher matcher = p.matcher(line);
        if (matcher.find()) {
            String geneName = matcher.group(1);
            String typeOfMutation = matcher.group(2);
            String change = matcher.group(3);
            String filter = matcher.group(4);
            int genomePosition = Integer.parseInt(matcher.group(5));
            String drugName = getDrugName(matcher.group(6));
            return new Resistance(geneName, typeOfMutation, change, genomePosition, filter, drugName);
        } else {
            throw new ResistanceFormatException();
        }
    }

    /**
     * From a single letter-code determine which drug it stands for.
     *
     * @param letter String that represents the single letter code
     * @return String the name of the drug
     */
    public final String getDrugName(final String letter) {
        switch (letter) {
            case "R":
                return "rifampicin";
            case "T":
            case "M":
                return "ethionomide";
            case "I":
                return "isoniazid";
            case "O":
                return "ofloxacin";
            case "S":
                return "streptomycin";
            case "K":
                return "kanamycin";
            case "P":
                return "pyrazinamide";
            case "E":
                return "ethambutol";
            default:
                return "none";

        }
    }

}
