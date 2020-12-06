import java.util.Iterator;
import java.util.Stack;

/**
 * Represents a Binary Search Tree for a generic type of data, in this case,
 * k is our generic type to store the comparable key and e is a generic type
 * element to store with the binary node. The binary search tree is
 * connected by Binary Nodes that have store a generic key and a generic element
 * data.
 * Reference: this class is referred and modified from the current CS3114 BST
 * powerpoint.
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 10.16.2019
 * @param <K>
 *            the key that is used for comparing binary nodes.
 * @param <E>
 *            the element that stores the generic data.
 */

public class BinarySearchTree<K extends Comparable<? super K>, E>
    implements Iterable<E> {

    private BinaryNode<K, E> root;
    private int nodeCount;


    /**
     * Constructs an empty tree.
     */
    public BinarySearchTree() {
        root = null;
        nodeCount = 0;
    }


    /**
     * Insert into the tree.
     *
     * @param k
     *            the key to insert.
     * @param e
     *            the element to store.
     */
    public void insert(K k, E e) {
        root = inserthelp(root, k, e);
        // System.out.println(root);
        nodeCount++;
    }


    /**
     * Find an item in the tree.
     *
     * @param k
     *            the key to search for.
     * @return the matching item or null if not found.
     */
    public E find(K k) {
        return findhelp(this.getRoot(), k);
    }


    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
        nodeCount = 0;
    }


    /**
     * True if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }


    /**
     * Internal method to insert a value into a subtree.
     *
     * @param rt
     *            the root to start with
     * @param k
     *            the key to insert.
     * @param e
     *            the element to insert
     * @return the new root of the subtree.
     */
    private BinaryNode<K, E> inserthelp(BinaryNode<K, E> rt, K k, E e) {
        if (rt == null) {
            return new BinaryNode<K, E>(k, e);
        }
        else if (rt.key().compareTo(k) > 0) {
            rt.setLeft(inserthelp(rt.getLeft(), k, e));
        }
        else {
            rt.setRight(inserthelp(rt.getRight(), k, e));
        }
        return rt;
    }


    /**
     * Internal method to remove a specified item from a subtree.
     *
     * @param rt
     *            the root to start with
     * @param k
     *            the key to insert.
     * @param e
     *            the element to insert
     * 
     * @return the new root of the subtree.
     */
    private BinaryNode<K, E> removehelp(BinaryNode<K, E> rt, K k, E e) {

        // If there's no more subtree to examine
        if (rt == null) {
            return null;
        }

        // if value should be to the left of the root
        if (rt.key().compareTo(k) > 0) {
            rt.setLeft(removehelp(rt.getLeft(), k, e));
        }
        // if value should be to the right of the root
        else if (rt.key().compareTo(k) < 0) {
            rt.setRight(removehelp(rt.getRight(), k, e));
        }
        // If value is on the current node
        else {
            if (rt.getElement().equals(e)) {
                if (rt.getLeft() == null) {
                    return rt.getRight();
                }
                else if (rt.getRight() == null) {
                    return rt.getLeft();
                }
                else {
                    BinaryNode<K, E> temp = findMin(rt.getRight());
                    rt.setElement(temp.getElement());
                    rt.setKey(temp.key());
                    rt.setRight(deletemin(rt.getRight()));
                }
            }
            else {
                rt.setRight(removehelp(rt.getRight(), k, e));
            }
        }
        return rt;
    }


    /**
     * Remove a specified item from a subtree.
     *
     * @param rt
     *            the root to start with
     * @param k
     *            the key to insert.
     * @param e
     *            the element to insert
     */
    public void remove(BinaryNode<K, E> rt, K k, E e) {
        root = removehelp(rt, k, e);
        nodeCount--;
    }


    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param rt
     *            the node that roots the tree.
     * @return node containing the smallest item.
     */
    private BinaryNode<K, E> findMin(BinaryNode<K, E> rt) {
        if (rt.getLeft() == null) {
            return rt;
        }
        else {
            return findMin(rt.getLeft());
        }
    }


    /**
     * Internal method to delete the min node.
     *
     * @param rt
     *            the node that roots the tree.
     * @return node containing the smallest item.
     */
    private BinaryNode<K, E> deletemin(BinaryNode<K, E> rt) {
        if (rt.getLeft() == null) {
            return rt.getRight();
        }
        else {
            rt.setLeft(deletemin(rt.getLeft()));
            return rt;
        }
    }


    /**
     * Internal method to help finding the node
     *
     * @param rt
     *            the node that roots the tree.
     * @param k
     *            the key to help finding the node
     * @return the element of the found node.
     */
    private E findhelp(BinaryNode<K, E> rt, K k) {
        if (rt == null) {
            return null; // Not found
        }
        else if (rt.key().compareTo(k) > 0) {
            // Search in the left subtree
            return findhelp(rt.getLeft(), k);
        }
        else if (rt.key().compareTo(k) < 0) {
            // Search in the right subtree
            return findhelp(rt.getRight(), k);
        }
        else {
            return rt.getElement(); // Match
        }
    }


    /**
     * The number of node in the bst.
     * 
     * @return the size
     */
    public int getSize() {
        return nodeCount;
    }


    /**
     * Return the root of the bst
     * 
     * @return BinaryNode<K> the root
     */
    public BinaryNode<K, E> getRoot() {
        return root;
    }


    /**
     * set root for the bst.
     * 
     * @param newRoot
     *            new root
     * @return BinaryNode<K> new root
     */
    public BinaryNode<K, E> setRoot(BinaryNode<K, E> newRoot) {
        root = newRoot;
        return root;
    }


    /**
     * Gets an in-order string representation of the tree
     * If the tree holds 5
     * / \
     * 2 6
     * \
     * 3
     * It would print (2, 3, 5, 6)
     * 
     * @return an in-order string representation of the tree
     */
    @Override
    public String toString() {
        if (root == null) {
            return "";
        }
        else {
            return root.toString();
        }
    }


    /**
     * Inorder iterator to iterate through the bst by key.
     * 
     * @author anali99
     *
     */
    private class PreIterator implements Iterator<E> {
        private Stack<BinaryNode<K, E>> treeStack;


        /**
         * constructor, creates a new PreIterator object
         */
        PreIterator() {
            treeStack = new Stack<BinaryNode<K, E>>();
            pushNode();
        }


        /**
         * This method push the element onto the stack
         */
        public void pushNode() {
            BinaryNode<K, E> node = root;
            while (node != null) {
                treeStack.push(node);
                node = node.getLeft();
            }
        }


        /**
         * This method return the next element in the binary search tree
         * 
         * @return The next element in the binary search tree
         */
        public E next() {
            BinaryNode<K, E> current = null;
            E element = null;
            if (!treeStack.empty()) {
                current = treeStack.pop();
                element = current.getElement();
                if (current.getRight() != null) {
                    current = current.getRight();
                    while (current != null) {
                        treeStack.push(current);
                        current = current.getLeft();
                    }
                }

            }
            return element;
        }


        /**
         * This method determines whether there is a next element
         * 
         * @return return true if there is a next element,
         *         otherwise return false.
         */
        public boolean hasNext() {

            return !treeStack.isEmpty();
        }

    }


    /**
     * The method creates a new PreIterator object
     */
    @Override
    public Iterator<E> iterator() {
        return new PreIterator();
    }

}
