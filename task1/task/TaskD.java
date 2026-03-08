
// TaskD.java
package task1.task;

import java.util.Random;
import task1.heap.BinaryHeap;

public class TaskD {

    public static String run(){
        /** Task 1d execution

        Measures the time complexity of both algorithms (insert one-by-one
        and buildHeap) using increasing input sizes: 100, 1000, 10000, 100000.
        */

        int[] sizes = {100, 1000, 10000, 100000};
        StringBuilder sb = new StringBuilder();
        sb.append("Task 1d – Complexity Measurement\n\n");
        sb.append(String.format("%-10s %20s %20s\n", "N", "Insert one-by-one", "BuildHeap"));
        sb.append("-".repeat(52)).append("\n");

        for(int n : sizes){
            // Generate random input
            Random rand = new Random(42);
            int[] input = new int[n];
            for(int i = 0; i < n; i++){
                input[i] = rand.nextInt(n * 10);
            }

            // Algorithm 1: Insert one-by-one
            long start1 = System.nanoTime();
            BinaryHeap heap1 = new BinaryHeap(n + 10);
            for(int v : input){
                heap1.insert(v);
            }
            long time1 = System.nanoTime() - start1;

            // Algorithm 2: BuildHeap (linear-time)
            long start2 = System.nanoTime();
            BinaryHeap heap2 = new BinaryHeap(n + 10);
            heap2.buildHeap(input);
            long time2 = System.nanoTime() - start2;

            sb.append(String.format("%-10d %17d ns %17d ns\n", n, time1, time2));
        }

        sb.append("\nInsert one-by-one: O(n log n) worst case\n");
        sb.append("BuildHeap:         O(n) using bottom-up construction\n");

        return sb.toString();
    }
}
