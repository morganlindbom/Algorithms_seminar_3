// HashTableLinearProbing.java

package task2.hash;

public class HashTableLinearProbing {

    private final Integer[] table;
    private final HashFunction hashFunction;

    public HashTableLinearProbing(int size) {
    /**
     * Constructor that initializes the hash table using linear probing.
     *
     * In linear probing, only one value can exist in each slot.
     * If a collision occurs, the algorithm searches sequentially
     * for the next available slot.
     *
     * Example:
     * hash index = 3
     * index 3 occupied
     * try index 4
     * try index 5
     * until empty slot found.
     */

        table = new Integer[size];
        hashFunction = new HashFunction(size);

    }

    public int insert(int value) {
    /**
     * Inserts a value using linear probing.
     *
     * The algorithm:
     * 1. Compute hash index.
     * 2. If slot empty -> insert.
     * 3. If collision -> move forward linearly until empty slot found.
     *
     * Example:
     * value = 6173
     * hash = 6173 mod 10 = 3
     *
     * If index 3 already contains 1323,
     * the algorithm checks:
     *
     * index 4
     * index 5
     * index 6
     *
     * until an empty slot is found.
     */

        int index = hashFunction.hash(value);

        while (table[index] != null) {

            index = (index + 1) % table.length;

        }

        table[index] = value;

        return index;

    }

    public Integer[] getTable() {
    /**
     * Returns the hash table so the GUI can display it.
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