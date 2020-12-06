/**
 * This class represent a student record that contains the pid and
 * AScore of a student and which that the record is in
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 11.10.2019
 */
public class Record implements Comparable<Record> {
    private long pid;
    private double ascore;
    private int run;


    /**
     * Create a record object
     * 
     * @param pid
     *            the pid of a student
     * @param ascore
     *            the AScore of a student
     */
    public Record(long pid, double ascore) {
        this.pid = pid;
        this.ascore = ascore;
    }


    /**
     * This method gets which run the record is in
     * 
     * @return the run that the record is in
     */
    public int getRun() {
        return run;
    }


    /**
     * This method sets which run that the record is in
     * 
     * @param run
     *            The run that the record is in
     */
    public void setRun(int run) {
        this.run = run;
    }


    /**
     * This method gets the pid of the student
     * 
     * @return the pid of the student
     */
    public long getPid() {
        return this.pid;
    }


    /**
     * This method gets the AScore of the student
     * 
     * @return the AScore of the student
     */
    public double getScore() {
        return this.ascore;
    }


    /**
     * This method sets the pid of the student
     * 
     * @param pid
     *            the pid to be set
     */
    public void setPid(long pid) {
        this.pid = pid;
    }


    /**
     * This method sets the AScore of the student
     * 
     * @param score
     *            the AScore to be set
     */
    public void setScore(double score) {
        this.ascore = score;
    }


    /**
     * This method compares the AScore of teo student record
     * 
     * @param other
     *            The other student record to be compared with
     * @return return -1 if this student record's score is lower
     *         that the other student record's score, 0 if they are equal,
     *         otherwise, return 1
     */
    public int compareTo(Record other) {
        if (this.ascore < other.getScore()) {
            return -1;
        }
        else if (Double.compare(this.ascore, other.getScore()) > 0) {
            return 1;
        }
        return 0; // else: equal->0
    }


    // for testing
    /**
     * Prints the student record
     * 
     * @return the string formed
     */
    public String toString() {
        StringBuilder str = new StringBuilder("Pid: ");
        str.append(this.getPid() + " ; " + "Score: " + this.ascore + "\n");
        return str.toString();
    }
}
