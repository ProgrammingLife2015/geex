package nl.tudelft.context.graph;

import org.apache.commons.lang.mutable.MutableInt;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Jasper Nieuwdorp <jaspernieuwdorp@hotmail.com>
 * @version 1.1
 * @since 06-05-2015
 */
public class BaseCounter extends HashMap<Character, MutableInt> {

    static DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
    protected int baseSize;

    /**
     * Constructor for the baseCounter
     *
     * @param bases String with the dna sequence
     */
    public BaseCounter(String bases) {
        baseSize = bases.length();
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
        return get(c).intValue();
    }

    /**
     * Get the percentage of a certain base, with n for an unknown base
     *
     * @return float value with the percentage of the base in the initial string
     */
    public float getPercentage(char c) {
        return (float) (getInt(c) * 100) / baseSize;
    }

    /**
     * Get a string representation of the percentage of a certain base, with n for an unknown base
     *
     * @return string string representing the value with the percentage of the base in the initial string
     */
    public String getPercentageString(char c) {
        double result = Double.valueOf(df.format(getPercentage(c)));
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
