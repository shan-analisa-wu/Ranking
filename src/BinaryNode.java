
/**
 * Represents a binary node in a binary search tree.
 * This BinaryNode class is based on the CS3114 course powerpoint.
 * 
 * @param <K>
 *            The type of key contained in the node.
 * @param <E>
 *            The type of element contained in the node.
 * @author Shan Wu, Ko Yat Chan
 * @version 10.16.2019
 */
class BinaryNode<K, E> {
    private K key;
    private E element;
    private BinaryNode<K, E> left;
    private BinaryNode<K, E> right;


    /**
     * Creates a node with no children.
     * 
     */
    BinaryNode() {
        left = null;
        right = null;
    }


    /**
     * Create a node with no children
     * 
     * @param key
     *            the key value of the binary node
     * @param val
     *            the element of the binary node
     */
    BinaryNode(K key, E val) {
        left = null;
        right = null;
        this.key = key;
        this.element = val;
    }


    /**
     * Create a node with children
     * 
     * @param key
     *            the key value of the binary node
     * @param val
     *            the element of the binary node
     * @param l
     *            the left node
     * @param r
     *            the right node
     */
    BinaryNode(K key, E val, BinaryNode<K, E> l, BinaryNode<K, E> r) {
        this.key = key;
        this.element = val;
        left = l;
        right = r;
    }


    /**
     * Get the key value
     * 
     * @return k the key value
     */
    public K key() {
        return key;
    }


    /**
     * Set the key value
     * 
     * @return k the key value
     * @param k
     *            the key value to be set
     */
    public K setKey(K k) {
        key = k;
        return key;
    }


    /**
     * Get the current data value stored in this node.
     * 
     * @return the element
     */
    public E getElement() {
        return element;
    }


    /**
     * Set the data value stored in this node.
     * 
     * @param value
     *            the new data value to set
     */
    public void setElement(E value) {
        element = value;
    }


    /**
     * Get the left child of this node.
     * 
     * @return a reference to the left child.
     */
    public BinaryNode<K, E> getLeft() {
        return left;
    }


    /**
     * Set this node's left child.
     * 
     * @param value
     *            the node to point to as the left child.
     */
    public void setLeft(BinaryNode<K, E> value) {
        left = value;
    }


    /**
     * Get the right child of this node.
     * 
     * @return a reference to the right child.
     */
    public BinaryNode<K, E> getRight() {
        return right;
    }


    /**
     * Set this node's right child.
     * 
     * @param value
     *            the node to point to as the right child.
     */
    public void setRight(BinaryNode<K, E> value) {
        right = value;
    }


    /**
     * Determines whether a node is a leaf
     * node in the tree
     * 
     * @return Return true if the node is a leaf
     *         node, otherwise, return false
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }


    /**
     * Provides an in-order representation of the node
     * 
     * @return a string representation of the node
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (left != null) {
            builder.append(left.toString() + "\n");
        }
        builder.append(element.toString() + " ");
        if (right != null) {
            builder.append(right.toString());
        }
        return builder.toString();
    }
}
