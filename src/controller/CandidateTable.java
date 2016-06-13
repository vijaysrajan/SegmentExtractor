package controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;

import java.util.Set;

public class CandidateTable {
	private LinkedHashSet<CandidateTableElement>  table = new LinkedHashSet<CandidateTableElement>();
	public CandidateTable() {
		
	}
	
	public void appendToCandidateTable(  CandidateTable t ) {
		table.addAll(t.getTable());
	}
	
	public void addToCandidateTable(String line, CandidateTableElement ancestor, String metricName) throws Exception {
		table.add(new CandidateTableElement(line, ancestor, metricName));
	}

	//scan table to find the best dim for a given stage  --select statement like
	public void scanAndCleanTableForSpecifiedStageOld(int stage) {
		//String retDim = null;
		LinkedHashMap<String,Double> hsetToFindBestCandidate = new LinkedHashMap<String,Double>();
		for (CandidateTableElement ce : table){
			if ((ce.getStageNum() != stage)) {
				continue;
			}
			//else
			Double d = hsetToFindBestCandidate.get(ce.getGroupingKey());
			if ( d == null) { 
				hsetToFindBestCandidate.put(ce.getGroupingKey(), ce.getMaximixationKPI() );
			} else {
				hsetToFindBestCandidate.put(ce.getGroupingKey(), d + ce.getMaximixationKPI() );
			}
		}
		
//		for (Map.Entry<String, Double> me : hsetToFindBestCandidate.entrySet() ) {
//			System.out.println("me.getKey() =" + me.getKey() + ",    me.getValue() = " + me.getValue() );
//		}
		
		String maxDim = null;
		Double maxVal = null;
		LinkedHashMap<String,Double> llMaxDimKeys = new LinkedHashMap<String,Double>();
		for (Map.Entry<String, Double> me : hsetToFindBestCandidate.entrySet()) {
		    String key = me.getKey();
		    if (key.equals(maxDim) == true) {
		    	llMaxDimKeys.put(maxDim,maxVal);
		    	maxDim = null;
		    	maxVal = null;
		    }
		    Double value = me.getValue();
		    if ( (maxDim == null) && (maxVal == null)) {
		    	maxDim = key;
		    	maxVal = value;
		    } else {
		    	if (maxVal.doubleValue() < value.doubleValue() ) {
		    		maxVal = value;
		    		maxDim = key;
		    	}
		    }
		}
		for (Map.Entry<String, Double> me : llMaxDimKeys.entrySet() ) {
			for (CandidateTableElement ce : table){
				System.out.println("ce.getGroupingKey() = " + ce.getGroupingKey());
				System.out.println("me.getKey() = " + me.getKey());
				if (ce.getGroupingKey().equals(me.getKey()) == true) {
					ce.setIsToBeConsideredTrue();
				}
			}
		}
		table.removeIf(p-> p.isToBeConsidered() == false);
		
	}
	
	
	public void scanAndCleanTableForSpecifiedStage(int stage) {
		//String retDim = null;
		LinkedHashMap<String, LinkedHashMap<String,Double>> hsetToFindBestCandidate = new LinkedHashMap<String, LinkedHashMap<String,Double>>();
		
		for (CandidateTableElement ce : table){
			if ((ce.getStageNum() != stage)) {
				continue;
			}
			//else
			LinkedHashMap<String,Double> toCount  = hsetToFindBestCandidate.get(ce.getAncestryStr());
			if ((toCount == null)) {
				LinkedHashMap<String,Double> majorVal = new LinkedHashMap<String,Double>();
				majorVal.put(ce.getGroupingKey(),ce.getMaximixationKPI() );
				hsetToFindBestCandidate.put(ce.getAncestryStr(), majorVal);
			} else {
				//LinkedHashMap<String,Double> majorVal = hsetToFindBestCandidate.get(ce.getAncestry());
				Double d = toCount.get(ce.getGroupingKey());
				if ( d == null) { 
					toCount.put(ce.getGroupingKey(), ce.getMaximixationKPI() );
				} else {
					toCount.put(ce.getGroupingKey(), d + ce.getMaximixationKPI() );
				}
			}
		}
		
//		for (Map.Entry<String, LinkedHashMap<String,Double>> me : hsetToFindBestCandidate.entrySet() ) {
//			for (Map.Entry<String, Double> me2 :  me.getValue().entrySet()) {
//				System.out.println("me.getKey() =" + me.getKey() + ",    me2.getKey() = " + me2.getKey() + ",    me2.getValue() = " + me2.getValue());
//			}
//		}
		
		LinkedHashMap<String, LinkedHashMap<String,Double>> temp = new LinkedHashMap<String, LinkedHashMap<String,Double>>();
		for (Map.Entry<String, LinkedHashMap<String,Double>> me2 : hsetToFindBestCandidate.entrySet() ) {
			String maxDim = null;
			Double maxVal = null;
			LinkedHashMap<String,Double> llMaxDimKeys = new LinkedHashMap<String,Double>();
			for (Map.Entry<String, Double> me :  me2.getValue().entrySet()) {
				 String key = me.getKey();
				 Double value = me.getValue();
				 if ( (maxDim == null) && (maxVal == null)) {
				    maxDim = key;
				    maxVal = value;
				 } else {
				    if (maxVal.doubleValue() < value.doubleValue() ) {
				    	maxVal = value;
				    	maxDim = key;
				    }
				 }
			}
			//System.out.println("maxDim = " + maxDim + ",    maxVal = " + maxVal);
			llMaxDimKeys.put(maxDim, maxVal);
			temp.put(me2.getKey(), llMaxDimKeys);
		}
		

		for (Map.Entry<String, LinkedHashMap<String,Double>> me2 : temp.entrySet() ) {
			for (Map.Entry<String, Double> me :  me2.getValue().entrySet()) {
				for (CandidateTableElement ce : table){
					//System.out.println("ce.getGroupingKey() = " + ce.getGroupingKey());
					//System.out.println("me.getKey() = " + me.getKey());
					if ( (ce.getGroupingKey().equals(me.getKey()) == true) && (ce.getStageNum() == stage)){
						ce.setIsToBeConsideredTrue();
					}
				}
			}
		}
		table.removeIf(p-> p.isToBeConsidered() == false);
	}
	
	

	public LinkedHashSet<CandidateTableElement> getTable() {
		return table;
	}

	//print table
	public void printTable() {
		for (CandidateTableElement cte : table) {
			System.out.println(cte.toString());
		}
	}

}
