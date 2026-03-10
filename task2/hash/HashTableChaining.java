// HashTableChaining.java

package task2.hash;

import java.util.ArrayList;
import java.util.List;

public class HashTableChaining {

    private List<Integer>[] table;
    private HashFunction hashFunction;

    @SuppressWarnings("unchecked")
    public HashTableChaining(int size) {
    /**
     * Constructor that initializes the hash table using separate chaining.
     *
     * Each index in the hash table contains a list. When multiple values
     * map to the same index (collision), they are stored inside that list.
     *
     * Example:
     * index 3 -> [1323, 6173]
     *
     * This makes insertion simple because collisions do not require
     * searching for another slot.
     */

        table = new ArrayList[size];

        for (int i = 0; i < size; i++) {
            table[i] = new ArrayList<>();
        }

        hashFunction = new HashFunction(size);

    }

    public int insert(int value) {
    /**
     * Inserts a value into the hash table.
     *
     * The method calculates the hash index using the hash function.
     * The value is then appended to the list at that index.
     *
     * Example:
     * value = 6173
     *
     * hash = 6173 mod 10 = 3
     *
     * If index 3 already contains [1323],
     * the list becomes:
     *
     * index 3 -> [1323, 6173]
     *
     * The method returns the index where the value was inserted.
     */

        int index = hashFunction.hash(value);

        table[index].add(value);

        return index;

    }

    public List<Integer>[] getTable() {
    /**
     * Returns the full hash table structure.
     *
     * This allows the GUI to read and visualize
     * the contents of each index.
     */

        return table;
    }

}