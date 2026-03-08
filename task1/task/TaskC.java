
// TaskC.java
package task1.task;

import task1.heap.BinaryHeap;

public class TaskC {

    public static String run(){
        /** Task 1c execution

        Traverses both heaps (insert one-by-one and buildHeap) using
        all four traversal strategies: in-order, pre-order, post-order,
        and level-order.
        */

        int[] input = {10,12,1,14,6,5,8,15,3,9,7,4,11,13,2};

        // Tree 1: built by inserting one at a time
        BinaryHeap tree1 = new BinaryHeap(50);
        for(int v : input){
            tree1.insert(v);
        }

        // Tree 2: built using linear-time buildHeap
        BinaryHeap tree2 = new BinaryHeap(50);
        tree2.buildHeap(input);

        StringBuilder sb = new StringBuilder();
        sb.append("Task 1c – Traversals\n");
        sb.append("Input: [");
        for(int i = 0; i < input.length; i++){
            if(i > 0) sb.append(", ");
            sb.append(input[i]);
        }
        sb.append("]\n");

        sb.append("\n═══ Tree 1 (Insert one-by-one) ═══\n");
        sb.append("Array:       [").append(tree1.levelOrder()).append("]\n");
        sb.append("Level-order: ").append(tree1.levelOrder()).append("\n");
        sb.append("Pre-order:   ").append(tree1.preOrder()).append("\n");
        sb.append("In-order:    ").append(tree1.inOrder()).append("\n");
        sb.append("Post-order:  ").append(tree1.postOrder()).append("\n");

        sb.append("\n═══ Tree 2 (BuildHeap) ═══\n");
        sb.append("Array:       [").append(tree2.levelOrder()).append("]\n");
        sb.append("Level-order: ").append(tree2.levelOrder()).append("\n");
        sb.append("Pre-order:   ").append(tree2.preOrder()).append("\n");
        sb.append("In-order:    ").append(tree2.inOrder()).append("\n");
        sb.append("Post-order:  ").append(tree2.postOrder()).append("\n");

        return sb.toString();
    }
}
