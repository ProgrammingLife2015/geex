package nl.tudelft.context.model.annotation.resistance;

import nl.tudelft.context.model.Parser;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.annotation.Resistance;

import java.io.BufferedReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Jasper Nieuwdorp
 * @version 1.1
 * @since 08-6-2015
 */
public class ResistanceParser extends Parser<AnnotationMap<Resistance>> {

    /**
     * Parse the file into an ResistanceMap.
     *
     * @param readerList Reader to read.
     * @return A parsed resistanceMap.
     */
    @Override
    protected AnnotationMap<Resistance> parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        Scanner sc = new Scanner(reader);
        AnnotationMap<Resistance> resistanceMap = new AnnotationMap<>();
        String line;
        int index = 0;
        while (sc.hasNextLine() && !isCancelled()) {
            line = sc.nextLine();
            while (line.matches("^##.*$")) {
                line = sc.nextLine();
            }
            try {
                resistanceMap.addAnnotation(getResistance(line));
            } catch (ResistanceFormatException e) {
                e.toString(); //For sending to logger.
            }
            index++;
        }

        return resistanceMap;
    }

    /**
     * Read a splitted line and generate an resistance.
     *
     * @param line the line with information for the resistance.
     * @return Resistance
     * @throws NumberFormatException     when the data isn't correct
     * @throws ResistanceFormatException when the value of the data isn't spec compliant.
     */
    public final Resistance getResistance(final String line) throws NumberFormatException, ResistanceFormatException {
        Pattern p = Pattern.compile("(^.*):(.*),(.*),(.*),(\\d+)\\t([A-Z])");
        Matcher matcher = p.matcher(line);
        if (matcher.find()) {
            int index = 1;
            String geneName = matcher.group(index);
            String typeOfMutation = matcher.group(++index);
            String change = matcher.group(++index);
            String filter = matcher.group(++index);
            int genomePosition = Integer.parseInt(matcher.group(++index));
            String drugName = getDrugName(matcher.group(++index).charAt(0));
            return new Resistance(geneName, typeOfMutation, change, filter, genomePosition, drugName);
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
    public final String getDrugName(final char letter) {
        switch (letter) {
            case 'R' :
                return "rifampicin";
            case 'T' :
            case 'M' :
                return "ethionomide";
            case 'I' :
                return "isoniazid";
            case 'O' :
                return "ofloxacin";
            case 'S' :
                return "streptomycin";
            case 'K' :
                return "kanamycin";
            case 'P' :
                return "pyrazinamide";
            case 'E' :
                return "ethambutol";
            default:
                return "none";

        }
    }

}
