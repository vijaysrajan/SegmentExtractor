package lineparser;

public class LineFIS {
	FISid fisId;
	int stageNum;
	DimVal [] dimensionNameAndValueArray;
	Metric [] metricNameAndValueArray;
	String [] strRepDimVal; 
	
	public LineFIS(FISid f, int stg, DimVal [] dvArr, Metric [] mtrcArr) {
		fisId = f;
		stageNum = stg;
		dimensionNameAndValueArray = dvArr;
		metricNameAndValueArray = mtrcArr;
		stringRepDimVal(dvArr);
	}
	
	private void stringRepDimVal(DimVal [] dvArr) {
		strRepDimVal = new String[dvArr.length];
		for (int i = 0; i < dvArr.length; i++) {
			strRepDimVal[i] = dvArr[i].toString();
		}
	}
	
	public LineFIS(FISid f, String line) throws Exception {
		
		////////////////////////////////////////////////////////////////////
		//Split 2^DnetspeedType^BCS_cable^Aos^BWindows Vista^Cbeacon^B26631.0^ATotalOnloadTime^B766009 into
		//      2  and
		//      netspeedType^BCS_cable^Aos^BWindows Vista^Cbeacon^B26631.0^ATotalOnloadTime^B766009
		//   ***Extract the stage and FIS*** ^D
		////////////////////////////////////////////////////////////////////
		String [] arr1 = splitBySeparator(line, 2, "exactly", Character.toString('\004') );
		
		////////////////////////////////////////////////////////////////////
		//Split alreadyCut(2^D) netspeedType^BCS_cable^Aos^BWindows Vista^Cbeacon^B26631.0^ATotalOnloadTime^B766009 into
		//      netspeedType^BCS_cable^Aos^BWindows Vista,
		//      beacon^B26631.0^ATotalOnloadTime^B766009
		//   ***Extract the FIS and separate the metrics *** ^C
		////////////////////////////////////////////////////////////////////
		String [] arr2 = splitBySeparator(arr1[1], 2, "atmost", Character.toString('\003') );
		
		////////////////////////////////////////////////////////////////////
		//Split netspeedType^BCS_cable^Aos^BWindows Vista   alreadyCut(^Cbeacon^B26631.0^ATotalOnloadTime^B766009) into
		//      netspeedType^BCS_cable  and
		//      os^BWindows Vista
		//   ***Extract individual FIS *** ^A
		////////////////////////////////////////////////////////////////////
		String [] arr3 = splitBySeparator(arr2[0], -1, "atleast", Character.toString('\001') );
		DimVal [] dimValArr = new DimVal[arr3.length];

		for (int i = 0; i < arr3.length; i++) {
			String [] arr4  =  splitBySeparator(arr3[i], 2, "exactly", Character.toString('\002') );
			dimValArr[i] = new DimVal(arr4[0], arr4[1]);
		}
		
		arr3 = splitBySeparator(arr2[1], -1, "atleast", Character.toString('\001') );
		Metric [] metricArr = new Metric[arr3.length];

		for (int i = 0; i < arr3.length; i++) {
			String [] arr4  =  splitBySeparator(arr3[i], 2, "exactly", Character.toString('\002') );
			metricArr[i] = new Metric(arr4[0], Double.parseDouble(arr4[1]));
		}
		dimensionNameAndValueArray = dimValArr;
		fisId = f;
		stageNum = f.getStage();
		metricNameAndValueArray = metricArr;
		stringRepDimVal(dimValArr);
		
	}
	
	
	public String [] getAllDimVal() {
		return strRepDimVal;
	}
	
    private static String [] splitBySeparator(String str, int expectedArraySize, String exactAtLeastAtMost, String separator) throws Exception {
        //String separator = Character.toString('\004');
        //String separator = Character.toString('\003');
        String [] ret = str.split(separator);

        switch (exactAtLeastAtMost.toLowerCase() ) {
                case "exatly":
                        if (ret.length != expectedArraySize) {
                                throw new Exception("Expecting " + exactAtLeastAtMost + " "  + separator + "s but got " + (ret.length - 1) );
                        }
                        break;
                case "atleast" :
                        if (ret.length >= expectedArraySize) {
                                throw new Exception("Expecting " + exactAtLeastAtMost + " "  + separator + "s but got " + (ret.length - 1) );
                        }
                        break;
                case "atmost" :
                        if (ret.length <= expectedArraySize) {
                                throw new Exception("Expecting " + exactAtLeastAtMost + " "  + separator + "s but got " + (ret.length - 1) );
                        }
                        break;
                default:
                        throw new Exception("Passed unacceptable value to parameter exactAtLeastAtMost = " + exactAtLeastAtMost + ". Expected values 'exactly', 'atleast' and 'atmost'." );

        }
        return ret;
    }
	
	
	
	public boolean isSubLineInLine(LineFIS subLine) {
		int myLineLength = strRepDimVal.length;
		int subLineLength = subLine.getAllDimVal().length;
		
		for (int i = 0; i < subLineLength; i++) {
			boolean isPresent = false;
			for (int j = 0; j < myLineLength; j++) {
				if (subLine.getAllDimVal()[j].equals(subLine.getAllDimVal()[i]) == true) {
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
	
}
