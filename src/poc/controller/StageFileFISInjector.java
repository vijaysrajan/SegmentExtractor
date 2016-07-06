package poc.controller;

import poc.lineparser.DimVal;
import poc.lineparser.LineFIS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class StageFileFISInjector {

    private static final boolean CandidateTableElement = false;
    private CandidateTable candidateTable = null;
    private boolean isBufferedReaderClosed = false;
    private BufferedReader stageFileBufferedReader = null;
    private int stageNum = 0;

    public StageFileFISInjector(String fileName, int stg) throws Exception {
        this.setStageNum(stg);
        candidateTable = new CandidateTable();
        stageFileBufferedReader = new BufferedReader(new FileReader(fileName));
    }

    public static void main(String[] args) throws Exception {

        StageFileFISInjector sf = new StageFileFISInjector(args[1], 1);
        sf.injectLines0(args[0]);
        //sf.getCandidateTable().printTable();
        sf.getCandidateTable().scanAndCleanTableForSpecifiedStage(1);
        //sf.getCandidateTable().printTable();

        sf.openNewStageFile(2, args[2]);
        sf.injectLines1(args[0]);
        //System.out.println("-----------");
        System.out.flush();
        sf.getCandidateTable().scanAndCleanTableForSpecifiedStage(2);
        //sf.getCandidateTable().printTable();

        sf.openNewStageFile(3, args[3]);
        sf.injectLines1(args[0]);
        //System.out.println("-----------");
        sf.getCandidateTable().scanAndCleanTableForSpecifiedStage(3);
        //sf.getCandidateTable().printTable();

        sf.openNewStageFile(4, args[4]);
        sf.injectLines1(args[0]);
        //System.out.println("-----------");
        sf.getCandidateTable().scanAndCleanTableForSpecifiedStage(4);
        sf.getCandidateTable().printTable();

    }

    public void openNewStageFile(int stg, String fileName) throws Exception {
        this.setStageNum(stg);
        stageFileBufferedReader.close();
        stageFileBufferedReader = new BufferedReader(new FileReader(fileName));

    }

    public String getLineFromFile() throws Exception {
        String retVal = stageFileBufferedReader.readLine();
        if ((retVal == null)) {
            stageFileBufferedReader.close();
        }
//		System.out.println(retVal);
        return retVal;
    }

    public void injectLines0(String s) throws Exception {
        String line = getLineFromFile();
        while (line != null) {
            candidateTable.addToCandidateTable(line, null, s);
            line = getLineFromFile();
        }
    }

    public void injectLines1(String s) throws Exception {
        String line = getLineFromFile();
        CandidateTable temp = new CandidateTable();
        while (line != null) {
            for (CandidateTableElement ce : this.getCandidateTable().getTable()) {
                LineFIS lf = new LineFIS(line);
                ArrayList<DimVal> retDimVal = new ArrayList<DimVal>();
                boolean isPresent = lf.getExtraDimVal(new LineFIS(ce.getLineAsIs()), retDimVal);
                if ((isPresent == true) && (retDimVal.size() == 1)) {
                    temp.addToCandidateTable(line, ce, s);
                }
            }
            line = getLineFromFile();
        }
        candidateTable.appendToCandidateTable(temp);
    }

    public int getStageNum() {
        return stageNum;
    }

    public void setStageNum(int stageNum) {
        this.stageNum = stageNum;
    }

    public CandidateTable getCandidateTable() {
        return candidateTable;
    }

    public void closeReader() {
        try {
            stageFileBufferedReader.close();
        } catch (Exception e) {

        }
    }

}
