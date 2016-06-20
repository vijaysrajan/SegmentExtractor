package poc.lineparser;

public class Metric  {
	private String  metricName;
	private double  metricVal;

	public Metric(String name, double val) {
		metricName = name;
		metricVal  = val;
	}
	
	public String getMetricName() {
		return metricName;
	}

	public double getMetricVal() {
		return metricVal;
	}

	public double setValue(double v) throws Exception {
		double retVal = -1;
		if ( (metricName == null) || (metricName.equals("")) ) {
			throw new Exception("Cannot set value for unknown metric.");
		} else  {
			retVal = getMetricVal();
			metricVal = v;
		}
		return retVal;
	}
	
	public String toString() {
		StringBuilder retVal = new StringBuilder();
		retVal.append(metricName);
		retVal.append("-");
		retVal.append(metricVal);
		return retVal.toString();
	}

	
	
}