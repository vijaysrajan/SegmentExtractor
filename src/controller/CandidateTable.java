package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CandidateTable {
	private ArrayList<CandidateTableElement>  table = new ArrayList<CandidateTableElement>();
	public CandidateTable() {
		
	}
	
	public void addToCandidateTable(String line, CandidateTableElement ancestor, String metricName) throws Exception {
		table.add(new CandidateTableElement(line, ancestor, metricName));
	}

	//scan table to find the best dim for a given stage  --select statement like
//	public String scanTableForStage(int stage) {
//		String retDim = null;
//		HashMap<String,Double> hsetToFindBestCandidate = new HashMap<String,Double>();
//		for (CandidateTableElement ce : table){
//			
//		}
//		
//		return retDim;
//	}

	//invalidate a row -- may not be needed
	
	//remove all invalidated rows
	
	//print table
	public void printTable() {
		for (CandidateTableElement cte : table) {
			System.out.println(cte.toString());
		}
	}

}
