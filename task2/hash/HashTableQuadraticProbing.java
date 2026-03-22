// HashTableQuadraticProbing.java

package task2.hash;

public class HashTableQuadraticProbing {

    private final Integer[] table;
    private final HashFunction hashFunction;

    public HashTableQuadraticProbing(int size) {
    /**
     * Constructor that initializes the hash table using quadratic probing.
     *
     * In quadratic probing, collisions are resolved by moving forward
     * using quadratic steps instead of linear steps.
     *
     * Instead of:
     * index + 1
     * index + 2
     * index + 3
     *
     * the algorithm uses:
     *
     * index + 1^2
     * index + 2^2
     * index + 3^2
     *
     * This reduces clustering problems that occur in linear probing.
     */

        table = new Integer[size];
        hashFunction = new HashFunction(size);

    }

    public int insert(int value) {
    /**
     * Inserts a value using quadratic probing.
     *
     * Algorithm:
     * 1. Compute the hash index.
     * 2. If the slot is free, insert the value.
     * 3. If collision occurs, try:
     *
     * index + 1^2
     * index + 2^2
     * index + 3^2
     *
     * until a free slot is found.
     *
     * Example:
     *
     * value = 6173
     * hash = 6173 mod 10 = 3
     *
     * if index 3 occupied:
     *
     * try index = 3 + 1^2 = 4
     * try index = 3 + 2^2 = 7
     * try index = 3 + 3^2 = 12 mod 10 = 2
     *
     * Returns the inserted index, or -1 if no slot can be found.
     */

        int index = hashFunction.hash(value);
        int initialIndex = index;
        int i = 1;
        int checked = 1;

        while (table[index] != null && checked < table.length) {
            index = (initialIndex + (i * i)) % table.length;
            i++;
            checked++;
        }
        if (table[index] != null) {
            return -1;
        }

        table[index] = value;

        return index;

    }

    public Integer[] getTable() {
    /**
     * Returns the hash table for visualization in the GUI.
     */

        return table;

    }

    public void clear() {
    /**
     * Clears the hash table.
     */

        for (int i = 0; i < table.length; i++) {

            table[i] = null;

        }

    }

}
