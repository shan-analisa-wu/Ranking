import student.TestCase;

/**
 * This method tests the method
 * in the Run class
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 11.10.2019
 */
public class RunTest extends TestCase {
    private Run run1;


    /**
     * Set up the test cases
     */
    public void setUp() {
        run1 = new Run(Long.valueOf(123456789), Long.valueOf(987654321), 40);
    }


    /**
     * Test the getstart and setstart method
     */
    public void testStart() {
        Long l = new Long(3456);
        run1.setStart(3456);
        run1.getstart();
        assertTrue(run1.getstart().equals(l));
    }


    /**
     * Test the getend method
     */
    public void testGetEnd() {
        Long l = new Long(987654321);
        assertTrue(run1.getEnd().equals(l));
    }


    /**
     * Test the getNumRecord method
     */
    public void testGetNumRecord() {
        assertEquals(40, run1.getNumRecord());
    }


    /**
     * Test the toString method
     */
    public void testToString() {
        assertEquals("Start: 123456789-- End: 987654321 -- numOfRecord: 40",
            run1.toString());
    }

}
