package poc.lineparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LineFIS {
    //FISid fisId;
    int stageNum;

    DimVal[] dimensionNameAndValueArray;
    Metric[] metricNameAndValueArray;
    String[] strRepDimVal;

    public LineFIS(int stg, DimVal[] dvArr, Metric[] mtrcArr) {
        //fisId = f;
        stageNum = stg;
        dimensionNameAndValueArray = dvArr;
        metricNameAndValueArray = mtrcArr;
        stringRepDimVal(dvArr);
    }

    private void stringRepDimVal(DimVal[] dvArr) {
        strRepDimVal = new String[dvArr.length];
        for (int i = 0; i < dvArr.length; i++) {
            strRepDimVal[i] = dvArr[i].toString();
        }
    }

    public LineFIS(String line) throws Exception {
        if (line.equals("") == true) {
            stageNum = 0;
            dimensionNameAndValueArray = null;
            metricNameAndValueArray = null;
            strRepDimVal = null;
            return;
        }

        ////////////////////////////////////////////////////////////////////
        //Split 2^DnetspeedType^BCS_cable^Aos^BWindows Vista^Cbeacon^B26631.0^ATotalOnloadTime^B766009 into
        //      2  and
        //      netspeedType^BCS_cable^Aos^BWindows Vista^Cbeacon^B26631.0^ATotalOnloadTime^B766009
        //   ***Extract the stage and FIS*** ^D
        ////////////////////////////////////////////////////////////////////
        String[] arr1 = splitBySeparator(line, 2, "exactly", Character.toString('\004'));

        ////////////////////////////////////////////////////////////////////
        //Split alreadyCut(2^D) netspeedType^BCS_cable^Aos^BWindows Vista^Cbeacon^B26631.0^ATotalOnloadTime^B766009 into
        //      netspeedType^BCS_cable^Aos^BWindows Vista,
        //      beacon^B26631.0^ATotalOnloadTime^B766009
        //   ***Extract the FIS and separate the metrics *** ^C
        ////////////////////////////////////////////////////////////////////
        String[] arr2 = splitBySeparator(arr1[1], 2, "atmost", Character.toString('\003'));

        ////////////////////////////////////////////////////////////////////
        //Split netspeedType^BCS_cable^Aos^BWindows Vista   alreadyCut(^Cbeacon^B26631.0^ATotalOnloadTime^B766009) into
        //      netspeedType^BCS_cable  and
        //      os^BWindows Vista
        //   ***Extract individual FIS *** ^A
        ////////////////////////////////////////////////////////////////////
        String[] arr3 = splitBySeparator(arr2[0], -1, "atleast", Character.toString('\001'));
        DimVal[] dimValArr = new DimVal[arr3.length];

        for (int i = 0; i < arr3.length; i++) {
            String[] arr4 = splitBySeparator(arr3[i], 2, "exactly", Character.toString('\002'));
            dimValArr[i] = new DimVal(arr4[0], arr4[1]);
        }

        arr3 = splitBySeparator(arr2[1], -1, "atleast", Character.toString('\001'));
        Metric[] metricArr = new Metric[arr3.length];

        for (int i = 0; i < arr3.length; i++) {
            String[] arr4 = splitBySeparator(arr3[i], 2, "exactly", Character.toString('\002'));
            metricArr[i] = new Metric(arr4[0], Double.parseDouble(arr4[1]));
        }
        dimensionNameAndValueArray = dimValArr;
        //fisId = f;
        stageNum = Integer.parseInt(arr1[0]);
        metricNameAndValueArray = metricArr;
        stringRepDimVal(dimValArr);

    }


    public Metric[] getMetricNameAndValueArray() {
        return metricNameAndValueArray;
    }

    public int getStageNum() {
        return stageNum;
    }

    public void setStageNum(int stageNum) {
        this.stageNum = stageNum;
    }


    public String[] getAllDimVal() {
        if (strRepDimVal == null) {
            return new String[0];
        }
        return strRepDimVal;
    }

    private static String[] splitBySeparator(String str, int expectedArraySize, String exactAtLeastAtMost, String separator) throws Exception {
        //String separator = Character.toString('\004');
        //String separator = Character.toString('\003');
        String[] ret = str.split(separator);

        switch (exactAtLeastAtMost.toLowerCase()) {
            case "exactly":
//                		System.out.println("str = " + str);
//                		System.out.println("ret.length =" + ret.length);
//                		System.out.println("expectedArraySize =" + expectedArraySize);
                if (ret.length != expectedArraySize) {
                    throw new Exception("Expecting " + exactAtLeastAtMost + " " + expectedArraySize + separator + "s but got " + (ret.length - 1));
                }
                break;
            case "atleast":
                if (ret.length < (expectedArraySize)) {
                    throw new Exception("Expecting " + exactAtLeastAtMost + " " + expectedArraySize + separator + "s but got " + (ret.length - 1));
                }
                break;
            case "atmost":
                //System.out.println("ret.length = " + ret.length);
                //System.out.println("expectedArraySize = " + expectedArraySize);
                //System.out.println( str );
                if ((ret.length > expectedArraySize)) {
                    throw new Exception("Expecting " + exactAtLeastAtMost + " " + expectedArraySize + separator + "s but got " + ret.length);
                }
                break;
            default:
                throw new Exception("Passed unacceptable value to parameter exactAtLeastAtMost = " + exactAtLeastAtMost + ". Expected values 'exactly', 'atleast' and 'atmost'.");

        }
        return ret;
    }


    public boolean isSubLineInLine(LineFIS subLine) {
        int myLineLength = strRepDimVal.length;
        int subLineLength = subLine.getAllDimVal().length;

        for (int i = 0; i < subLineLength; i++) {
            boolean isPresent = false;
            for (int j = 0; j < myLineLength; j++) {
                if (this.getAllDimVal()[j].equals(subLine.getAllDimVal()[i]) == true) {
                    isPresent = true;
                    break;
                }
            }
            if (isPresent == false) {
                return false;
            }
        }
        return true;
    }

    public DimVal[] getExtraDimValInLongerLineFIS(LineFIS subLine) throws Exception {
        ArrayList<DimVal> retDimValArrayList = new ArrayList<DimVal>();
        int myLineLength = strRepDimVal.length;
        int subLineLength = subLine.getAllDimVal().length;
        //ArrayList<Integer> innerLoopArrayList = new ArrayList<Integer>();

        int outerLoop = 0;
        int innerLoop = 0;
        String[] outerLoopStrArr = null;
        String[] innerLoopStrArr = null;

        if (myLineLength > subLineLength) {
            outerLoop = myLineLength;
            outerLoopStrArr = strRepDimVal;
            innerLoop = subLineLength;
            innerLoopStrArr = subLine.getAllDimVal();
        } else {
            outerLoop = subLineLength;
            outerLoopStrArr = subLine.getAllDimVal();
            innerLoop = myLineLength;
            innerLoopStrArr = strRepDimVal;
        }

        for (int i = 0; i < outerLoop; i++) { // smaller loop
            boolean isPresent = false;
            for (int j = 0; j < innerLoop; j++) {
                if (innerLoopStrArr[j].equals(outerLoopStrArr[i]) == true) {
                    isPresent = true;
                    break;
                }
            }
            if (isPresent == false) {
                String[] arrDimVal1 = outerLoopStrArr[i].split(Character.toString('\001'));
                String dim1 = arrDimVal1[0];
                String val1 = arrDimVal1[1];
                retDimValArrayList.add(new DimVal(dim1, val1));
            }
        }
        DimVal[] arrDV = new DimVal[retDimValArrayList.size()];
        retDimValArrayList.toArray(arrDV);
        return arrDV;
    }


    public boolean getExtraDimVal(LineFIS subLine, ArrayList<DimVal> retDimVal) throws Exception {

        boolean retBool = false;
        Set<String> s1 = new HashSet<String>();
        Collections.addAll(s1, strRepDimVal);

        Set<String> s2 = new HashSet<String>();
        Collections.addAll(s2, subLine.getAllDimVal());
        Set<String> difference1 = new HashSet<String>(s1);
        difference1.removeAll(s2);

        Set<String> difference2 = new HashSet<String>(s2);
        difference2.removeAll(s1);
        if (difference2.isEmpty()) {
            retBool = true;
        }
        difference2.addAll(difference1);
        String[] ret = difference2.toArray(new String[difference2.size()]);

        //DimVal [] arrDV = new DimVal[ret.length];
        for (int i = 0; i < ret.length; i++) {
            String[] tmp = ret[i].split(Character.toString('\001'));
            retDimVal.add(new DimVal(tmp[0], tmp[1]));
        }

        return retBool;
    }

    @Override
    public String toString() {
        if ((strRepDimVal == null) || (strRepDimVal.equals("") == true))
            return "";
        StringBuilder sb = new StringBuilder();
        for (String s : strRepDimVal) {
            sb.append(s);
            if (strRepDimVal[strRepDimVal.length - 1] != s) {
                sb.append(Character.toString('\002'));
            }
        }
        return sb.toString();
    }

}
