// InputNumbers.java

package task2.data;

public class InputNumbers {

    private static final int[] numbers = {
        4371,
        1323,
        6173,
        4199,
        4344,
        9679,
        1989
    };

    public static int[] getNumbers() {
    /**
     * Returns the predefined numbers used in Task 2.
     *
     * The assignment specifies a fixed dataset that
     * should be inserted into the hash table.
     *
     * These numbers are used to demonstrate collisions
     * and the behavior of different collision resolution
     * strategies.
     *
     * The numbers are:
     * 4371, 1323, 6173, 4199, 4344, 9679, 1989
     *
     * The GUI will process these numbers sequentially
     * when the Run button is pressed.
     */

        return numbers;
    }

}