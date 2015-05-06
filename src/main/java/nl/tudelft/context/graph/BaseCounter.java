package nl.tudelft.context.graph;

import nl.tudelft.context.data.MutableInt;

import java.util.HashMap;

/**
 * @author Jasper Nieuwdorp <jaspernieuwdorp@hotmail.com>
 * @version 1.0
 * @since 06-05-2015
 */
public class BaseCounter extends HashMap<Character, MutableInt> {

    /**
     * Constructor for the baseCounter
     *
     * @param s String with the dna sequence
     */
    public BaseCounter(String s) {
        put('A', new MutableInt());
        put('T', new MutableInt());
        put('C', new MutableInt());
        put('G', new MutableInt());
        put('N', new MutableInt());
        for (char i : s.toCharArray()) {
            MutableInt count = get(i);
            if (count != null) {
                count.increment();
            }
        }
    }

    /**
     * Get the count of a certain base, with n for an unknown base
     *
     * @return int value with the number of times the base occurs in the initial string
     */
    public int getInt(char c) {
        if (containsKey(c)) {
            return get(c).get();
        }
        return 0;
    }

    /**
     * Get the percentage of a certain base, with n for an unknown base
     *
     * @return float value with the percentage of the base in the initial string
     */
    public float getPercentage(char c) {
        int total = values().stream().mapToInt(MutableInt::get).sum();
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
     * @return string string representing thev alue with the percentage of the base in the initial string
     */
    public String getPercString(char c) {
        double rounded = Math.round(getPercentage(c) * 100) / 100.00d;
        return Double.toString(rounded);
    }

    /**
     * return a string reprecentation of the percentages of the bases in an node with a certain ID>
     */
    @Override
    public String toString() {

        String pA = getPercString('A');
        String pT = getPercString('T');
        String pC = getPercString('C');
        String pG = getPercString('G');
        String pN = getPercString('N');

        return "A: " + pA + "%, T: " + pT + " %, C: " + pC + " %, G: " + pG + " %, N: " + pN;

    }

}
