import java.io.RandomAccessFile;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
import java.io.IOException;
// import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * This class use replacement sort and multiway merge
 * to sort the given AScore and pid file
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 11.10.2019
 */
public class Sorting {
    private MaxHeap<Record> maxHeap;
    private static int bufferSize = 16384;
    private static int numRecordsInHeap = 8192;
    private long currentByte = 0;
    private ByteBuffer inputBuff;
    private ByteBuffer outputBuff;
    private RandomAccessFile inputFile;
    private long fileLength;
    private RandomAccessFile runFile;
    private Record[] records;
    private ArrayList<Run> runs;
    private int numRuns = 0;
    private int numRecords = 0;
    private int numRecordsPerRun = 0;

    private int fileSwapStep = 0;


    /**
     * Create a new sorting object
     * 
     * @param data
     *            the name of the input file
     * @param run
     *            the name of the run file created
     */
    public Sorting(String data, String run) {
        inputBuff = ByteBuffer.allocate(bufferSize);
        outputBuff = ByteBuffer.allocate(bufferSize);
        records = new Record[numRecordsInHeap];
        runs = new ArrayList<Run>();
        try {
            inputFile = new RandomAccessFile(data, "rw");
            fileLength = inputFile.length();
            runFile = new RandomAccessFile(run, "rw");
            initialHeap();
            externSort();
            inputFile.close();
            runFile.close();

            multiwayMerge(data, run);
            runFile = new RandomAccessFile(run, "rw");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    // -----------------------Replacement Sorting--------------------------
    /**
     * This method done the replacement sort process
     */
    public void externSort() {
        while (currentByte < fileLength) {
            fillInputBuff();
            while (!inputBuffEmpty()) {
                if (maxHeap.getSize() != 0) {
                    Record currentMax = maxHeap.getMax();
                    writeToBuff(currentMax);
                    Record nextRecord = getNextRecord();
                    maxHeap.replaceSelection(nextRecord, currentMax);
                }
                else {
                    Long end = (long)(numRecords * 16) - 1;
                    Long start = end + 1 - (long)(numRecordsPerRun * 16);
                    Run run = new Run(start, end, numRecordsPerRun);
                    numRecordsPerRun = 0;
                    runs.add(run);
                    numRuns++;
                    maxHeap.setSize(numRecordsInHeap);
                    maxHeap.buildheap();
                    Record currentMax = maxHeap.getMax();
                    writeToBuff(currentMax);
                    Record nextRecord = getNextRecord();
                    maxHeap.replaceSelection(nextRecord, currentMax);
                }
            }
        }
        if (!outputBuffEmpty()) {
            outBuffToFile();
        }
        int count = checkHeapArraySize();
        maxHeap.setSize(count);
        maxHeap.buildheap();
        if (!runs.isEmpty()) {
            Long end = (long)(numRecords * 16) - 1;
            Long start = end + 1 - (long)(numRecordsPerRun * 16);
            Run run = new Run(start, end, numRecordsPerRun);
            numRuns++;
            numRecordsPerRun = 0;
            runs.add(run);
        }
        while (maxHeap.getSize() != 0) {
            Record currentMax = maxHeap.removemax();
            writeToBuff(currentMax);
        }
        Long end = (long)(numRecords * 16) - 1;
        Long start = end + 1 - (long)(numRecordsPerRun * 16);
        Run run = new Run(start, end, numRecordsPerRun);
        numRecordsPerRun = 0;
        runs.add(run);
        numRuns++;

        if (!outputBuffEmpty()) {
            outBuffToFile();
        }

    }


    /**
     * This method gets the size of the heap array
     * 
     * @return The size of the heap array
     */
    private int checkHeapArraySize() {
        int count = 0;
        for (int i = 0; i < numRecordsInHeap; i++) {
            if (records[i] != null) {
                count++;
            }
        }
        return count;
    }


    /**
     * This method build an initial 8 blocks
     * heap before the start of the replacement sort
     */
    private void initialHeap() {
        int count = 0;
        fillInputBuff();
        while (count < numRecordsInHeap) {
            if (inputBuffEmpty()) {
                fillInputBuff();
                Record record = this.getNextRecord();
                records[count] = record;
                count++;
            }
            else {
                Record record = this.getNextRecord();
                records[count] = record;
                count++;
            }
        }
        maxHeap = new MaxHeap<Record>(records, numRecordsInHeap,
            numRecordsInHeap);

    }


    /**
     * Write the contents that in the output buffer
     * to run file
     */
    private void outBuffToFile() {
        byte[] output = outputBuff.array();
        try {
            runFile.write(output);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        outputBuff.clear();
    }


    /**
     * This method gets the next record that is in the input file
     * 
     * @return The next record
     */
    private Record getNextRecord() {
        long pid = inputBuff.getLong();
        double score = inputBuff.getDouble();
        Record next = new Record(pid, score);
        return next;
    }


    /**
     * This method writes a record to the output buffer
     * 
     * @param record
     *            the record to be wrote to the output buffer
     */
    private void writeToBuff(Record record) {
        if (outputBuff.position() == outputBuff.limit()) {
            outBuffToFile();
        }
        outputBuff.putLong(record.getPid());
        outputBuff.putDouble(record.getScore());
        numRecords++;
        numRecordsPerRun++;
    }


    /**
     * Check whether the input buffer is empty
     * 
     * @return return true of the input buffer is empty,
     *         otherwise, return false
     */
    private boolean inputBuffEmpty() {
        return inputBuff.position() == inputBuff.limit();
    }


    /**
     * Check whether the output buffer is empty
     * 
     * @return return true of the output buffer is empty,
     *         otherwise, return false
     */
    private boolean outputBuffEmpty() {
        return outputBuff.position() != outputBuff.limit();
    }


    /**
     * This method fills the input buffer with
     * 1 block of input data
     */
    private void fillInputBuff() {
        byte[] input = new byte[bufferSize];
        try {
            inputFile.read(input);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        inputBuff = ByteBuffer.wrap(input);
        currentByte = currentByte + bufferSize;
    }


    // ---------------------------Multiway Merge-------------------------

    /**
     * This method takes the output file of the replacement sort
     * and merge all the runs get in the replacement sort with
     * multiway merge
     * 
     * @param data
     *            The name of the input file
     * @param run
     *            The name of the run file created
     * @throws IOException
     *             throws exception while the file is not found
     */
    public void multiwayMerge(String data, String run) throws IOException {

        fileSwapStep = (int)Math.ceil(runs.size() / 64.0) + 1;

        int countSwap = 0;
        while (countSwap < fileSwapStep) {
            if (countSwap % 2 == 0) {
                inputFile = new RandomAccessFile(run, "rw");
                runFile = new RandomAccessFile(data, "rw");
            }
            else {
                inputFile = new RandomAccessFile(data, "rw");
                runFile = new RandomAccessFile(run, "rw");
            }
            merge();
            inputFile.close();
            runFile.close();
            countSwap++;
        }
    }


    /**
     * This method merge certain number of runs to a sorted bigger run
     * 
     * @throws IOException
     *             throws exception while the file cannot be read
     */
    public void merge() throws IOException {
        int totalSteps = (int)Math.ceil(runs.size() / 8.0); // 2
        int countStep = 0;
        int numOfUnsortRun = numRuns; // 9
        int currNumRuns = 0;
        ArrayList<Run> newRuns = new ArrayList<Run>();
        numRecords = 0;

        while (countStep < totalSteps) {
            ArrayList<Integer> recordsReference = buildRecordReference(
                numOfUnsortRun, countStep);
            ArrayList<Integer> totalRecords = buildTotalRecord(numOfUnsortRun,
                countStep);
            int[] blockNum = new int[numOfUnsortRun];
            // ----------------initial heap-------------------------
            int initHeapSize = 0;
            if (numOfUnsortRun < 8) {
                initHeapSize = numOfUnsortRun * 1024;
            }
            else {
                initHeapSize = numRecordsInHeap;
            }
            int count = 0;
            int runIdx = countStep * 8;
            blockNum[0]++;
            long currPos = runs.get(runIdx).getstart();
            inputFile.seek(currPos);
            fillInputBuff2();
            while (count < initHeapSize) {
                if (inputBuffEmpty()) {
                    runIdx++;
                    blockNum[runIdx - countStep * 8]++;
                    inputFile.seek(runs.get(runIdx).getstart());
                    fillInputBuff2();
                    Record record = this.getNextRecord2(runIdx + 1);
                    records[count] = record;
                    count++;
                }
                else {
                    Record record = this.getNextRecord2(runIdx + 1);
                    records[count] = record;
                    count++;
                }
            }
            maxHeap.setSize(initHeapSize);
            maxHeap.buildheap();

            // ----------------initial heap-------------------------

            // ---------------Heap sorting--------------------------
            while (maxHeap.getSize() != 0) {

                Record currentMax = maxHeap.removemax();
                int indexOfRun = currentMax.getRun() - 1; //
                int indexOfReference = indexOfRun - countStep * 8;
                recordsReference.set(indexOfReference, recordsReference.get(
                    indexOfReference) - 1);
                totalRecords.set(indexOfReference, totalRecords.get(
                    indexOfReference) - 1);
                writeToBuff(currentMax);

                // -----------------when the block is done------------------
                if (recordsReference.get(indexOfReference) == 0) {
                    int remainRecords = totalRecords.get(indexOfReference);

                    if (remainRecords >= 1024) {
                        long nextStart = runs.get(indexOfRun).getstart()
                            + blockNum[indexOfReference] * 1024 * 16;
                        inputFile.seek(nextStart);
                        fillInputBuff2();
                        blockNum[indexOfReference]++;

                        for (int i = 0; i < 1024; i++) {
                            Record newRecord = this.getNextRecord2(indexOfRun
                                + 1);
                            maxHeap.insert(newRecord);
                        }
                        recordsReference.set(indexOfReference, 1024);
                    }
                    else if (remainRecords > 0 && remainRecords < 1024) {
                        long nextStart = runs.get(indexOfRun).getstart()
                            + blockNum[indexOfReference] * 1024 * 16;
                        inputFile.seek(nextStart);
                        fillInputBuff2(remainRecords * 16);
                        blockNum[indexOfReference]++;
                        for (int i = 0; i < remainRecords; i++) {
                            Record newRecord = this.getNextRecord2(indexOfRun
                                + 1);
                            maxHeap.insert(newRecord);
                        }
                        recordsReference.set(indexOfReference, remainRecords);

                    }
                    else {
                        numOfUnsortRun--;
                    }
                }
                // -----------------when the block is done------------------
            }
            if (!outputBuffEmpty()) {
                outBuffToFile();
            }
            Long end = (long)(numRecords * 16) - 1;
            Long start = end + 1 - (long)(numRecordsPerRun * 16);
            Run run = new Run(start, end, numRecordsPerRun);
            currNumRuns++;
            numRecordsPerRun = 0;
            newRuns.add(run);
            countStep++;
            // ---------------Heap sorting--------------------------
        }
        numRuns = currNumRuns;
        runs = newRuns;
    }


    /**
     * This method create an arraylist that contains the total number
     * of records in each run
     * 
     * @return the arraylist contaning total number of records
     *         in each run
     */
    private ArrayList<Integer> buildTotalRecord(int runNum, int step) {
        ArrayList<Integer> tr = new ArrayList<Integer>();
        int lowerBound = step * 8;
        for (int i = lowerBound; i < lowerBound + runNum; i++) {
            tr.add(runs.get(i).getNumRecord());
        }
        return tr;
    }


    /**
     * This method creates an arraylist of the number of records in the block
     * of each run that is currently in the heap
     * 
     * @param runNum
     *            the number of runs to be merged to a bigger run
     * @param step
     *            The number of steps that have taken in the multiway merge
     *            process
     * @return the arraylist created
     */
    private ArrayList<Integer> buildRecordReference(int runNum, int step) {
        ArrayList<Integer> rr = new ArrayList<Integer>();
        int lowerBound = step * 8;
        for (int i = lowerBound; i < lowerBound + runNum; i++) {
            int currNumRecord = runs.get(i).getNumRecord();
            if (currNumRecord < 1024) {
                rr.add(currNumRecord);
            }
            else {
                rr.add(1024);
            }
        }
        return rr;
    }


    /**
     * This method gets the next record
     * 
     * @param runNum
     *            the run number
     * @return
     */
    private Record getNextRecord2(int runNum) {
        long pid = inputBuff.getLong();
        double score = inputBuff.getDouble();
        Record next = new Record(pid, score);
        next.setRun(runNum);
        return next;
    }


    /**
     * This method reads one block of
     * data from the input file and put
     * it into input buffer
     */
    private void fillInputBuff2() {
        byte[] input = new byte[bufferSize];
        try {
            inputFile.read(input);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        inputBuff = ByteBuffer.wrap(input);
    }


    // overload
    /**
     * This method reads one block of
     * data from the input file and put
     * it into input buffer
     * 
     * @param size
     *            the number of bytes to be read from
     *            the input file
     */
    private void fillInputBuff2(int size) {
        byte[] input = new byte[size];
        try {
            inputFile.read(input);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        inputBuff = ByteBuffer.wrap(input);
    }


    /**
     * This method gets the number of swaps
     * of input file and run file to be taken
     * 
     * @return the number of swaps
     */
    public int getFileSwap() {
        return this.fileSwapStep;
    }


    /**
     * This method gets the input buffer
     * 
     * @return the input buffer
     */
    public ByteBuffer getInputBuffer() {
        return inputBuff;
    }

}
