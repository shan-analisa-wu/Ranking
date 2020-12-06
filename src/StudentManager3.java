/**
 * The student manager. This class adds students read from cvs and
 * binary file into an array of student and insert students to specific
 * sections
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 10.20.2019
 */

public class StudentManager3 {
    private BinarySearchTree<Long, Student> studentList;


    /**
     * Create a new StudentManager object
     */
    public StudentManager3() {
        studentList = new BinarySearchTree<Long, Student>(); // key = pid
    }


    /**
     * This method add a student to the student bst
     * 
     * @param newStudent
     *            The student to be added
     */
    public void addStudent(Student newStudent) {
        studentList.insert(newStudent.getPid(), newStudent);
    }


    /**
     * This method get the list of student
     * 
     * @return The list of students
     */
    public BinarySearchTree<Long, Student> getStudentList() {
        return this.studentList;
    }


    /**
     * This method checks whether there is any students
     * in the students list
     * 
     * @return return true if there is any students in the student list,
     *         otherwise, return false
     */
    public boolean isEmpty() {
        return (studentList.getSize() == 0);
    }


    /**
     * This method clear the studentList
     */
    public void clear() {
        studentList = new BinarySearchTree<Long, Student>();
    }


    /**
     * This method gets how many students are in the student list
     * 
     * @return The number of students that are in the student list
     */
    public int size() {
        return studentList.getSize();
    }


    /**
     * This method finds whether a particular student is in the student list
     * 
     * @param pid
     *            The pid of the student to be found
     * @return return the student that is found
     */
    public Student findStudent(long pid) {
        Student foundStudent = studentList.find(pid);
        return foundStudent;
    }


    /**
     * This method prints out the student list
     */
    public void printManager() {
        System.out.println(studentList);
    }
}
