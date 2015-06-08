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
    protected ResistanceMap parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        Scanner sc = new Scanner(reader);
        ResistanceMap resistanceMap = new ResistanceMap();
        String line = "";
        String cvsSplitBy = ",";
        int index = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            while (line.matches("^##.*$")) {
                line = sc.nextLine();
            }
            line = line.replace(":", ",");
            line = line.replace("\r", ",");
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
        String drugName = "none";

        Pattern p = Pattern.compile("(\\d+)(\t)([A-Z])");
        int genomePosition = Integer.MAX_VALUE;
        Matcher matcher = p.matcher(splitLine[4]);
        if (matcher.find()) {
            genomePosition = Integer.parseInt(matcher.group(1));
            drugName = getDrugName(matcher.group(3));
        }

        Resistance resistance = new Resistance(geneName, typeOfMutation, change, genomePosition, drugName);
        return resistance;
    }

    /**
     * From a single letter-code determine which drug it stands for.
     *
     * @param letter String that represents the single letter code
     * @return String the name of the drug
     */
    public final String getDrugName(final String letter) {
        String drugName;
        switch (letter) {
            case "R":
                drugName = "rifampicin";
                break;
            case "T":
                drugName = "ethionomide";
                break;
            case "M":
                drugName = "ethionomide";
                break;
            case "I":
                drugName = "isoniazid";
                break;
            case "O":
                drugName = "ofloxacin";
                break;
            case "S":
                drugName = "streptomycin";
                break;
            case "K":
                drugName = "kanamycin";
                break;
            case "P":
                drugName = "pyrazinamide";
                break;
            case "E":
                drugName = "ethambutol";
                break;
            default:
                drugName = "none";

        }
        return drugName;
    }

}
