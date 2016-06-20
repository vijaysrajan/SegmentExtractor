package poc.treecomponents;
import java.util.LinkedList;

public class DimensionNode extends BaseNode {
	private String dimensionName;

	private double numericContribution;
	private LinkedList<ValueNode> valChildren;
	
	public DimensionNode ( int stage, String dimName, double totalMetric){
		dimensionName = dimName;
		stageNumber = stage;
		numericContribution = totalMetric;
		valChildren = new LinkedList<ValueNode>();
	}
	
	public String getDimensionName() {
		return dimensionName;
	}
	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	public double getNumericContribution() {
		return numericContribution;
	}
	public void setNumericContribution(double numericContribution) {
		this.numericContribution = numericContribution;
	}
	public LinkedList<ValueNode> getValChildren() {
		return valChildren;
	}
	public void addValNode(ValueNode valNode) {
		this.valChildren.add(valNode);
	}
}
