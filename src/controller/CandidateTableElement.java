package controller;

import java.util.ArrayList;

import lineparser.DimVal;
import lineparser.LineFIS;
import lineparser.Metric;
//import treecomponents.ValueNode;
//import treecomponents.DimensionNode;

public class CandidateTableElement {
	private LineFIS thisLine = null;
	private int stageNum = -1;
	private LineFIS ancestry = null;
	private String ancestryStr = null;
	private String dim = null;
	private String val = null;
	private String maximixationKPIName = null;
	private double maximixationKPIValue = 0;
	private Metric [] KPIs = null;
	private boolean toBeConsidered = true;
	//private ValueNode parentValueNodeInTree;

	public CandidateTableElement(String line, CandidateTableElement ancestor, String metricName) throws Exception{
		thisLine = new LineFIS(line);
		stageNum = thisLine.getStageNum();
		if (stageNum <= 0)  {
			throw new Exception("Stahe Num must be a natural number.");
		} else if ((stageNum == 1) || (ancestor == null) )  {
			ancestry = new LineFIS("");
			ancestry.setStageNum(0);
		} else {
			ancestry = ancestor.getThisLine();
		}
		ancestryStr = ancestry.toString();
		ArrayList<DimVal> v = new ArrayList<DimVal>();
		boolean ret = thisLine.getExtraDimVal(ancestry, v);
		if ( ((v.size() != 1) || (ret == false))  && thisLine.getStageNum() == (ancestor.getStageNum() + 1 ))  {
			//throw new Exception("This line is not a descendent of " + ancestry.toString());
			toBeConsidered = false;
		} else {
			dim = v.get(0).getDim();
			val = v.get(0).getValue();
			toBeConsidered = true;
		}
		KPIs = thisLine.getMetricNameAndValueArray();

		for (Metric m : KPIs) {
			if (m.getMetricName().toLowerCase().equals(metricName.toLowerCase()) == true) {
				this.maximixationKPIValue = m.getMetricVal();
				this.maximixationKPIName= m.getMetricName();
			}
		}
		if (maximixationKPIName == null) {
			throw new Exception("Metric(KPI) name " + metricName + " not found.");
		}

		//parentValueNodeInTree = null;
	}


	public LineFIS getThisLine() {
		return thisLine;
	}

	public int getStageNum() {
		return stageNum;
	}

	public LineFIS getAncestry() {
		return ancestry;
	}
	public String getAncestryStr() {
		return ancestryStr;
	}

	public String getDim() {
		return dim;
	}

	public String getVal() {
		return val;
	}

	public double getMaximixationKPI() {
		return maximixationKPIValue;
	}

	public Metric[] getKPIs() {
		return KPIs;
	}
	
	public boolean isToBeConsidered() {
		return toBeConsidered;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getStageNum());
		sb.append(",");
		sb.append(this.getAncestryStr());
		sb.append(",");
		sb.append(this.getDim());
		sb.append("=");
		sb.append(this.getVal());
		sb.append(",");
		sb.append(this.getThisLine());
		sb.append(",");
		sb.append(this.getMaximixationKPI());
		sb.append(",");
		sb.append(toBeConsidered);
		return sb.toString();
	}
	
}