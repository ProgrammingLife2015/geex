package nl.tudelft.context.graph;

import org.apache.commons.lang.mutable.MutableInt;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Jasper Nieuwdorp <jaspernieuwdorp@hotmail.com>
 * @version 1.0
 * @since 06-05-2015
 */
public class BaseCounter extends HashMap<Character, MutableInt> {

    static DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

    /**
     * Constructor for the baseCounter
     *
     * @param bases String with the dna sequence
     */
    public BaseCounter(String bases) {
        put('A', new MutableInt());
        put('T', new MutableInt());
        put('C', new MutableInt());
        put('G', new MutableInt());
        put('N', new MutableInt());
        bases.chars()
                .mapToObj(x -> get((char) x))
                .filter(Objects::nonNull)
                .forEach(MutableInt::increment);

    }

    /**
     * Get the count of a certain base, with n for an unknown base
     *
     * @return int value with the number of times the base occurs in the initial string
     */
    public int getInt(char c) {
        if (containsKey(c)) {
            return get(c).intValue();
        }
        return 0;
    }

    /**
     * Get the percentage of a certain base, with n for an unknown base
     *
     * @return float value with the percentage of the base in the initial string
     */
    public float getPercentage(char c) {
        int total = values().stream().mapToInt(MutableInt::intValue).sum();
        if (total == 0) {
            return 0f;
        }
        if (containsKey(c)) {
            return (float) (getInt(c) * 100) / total;
        }
        return 0f;
    }

    /**
     * Get a string representation of the percentage of a certain base, with n for an unknown base
     *
     * @return string string representing the value with the percentage of the base in the initial string
     */
    public String getPercentageString(char c) {
        double rounded = Math.round(getPercentage(c) * 100) / 100.00d;
        Double result = Double.valueOf(df.format(rounded));
        return Double.toString(result);
    }

    /**
     * return a string representation of the percentages of all the bases in an node with a certain ID.
     *
     * @return string representation of all occurrence-rates of the bases in the BaseCounter.
     */
    @Override
    public String toString() {

        return "A: " + getPercentageString('A') + "%, T: " + getPercentageString('T') + "%, C: " + getPercentageString('C') + "%, G: " + getPercentageString('G') + "%, N: " + getPercentageString('N') + "%";

    }

}
