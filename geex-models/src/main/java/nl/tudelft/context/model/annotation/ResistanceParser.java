package nl.tudelft.context.model.annotation;

import nl.tudelft.context.logger.Log;
import nl.tudelft.context.model.Parser;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
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
     * The index of the group that holds the geneName.
     */
    public static final int GENE_NAME_INDEX = 1;

    /**
     * The index of the group that holds the typeOfMutation.
     */
    public static final int TYPE_OF_MUTATION_INDEX = 2;

    /**
     * The index of the group that holds the change.
     */
    public static final int CHANGE_INDEX = 3;

    /**
     * The index of the group that holds the filter.
     */
    public static final int FILTER_INDEX = 4;

    /**
     * The index of the group that holds the genomePosition.
     */
    public static final int GENOME_POSITION_SHIFT = 5;

    /**
     * The index of the group that holds the drugName.
     */
    public static final int DRUG_NAME_INDEX = 6;

    /**
     * Parse the file into an ResistanceMap.
     *
     * @param readerList Reader to read.
     * @return A parsed resistanceMap.
     */
    @Override
    public ResistanceMap parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        Scanner sc = new Scanner(reader);
        List<Resistance> resistances = new ArrayList<>();
        String line;
        while (sc.hasNextLine() && !isCancelled()) {
            line = sc.nextLine();
            while (line.matches("^##.*$")) {
                line = sc.nextLine();
            }
            try {
                resistances.add(getResistance(line));
            } catch (ResistanceFormatException e) {
                Log.debug(e.toString());
                Log.debug(e);
            }
        }

        return new ResistanceMap(resistances);
    }

    /**
     * Read a splitted line and generate an resistance.
     *
     * @param line the line with information for the resistance.
     * @return Resistance
     * @throws ResistanceFormatException when the value of the data isn't spec compliant.
     */
    public final Resistance getResistance(final String line)
            throws ResistanceFormatException {
        Pattern p = Pattern.compile("(^.*):(.*),(.*),(.*),(\\d+)\\t([A-Z])");
        Matcher matcher = p.matcher(line);
        if (matcher.find()) {
            String geneName = matcher.group(GENE_NAME_INDEX);
            String typeOfMutation = matcher.group(TYPE_OF_MUTATION_INDEX);
            String change = matcher.group(CHANGE_INDEX);
            String filter = matcher.group(FILTER_INDEX);
            int genomePosition = Integer.parseInt(matcher.group(GENOME_POSITION_SHIFT));
            String drugName = getDrugName(matcher.group(DRUG_NAME_INDEX));
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
    public final String getDrugName(final String letter) {
        return DrugName.valueOf(letter).toString();
    }

}
