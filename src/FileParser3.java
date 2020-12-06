//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * a class for reading the given command file.
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 11.10.2019
 */
public class FileParser3 {

//    /**
//     * A helper function for reading the .txt file
//     * 
//     * @param cmdFileName
//     *            the name of the cmd file.
//     */
//    private void readCmdFile(String cmdFileName) {
//        String line;
//        try {
//            RandomAccessFile raf = new RandomAccessFile(cmdFileName, "rw");
//            while ((line = raf.readLine()) != null) {
//// ce.checkCommands(line);
//            }
//            raf.close();
//        }
//        catch (FileNotFoundException e) {
//            System.err.println("Cannot find the file: " + cmdFileName);
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//            System.err.println("Unable to access the file: " + cmdFileName);
//        }
//    }


    /**
     * This method reads the student data file
     * 
     * @param binName
     *            The name of the student data file
     */
    @SuppressWarnings("unused")
    public void binStudenteReader(String binName) {
        ArrayList<Integer> ptrList = new ArrayList<Integer>();
        ArrayList<Long> pidList = new ArrayList<Long>();
        ArrayList<String> firstNameList = new ArrayList<String>();
        ArrayList<String> middleNameList = new ArrayList<String>();
        ArrayList<String> lastNameList = new ArrayList<String>();
        StringBuffer sBuffer = new StringBuffer();
        byte[] titleByte = new byte[10];
        byte[] dummyByte = new byte[8];
        try {
            RandomAccessFile raf = new RandomAccessFile(binName, "rw");
            ptrList.add((int)raf.getFilePointer());
            raf.seek(ptrList.get(0));
            raf.read(titleByte);
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
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
