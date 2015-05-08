package nl.tudelft.context.graph;

import org.apache.commons.collections.bag.HashBag;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author Jasper Nieuwdorp <jaspernieuwdorp@hotmail.com>
 * @version 1.2
 * @since 06-05-2015
 */
public class BaseCounter extends HashBag {

    static DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

    /**
     * Constructor for the baseCounter
     *
     * @param bases String with the dna sequence
     */
    public BaseCounter(String bases) {
        bases.chars()
                .mapToObj(x -> (char) x)
                .forEach(this::add);
    }

    /**
     * Get the percentage of a certain base, with N for an unknown base
     *
     * @return float value with the percentage of the base in the initial string
     */
    public float getPercentage(char c) {
        return (float) (getCount(c) * 100) / size();
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
