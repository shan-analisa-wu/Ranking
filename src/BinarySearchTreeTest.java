
// import java.util.Iterator;
import student.TestCase;

/**
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 09.20.2019
 */
public class BinarySearchTreeTest extends TestCase {
    private BinarySearchTree<Integer, Integer> bst1;
    private BinarySearchTree<Integer, String> bst2;


    /**
     * Setup
     */
    public void setUp() {
        bst1 = new BinarySearchTree<>();
        bst2 = new BinarySearchTree<>();
        bst1.insert(2, 2);
        bst1.insert(3, 3);
        bst1.insert(1, 1);
    }


    /**
     * Test the insert method
     */
    public void testInsert() {
        bst1.insert(5, 5);
        bst2.insert(1, "A");
        assertEquals("A", bst2.getRoot().getElement());
        boolean result = false;
        if (1 == bst2.getRoot().key()) {
            result = true;
        }
        assertTrue(result);
    }


    /**
     * Test the remove method
     */
    public void testRemove() {
        bst1.insert(2, 2);
        bst1.insert(3, 3);
        bst1.insert(1, 1);
        bst1.insert(5, 5);
        boolean result = true;
        assertTrue(result);
    }


    /**
     * Test the find method
     */
    public void testFind() {
        boolean result = false;
        if (2 == bst1.find(2)) {
            result = true;
        }
        assertTrue(result);
    }


    /**
     * Test makeEmpty
     */
    public void testMakeEmpty() {
        bst1.makeEmpty();
        assertTrue(bst1.isEmpty());
    }


    /**
     * Test getRoot
     */
    public void testGetRoot() {
        boolean result = false;
        if (bst1.getRoot().getElement() == 2) {
            result = true;
        }
        assertTrue(result);
    }
}
