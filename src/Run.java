/**
 * This method represents a specific run
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 11.10.2019
 */
public class Run {
    private Long start;
    private Long end;
    private int numRecord;


    /**
     * Create a new Run object
     * 
     * @param start
     *            the start of the run
     * @param end
     *            the end of the run
     * @param numRecord
     *            the number of records that is in this run
     */
    public Run(Long start, Long end, int numRecord) {
        this.start = start;
        this.end = end;
        this.numRecord = numRecord;
    }


    /**
     * This method gets the start of the run
     * 
     * @return the start starting byte of the run
     */
    public Long getstart() {
        return start;
    }


    /**
     * This method gets the end of the position
     * 
     * @return the ending byte of the run
     */
    public Long getEnd() {
        return end;
    }


    /**
     * This method sets the starting byte of the run
     * 
     * @param s
     *            the staring byte of the run
     */
    public void setStart(long s) {
        start = s;
    }


    /**
     * This method gets the number of record that is in the run
     * 
     * @return the number of record in the run
     */
    public int getNumRecord() {
        return numRecord;
    }


    /**
     * This method prints the run
     * @return The string formed
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Start: " + start + "-- End: " + end + " -- numOfRecord: "
            + numRecord);
        return str.toString();
    }
}
