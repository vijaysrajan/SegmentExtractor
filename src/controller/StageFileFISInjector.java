package controller;

import java.io.BufferedReader;
import java.io.FileReader;

import controller.CandidateTable;
import lineparser.StageLines;

public class StageFileFISInjector {
	
	private CandidateTable candidateTable = null;
	private boolean isBufferedReaderClosed = false;
	private BufferedReader stageFileBufferedReader = null  ;
	private int stageNum = 0;

	public StageFileFISInjector(String fileName, int stg) throws Exception {
		this.setStageNum(stg);
		candidateTable = new CandidateTable();
		stageFileBufferedReader= new BufferedReader(new FileReader(fileName));
	}
	
	public String getLineFromFile() throws Exception {
		String retVal = stageFileBufferedReader.readLine();
		if ( (retVal == null)  ) {
			stageFileBufferedReader.close();
		}
//		System.out.println(retVal);
		return retVal;
	}

//	public void injectLines() throws Exception{
//		String line = getLineFromFile();
//		while (line != null) {
//			candidateTable.addToCandidateTable(line);
//			line = getLineFromFile();
//		} 
//	}
	
	public void injectLines0() throws Exception{
	String line = getLineFromFile();
	while (line != null) {
		candidateTable.addToCandidateTable(line,null,"beacon");
		line = getLineFromFile();
	} 
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
	
	public static void main(String [] args) throws Exception {
		StageFileFISInjector sf = new StageFileFISInjector(args[0], 1);
		sf.injectLines0();
		sf.getCandidateTable().printTable();
	}
	
}
