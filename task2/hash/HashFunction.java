// HashFunction.java

package task2.hash;

public class HashFunction {

    private final int tableSize;

    public HashFunction(int tableSize) {
    /**
     * Constructor for the hash function.
     *
     * The hash function depends on the table size.
     * In this assignment the table size is 10, which means
     * the hash function will return values between 0 and 9.
     *
     * Example:
     * 6173 mod 10 = 3
     */

        this.tableSize = tableSize;

    }

    public int hash(int value) {
    /**
     * Computes the hash index for a given value.
     *
     * The assignment specifies the hash function:
     *
     * h(x) = x mod tableSize
     *
     * This means the remainder after dividing the value
     * by the table size determines the index in the hash table.
     *
     * Example:
     * value = 4371
     * tableSize = 10
     *
     * 4371 mod 10 = 1
     *
     * Therefore the value should be inserted at index 1.
     */

        return value % tableSize;

    }

}