package poc.treecomponents;

import java.util.LinkedList;

public class ValueNode extends BaseNode{

	String	valueName;

	double	numericContribution;
	LinkedList<DimensionNode>	dimChildren;
	String	fullRepresentation;
	//double	[]	metrics;
	
	public ValueNode (String valName, int stage, double totalAgg, String full ) {
			this.valueName = valName;
			this.fullRepresentation = full;
			this.dimChildren = new LinkedList<DimensionNode>();
			stageNumber = stage;
			this.numericContribution = totalAgg;
	}

	
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valName) {
		this.valueName = valName;
	}

	public double getNumericContribution() {
		return numericContribution;
	}
	public void setNumericContribution(double numericContribution) {
		this.numericContribution = numericContribution;
	}
	public LinkedList<DimensionNode> getDimChildren() {
		return dimChildren;
	}

	public void addDimChild(DimensionNode dn) {
		this.dimChildren.add(dn);
	}
	
	public String getFullRepresentation() {
		return fullRepresentation;
	}
	
	public void setFullRepresentation(String fullRepresentation) {
		this.fullRepresentation = fullRepresentation;
	}
//	public double[] getMetrics() {
//		return metrics;
//	}
//	public void setMetrics(double[] metrics) {
//		this.metrics = metrics;
//	}
	
	
}
