import student.TestCase;

/**
 * Test the MaxHeap class
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 11.10.2019
 */
public class MaxHeapTest extends TestCase {
    // private Record[] records = new Record[5];
    private MaxHeap<Record> mh;
    private Record newRecord;
    private Record maxRecord;


    /**
     * Set up the test cases
     */
    public void setUp() throws Exception {
        Record[] records = new Record[5];
        records[0] = new Record(293932321, 22.33);
        records[1] = new Record(223232431, 33.4);
        records[2] = new Record(333234231, 44.5);
        records[3] = new Record(333224231, 54.5);
        maxRecord = new Record(733234231, 64.5);
        records[4] = maxRecord;
        newRecord = new Record(222222222, 77.7);
        mh = new MaxHeap<Record>(records, records.length, 10);
        mh.buildheap();
    }


    /**
     * Test the heapsize method
     */
    public void testHeapsize() {
        assertEquals(5, mh.heapsize());
    }


    /**
     * Test the isLeaf method
     */
    public void testIsLeaf() {
        assertTrue(mh.isLeaf(2));
        assertTrue(mh.isLeaf(3));
        assertTrue(mh.isLeaf(4));
    }


    /**
     * Test the leftChild method
     */
    public void testLeftchild() {
        assertEquals(3, mh.leftchild(1));
        assertEquals(1, mh.leftchild(0));
        assertEquals(-1, mh.leftchild(2));
    }


    /**
     * Test the rightChild method
     */
    public void testRightchild() {
        assertEquals(2, mh.rightchild(0));
        assertEquals(4, mh.rightchild(1));
        assertEquals(-1, mh.rightchild(2));
    }


    /**
     * Test the parent method
     */
    public void testParent() {
        assertEquals(-1, mh.parent(0));
        assertEquals(1, mh.parent(3));
        assertEquals(1, mh.parent(4));
        assertEquals(0, mh.parent(2));
        assertEquals(0, mh.parent(1));
    }


    /**
     * Test the insert method
     */
    public void testInsert() {
        mh.removemax();
        mh.insert(newRecord);
        assertEquals(5, mh.heapsize());
    }


    /**
     * Test the removeMax method
     */
    public void testRemovemax() {
        assertEquals(mh.removemax(), maxRecord);
    }


    /**
     * Test the remove method
     */
    public void testRemove() {
        assertEquals(mh.remove(0), maxRecord);
    }


    /**
     * Test the getMax method
     */
    public void testGetMax() {
        boolean flag = false;
        if (Double.compare(64.5, mh.getMax().getScore()) == 0) {
            flag = true;
        }
        assertTrue(flag);
    }

// public void testReplaceSelection() {
// System.out.println("Replace: " + mh.replaceSelection(new Record(22222,
// 100.0),
// new Record(3333, 20)));
// System.out.println(mh.getMax());
// System.out.println("Replace: " + mh.replaceSelection(new Record(22222, 20.0),
// new Record(3333, 64.5)));
// System.out.println(mh.heapsize());
// }

}
