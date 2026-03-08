
// TaskE.java
package task1.task;

import java.util.Random;
import task1.heap.BinaryHeap;

public class TaskE {

    public static String run(){
        /** Task 1e execution

        Investigates priority queues by comparing the cost of
        deleteMin vs insert on both trees (insert-built and buildHeap-built).
        Measures average time per operation over many repetitions.
        */

        int n = 10000;
        int ops = 5000;
        Random rand = new Random(42);

        int[] input = new int[n];
        for(int i = 0; i < n; i++){
            input[i] = rand.nextInt(n * 10);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Task 1e – Priority Queue: deleteMin vs insert\n\n");
        sb.append(String.format("Heap size: %d, Operations measured: %d\n\n", n, ops));
        sb.append(String.format("%-30s %18s %18s\n", "Operation", "Tree 1 (Insert)", "Tree 2 (BuildHeap)"));
        sb.append("-".repeat(68)).append("\n");

        // Measure insert on Tree 1
        BinaryHeap t1Insert = clone(n + ops + 10, input, true);
        long start = System.nanoTime();
        for(int i = 0; i < ops; i++){
            t1Insert.insert(rand.nextInt(n * 10));
        }
        long insertTime1 = System.nanoTime() - start;

        // Measure insert on Tree 2
        BinaryHeap t2Insert = clone(n + ops + 10, input, false);
        rand = new Random(42);  // reset for same values
        start = System.nanoTime();
        for(int i = 0; i < ops; i++){
            t2Insert.insert(rand.nextInt(n * 10));
        }
        long insertTime2 = System.nanoTime() - start;

        // Measure deleteMin on Tree 1
        BinaryHeap t1Delete = clone(n + ops + 10, input, true);
        start = System.nanoTime();
        for(int i = 0; i < ops; i++){
            t1Delete.deleteMin();
        }
        long deleteTime1 = System.nanoTime() - start;

        // Measure deleteMin on Tree 2
        BinaryHeap t2Delete = clone(n + ops + 10, input, false);
        start = System.nanoTime();
        for(int i = 0; i < ops; i++){
            t2Delete.deleteMin();
        }
        long deleteTime2 = System.nanoTime() - start;

        sb.append(String.format("%-30s %15d ns %15d ns\n",
                "Insert (" + ops + " ops)", insertTime1, insertTime2));
        sb.append(String.format("%-30s %15d ns %15d ns\n",
                "DeleteMin (" + ops + " ops)", deleteTime1, deleteTime2));
        sb.append(String.format("%-30s %15d ns %15d ns\n",
                "Avg Insert", insertTime1 / ops, insertTime2 / ops));
        sb.append(String.format("%-30s %15d ns %15d ns\n",
                "Avg DeleteMin", deleteTime1 / ops, deleteTime2 / ops));

        sb.append("\nConclusion:\n");
        sb.append("Insert is O(log n) worst case but often O(1) average.\n");
        sb.append("DeleteMin is always O(log n) since the new root must\n");
        sb.append("percolate down through the entire height of the tree.\n");
        sb.append("Therefore deleteMin is typically more expensive than insert.\n");

        return sb.toString();
    }

    private static BinaryHeap clone(int capacity,
                                     int[] input, boolean useInsert){
        /** create a copy of a heap

        Rebuilds a heap using the same method as the original
        so measurements start from identical state.
        */
        BinaryHeap copy = new BinaryHeap(capacity);
        if(useInsert){
            for(int v : input){
                copy.insert(v);
            }
        } else {
            copy.buildHeap(input);
        }
        return copy;
    }
}
