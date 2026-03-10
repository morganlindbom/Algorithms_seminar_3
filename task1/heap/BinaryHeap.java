
// BinaryHeap.java
package task1.heap;

public class BinaryHeap {

    private final int[] heap;
    private int size;

    public BinaryHeap(int capacity){
        /** constructor for BinaryHeap

        Initializes the heap array and sets the starting size to zero.
        The array is created with capacity + 1 so that index 1 can be used
        as the root of the heap. This simplifies parent and child calculations.
        */
        heap = new int[capacity + 1];
        size = 0;
    }

    public int size(){
        /** returns current heap size

        Provides the number of elements currently stored in the heap.
        */
        return size;
    }

    public int get(int index){
        /** get value at index

        Returns the element stored at a given heap index.
        */
        return heap[index];
    }

    public void insert(int value){
        /** insert element into heap

        Inserts a new value into the heap and restores the heap property
        by performing the percolate-up operation.
        */

        heap[++size] = value;
        int i = size;

        while(i > 1 && heap[i] < heap[i/2]){
            int temp = heap[i];
            heap[i] = heap[i/2];
            heap[i/2] = temp;
            i = i / 2;
        }
    }

    public void deleteMin(){
        /** remove minimum element

        Removes the smallest element from the heap which is located at
        the root (index 1).
        */

        heap[1] = heap[size];
        size--;
        percolateDown(1);
    }

    private void percolateDown(int index){
        /** restore heap order downward

        Moves the element at the specified index downward in the heap
        until the heap property is satisfied again.
        */

        while(index * 2 <= size){

            int child = index * 2;

            if(child + 1 <= size && heap[child + 1] < heap[child]){
                child++;
            }

            if(heap[index] <= heap[child]){
                break;
            }

            int temp = heap[index];
            heap[index] = heap[child];
            heap[child] = temp;

            index = child;
        }
    }

    public void buildHeap(int[] array){
        /** build heap using linear-time algorithm

        Constructs a heap from an existing array using the bottom-up
        heap construction method.
        */

        size = array.length;

        System.arraycopy(array, 0, heap, 1, array.length);

        for(int i = size / 2; i > 0; i--){
            percolateDown(i);
        }
    }

    public int getMin(){
        /** return minimum element

        Returns the root element (minimum) without removing it.
        */
        return heap[1];
    }

    // ---- Traversal methods ----

    public String levelOrder(){
        /** level-order traversal

        Returns elements in array order which corresponds to
        breadth-first (level-order) traversal of the heap tree.
        */
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= size; i++){
            if(i > 1) sb.append(", ");
            sb.append(heap[i]);
        }
        return sb.toString();
    }

    public String preOrder(){
        /** pre-order traversal

        Visits root, then left subtree, then right subtree.
        */
        StringBuilder sb = new StringBuilder();
        preOrder(1, sb);
        return sb.toString();
    }

    private void preOrder(int index, StringBuilder sb){
        if(index > size) return;
        if(sb.length() > 0) sb.append(", ");
        sb.append(heap[index]);
        preOrder(index * 2, sb);
        preOrder(index * 2 + 1, sb);
    }

    public String inOrder(){
        /** in-order traversal

        Visits left subtree, then root, then right subtree.
        */
        StringBuilder sb = new StringBuilder();
        inOrder(1, sb);
        return sb.toString();
    }

    private void inOrder(int index, StringBuilder sb){
        if(index > size) return;
        inOrder(index * 2, sb);
        if(sb.length() > 0) sb.append(", ");
        sb.append(heap[index]);
        inOrder(index * 2 + 1, sb);
    }

    public String postOrder(){
        /** post-order traversal

        Visits left subtree, then right subtree, then root.
        */
        StringBuilder sb = new StringBuilder();
        postOrder(1, sb);
        return sb.toString();
    }

    private void postOrder(int index, StringBuilder sb){
        if(index > size) return;
        postOrder(index * 2, sb);
        postOrder(index * 2 + 1, sb);
        if(sb.length() > 0) sb.append(", ");
        sb.append(heap[index]);
    }

    @Override
    public String toString(){
        /** heap array representation

        Creates a readable string representation of the heap array.
        */

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for(int i = 1; i <= size; i++){
            if(i > 1) sb.append(", ");
            sb.append(heap[i]);
        }

        sb.append("]");
        return sb.toString();
    }
}
