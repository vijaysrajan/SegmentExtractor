package poc.lineparser;


public class DimVal {

    private String dimensionName;
    private String dimensionValue;

    public DimVal(String d, String v) {
        dimensionName = d;
        dimensionValue = v;
    }

    public String getDim() {
        return dimensionName;
    }

    public String getValue() {
        return dimensionValue;
    }


    public String setValue(String v) throws Exception {
        String retVal = null;
        if ((dimensionName == null) || (dimensionName.equals(""))) {
            throw new Exception("Cannot get Value of empty dimension.");
        } else {
            retVal = getValue();
            dimensionValue = v;
        }
        return retVal;
    }

    @Override
    public String toString() {
        StringBuilder retVal = new StringBuilder();
        retVal.append(dimensionName);
        retVal.append(Character.toString('\001'));
        retVal.append(dimensionValue);
        return retVal.toString();
    }

}

