/**
 * This class represents a student
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 10.20.2019
 */
public class Student {
    private Name name;
    private long pid;


    /**
     * Create a new student object
     * 
     * @param name
     *            the name of the student
     * @param pid
     *            the pid of the student
     */
    public Student(Name name, long pid) {
        this.name = name;
        this.pid = pid;
    }


    /**
     * This method get the name of a student
     * 
     * @return name The name of a student
     */
    public Name getName() {
        return name;
    }


    /**
     * This command returns the student id of a student
     * 
     * @return pid The pid of a student
     */
    public Long getPid() {
        return pid;
    }


    /**
     * This method print out the student id, full name and the score of a
     * specific student.
     * 
     * @return output The id, name and score of a student
     *         as a string
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (pid < 100000000) {
            String pidStr = String.valueOf(pid);
            int flag = 9 - pidStr.length();
            for (int i = 0; i < flag; i++) {
                str.append("0");
            }
            str.append(pid + ", " + name.getFirstName() + " " + name
                .getLastName());
        }
        else {
            str.append(pid + ", " + name.getFirstName() + " " + name
                .getLastName());
        }

        return str.toString();
    }

}
