import student.TestCase;

/**
 * This method tests the method
 * in the Record class
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 11.10.2019
 */
public class RecordTest extends TestCase {
    private Record record1;


    /**
     * Set up the test cases
     */
    public void setUp() {
        record1 = new Record(12345, 30.5);
    }


    /**
     * Test the setRun and getRun method
     */
    public void testRun() {
        record1.setRun(1);
        assertEquals(1, record1.getRun());
    }


    /**
     * Test the getpid and setpid method
     */
    public void testPid() {
        record1.setPid(54321);
        boolean flag = false;
        if (record1.getPid() == 54321) {
            flag = true;
        }
        assertTrue(flag);
    }


    /**
     * Test the setAScore and getAScore method
     */
    public void testAScore() {
        record1.setScore(40.5);
        assertEquals(0, Double.compare(40.5, record1.getScore()));
    }


    /**
     * Test the compareTo method
     */
    public void testCompareTo() {
        Record record2 = new Record(54321, 50.5);
        assertEquals(-1, record1.compareTo(record2));
        assertEquals(1, record2.compareTo(record1));
        record2.setScore(30.5);
        assertEquals(0, record1.compareTo(record2));
    }


    /**
     * Test the toString method
     */
    public void testToString() {
        assertEquals("Pid: 12345 ; Score: 30.5\n", record1.toString());
    }

}
