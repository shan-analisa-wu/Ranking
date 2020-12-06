/**
 * Represents a Maxheap for a generic type of data, in this case,
 * e is a generic type element to store with the binary node. The
 * Maxheap uses an array of nodes.
 * Reference: this class is referred and modified from the current CS3114
 * openDSA
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 10.16.2019
 * @param <E>
 *            the element that stores the generic data.
 */
public class MaxHeap<E extends Comparable<? super E>> {
    private E[] records; // Pointer to the heap array
    private int capacity; // Maximum size of the heap
    private int currSize; // Number of things in heap


    /**
     * Create a maxHeap
     * 
     * @param heapArray
     *            the node array
     * @param num
     *            the number of element in the max heap
     * @param max
     *            the maximum number of elements that the max heap can store
     */
    public MaxHeap(E[] heapArray, int num, int max) {
        records = heapArray;
        currSize = num;
        capacity = max;
        buildheap();
    }


    /**
     * Return current size of the heap
     * 
     * @return the size of the max heap
     */
    public int heapsize() {
        return currSize;
    }


    /**
     * set the array of the max heap
     * 
     * @param records
     *            the array to be set
     */
    public void setRecords(E[] records) {
        this.records = records;
    }


    /**
     * Is pos a leaf position?
     * 
     * @param pos
     *            a specific position to be determined
     * @return
     *         return true if a node is leaf
     */
    public boolean isLeaf(int pos) {
        return (pos >= currSize / 2) && (pos < currSize);
    }


    /**
     * Return position for left child of pos
     * 
     * @param pos
     *            the position
     * @return the position for left child of pos
     */
    public int leftchild(int pos) {
        if (pos >= currSize / 2) {
            return -1;
        }
        return 2 * pos + 1;
    }


    /**
     * Return position for right child of pos
     * 
     * @param pos
     *            the position
     * @return the position for right child of pos
     */
    public int rightchild(int pos) {
        if (pos >= (currSize - 1) / 2) {
            return -1;
        }
        return 2 * pos + 2;
    }


    /**
     * Return position for parent
     * 
     * @param pos
     *            the position
     * @return the position for the parent of pos
     */
    public int parent(int pos) {
        if (pos <= 0) {
            return -1;
        }
        return (pos - 1) / 2;
    }


    /**
     * This method build the max heap
     * 
     */
    public void buildheap() {
        for (int i = currSize / 2 - 1; i >= 0; i--) {
            siftdown(i);
        }
    }


    /**
     * Insert a specific element into heap
     * 
     * @param val
     *            the element to be inserted
     */
    public void insert(E val) {
        if (currSize >= capacity) {
            return;
        }
        int curr = currSize++;
        records[curr] = val; // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0) && (records[curr].compareTo(records[parent(
            curr)]) > 0)) {
            swap(records, curr, parent(curr));
            curr = parent(curr);
        }
    }


    /**
     * This method do the replacement selection
     * 
     * @param inputVal
     *            the input value from the input buffer
     * @param lastElementOfBuff
     *            the last element that is outputted
     *            to the output buffer
     */
    public void replaceSelection(E inputVal, E lastElementOfBuff) {
        if (inputVal != null) {
            records[0] = inputVal;
            if (lastElementOfBuff != null) {
                if (records[0].compareTo(lastElementOfBuff) > 0) {
                    swap(records, 0, currSize - 1);
                    currSize--; // heap size - 1
                    siftdown(0);
                }
                else {
                    siftdown(0);
                }
            }
        }
    }


    /**
     * This method gets the maximum value that stored
     * in the heap
     * 
     * @return the maximum value of the heap
     */
    public E getMax() {
        return records[0];
    }


    /**
     * Put element in its correct place
     */
    private void siftdown(int pos) {
        if ((pos < 0) || (pos >= currSize)) {
            return; // Illegal position
        }
        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if ((j < (currSize - 1)) && (records[j].compareTo(records[j
                + 1]) < 0)) {
                j++; // j is now index of child with greater value
            }
            if (records[pos].compareTo(records[j]) >= 0) {
                return;
            }
            swap(records, pos, j);
            pos = j; // Move down
        }
    }

// public void


    /**
     * This method removed the maximum value in the
     * max heap
     * 
     * @return the maximum value in the max heap
     */
    public E removemax() { // Remove minimum value
        if (currSize == 0) {
            return null; // Removing from empty heap
        }
        swap(records, 0, --currSize); // Swap minimum with last value
        if (currSize != 0) { // Not on last element
            siftdown(0); // Put new heap root val in correct place
        }
        return records[currSize];
    }


    /**
     * Remove element at specified position
     * 
     * @param pos
     *            The position of the element in
     *            the heap array to be removed
     * @return the element that got removed
     */
    public E remove(int pos) {
        if ((pos < 0) || (pos >= currSize)) {
            return null; // Illegal heap position
        }
        if (pos == (currSize - 1)) {
            currSize--; // Last element, no work to be done
        }
        else {
            swap(records, pos, --currSize); // Swap with last value
            // If we just swapped in a small value, push it up
            while ((pos > 0) && (records[pos].compareTo(records[parent(
                pos)]) > 0)) {
                swap(records, pos, parent(pos));
                pos = parent(pos);
            }
            if (currSize != 0) {
                siftdown(pos); // If it is big, push down
            }
        }
        return records[currSize];
    }


    /**
     * This method swaps two element in the heap array
     * 
     * @param arr
     *            the heap array
     * @param firstIndex
     *            the index of the first element to be swapped
     * @param secondIdx
     *            the index of the secong element to be swapped
     */
    private void swap(E[] arr, int firstIndex, int secondIdx) {
        E tmp = arr[firstIndex];
        arr[firstIndex] = arr[secondIdx];
        arr[secondIdx] = tmp;
    }


    /**
     * This method gets the current size of the heap
     * 
     * @return the size of the heap
     */
    public int getSize() {
        return currSize;
    }


    /**
     * This method sets the size of a heap
     * 
     * @param sz
     *            The size of the heap to be set
     */
    public void setSize(int sz) {
        this.currSize = sz;
    }
}
