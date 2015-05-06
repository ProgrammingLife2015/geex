package nl.tudelft.context.supporting;

/**
 * @author Jasper Nieuwdorp <jaspernieuwdorp@hotmail.com>
 * @version  1.0
 * @since 04-05-2015
 *
 * Used to count, for example in a map.
 * Because this wrapper is used for counting it starts at 0.
 */
public class MutableInt {
    private int value = 0;

    /**
     * Return the integer value of the MutableInt.
     */
    public int get() {
        return value;
    }

    /**
     * Increase the value of the mutableInt by 1.
     */
    public void increment() {
        value++;
    }

    /**
     * Decrease the value of the mutableInt by 1.
     */
    public void decrement() {
        value = value - 1;
    }

    /**
     * set the value of the mutableInt back to 0.
     */
    public void reset() {
        value = 0;
    }

    /**
     * Return a string representation of the mutable integer.
     */
    @Override
    public String toString(){
        return Integer.toString(value);
    }
}

