
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
// import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.nio.ByteBuffer;

/**
 * a class for reading the given command file.
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 11.10.2019
 */
public class DataFileParser {

    private StudentManager3 sm = new StudentManager3();
    private InputStream waf;
    private final String runFileName = "myRun.bin";
    private static int bufferSize = 16384;
    @SuppressWarnings("unused")
    private Sorting sort;
    private ByteBuffer buffer;


    /**
     * The constructor for reading the input file.
     * 
     * @param dataName
     *            the name of student data file
     * @param inputBin
     *            the name of the input file
     * @param fileSwap
     *            the number of swaps of the input file and the run file
     * @param sort
     *            the object that sort the input file
     */
    public DataFileParser(
        String dataName,
        String inputBin,
        int fileSwap,
        Sorting sort) {

        dataStudenteReader(dataName);
        try {
            if (fileSwap % 2 == 0) {
                waf = new FileInputStream(runFileName);
            }
            else {
                waf = new FileInputStream(inputBin);
            }
            printResult();
            waf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method gets the student manager that stores
     * the student information of VT students
     * 
     * @return returns the student manager
     */
    public StudentManager3 getSm() {
        return sm;
    }


    /**
     * This method reads the student data file
     * 
     * @param dataName
     *            The name of the student data file
     */
    public void dataStudenteReader(String dataName) {
        ArrayList<Integer> ptrList = new ArrayList<Integer>();
        ArrayList<Long> pidList = new ArrayList<Long>();
        ArrayList<String> firstNameList = new ArrayList<String>();
        ArrayList<String> middleNameList = new ArrayList<String>();
        ArrayList<String> lastNameList = new ArrayList<String>();
        StringBuffer sBuffer = new StringBuffer();
        byte[] titleByte = new byte[10];
        byte[] dummyByte = new byte[8];
        try {
            RandomAccessFile raf = new RandomAccessFile(dataName, "rw");
            ptrList.add((int)raf.getFilePointer());
            raf.seek(ptrList.get(0));
            raf.read(titleByte);
            @SuppressWarnings("unused")
            String titleStr = new String(titleByte);
            long currPtr = 0;
            int numOfRecord = raf.readInt();
            while (currPtr < raf.length()) {
                pidList.add(raf.readLong());
                byte currByte;
                sBuffer = new StringBuffer();
                while ((currByte = raf.readByte()) != (byte)36) {
                    sBuffer = sBuffer.append((char)currByte);
                }
                firstNameList.add(sBuffer.toString());
                sBuffer = new StringBuffer();
                while ((currByte = raf.readByte()) != (byte)36) {
                    sBuffer.append((char)currByte);
                }
                middleNameList.add(sBuffer.toString());
                sBuffer = new StringBuffer();
                while ((currByte = raf.readByte()) != (byte)36) {
                    sBuffer.append((char)currByte);
                }
                lastNameList.add(sBuffer.toString());
                raf.read(dummyByte);
                currPtr = raf.getFilePointer();

            }
            raf.close();
            for (int i = 0; i < numOfRecord; i++) {
                Student newStudent;
                if (middleNameList.get(i).length() == 0) {
                    newStudent = new Student(new Name(firstNameList.get(i),
                        lastNameList.get(i)), pidList.get(i));
                }
                else {
                    Name newName = new Name(firstNameList.get(i), lastNameList
                        .get(i), middleNameList.get(i));

                    newStudent = new Student(newName, pidList.get(i));
                }
                sm.addStudent(newStudent);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method prints out the rank of
     * the first 100 VT students
     * 
     * @throws IOException
     *             throws exception if the file cannot be read
     */
    public void printResult() throws IOException {

        int ranking = 1;
        int vtStudentCount = 0;
        int end = fillBuff();
        while (end != -1 && vtStudentCount < 100) {
            while (!buffEmpty() && vtStudentCount < 100) {
                long currPid = buffer.getLong();
                double currAscore = buffer.getDouble();
                if (currPid != 0 && currAscore != 0.0) {
                    if (Long.toString(currPid).substring(0, 3).equals("909")) {
                        long pid = Long.parseLong(Long.toString(currPid)
                            .substring(3, 12));
                        Student currStudent = sm.findStudent(pid);
                        if (currStudent != null) {
                            System.out.println(currPid + ", " + currStudent
                                .getName() + " at rank " + ranking
                                + " with Ascore " + currAscore);
                        }
                        vtStudentCount++;
                    }
                }
                ranking++;
            }
            end = fillBuff();

        }
    }


    /**
     * This method fills the buffer
     * 
     * @return returns the number of data read
     */
    private int fillBuff() {
        byte[] input = new byte[bufferSize];
        int flag = 0;
        try {
            flag = waf.read(input);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        buffer = ByteBuffer.wrap(input);
        return flag;
    }


    /**
     * Check if the buffer is empty
     * 
     * @return return true if the buffer is empty,
     *         otherwise, return false
     */
    private boolean buffEmpty() {
        return buffer.position() == buffer.limit();
    }

}
