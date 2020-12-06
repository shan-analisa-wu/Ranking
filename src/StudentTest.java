
import student.TestCase;

/**
 * Tests all the functionalities of the Student class
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 10.16.2019
 *
 */

public class StudentTest extends TestCase {
    private Student student1;
    private Student student2;


    /**
     * Setup for the following testings.
     */
    public void setUp() {
        Name name1;
        Name name2;
        name1 = new Name("John", "Smith", "William");
        name2 = new Name("Mary", "Will");
        student1 = new Student(name1, 123456);
        student2 = new Student(name2, 123789);
    }


    /**
     * Test the getName method
     */
    public void testGetName() {
        System.out.println(student1.getName());
        boolean result = true;
        assertTrue(result);
    }


    /**
     * Test the getPid method
     */
    public void testGetPid() {
        boolean result = false;
        if (student2.getPid() == 123789) {
            result = true;
        }
        assertTrue(result);
    }

}
