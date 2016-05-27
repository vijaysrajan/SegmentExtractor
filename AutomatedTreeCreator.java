import java.util.LinkedList;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Arrays;



public class AutomatedTreeCreator {

	static class Val {
		String val;
		double [] metric;
		public Val() {
		}
		public Val(String v, double [] m) {
			val = v;
			metric = m;
		}
		public Val(String v, String m, String separator) {
			val = v;
			String [] s = m.split(separator);
			metric = new double[m.length];
			for (int i = 0; i < m.length; i++) {
				metric[i] = Double.parseDouble(s[i]);
			}
		}
		public Val(String v, String [] m) {
			val = v;
			metric = new double[m.length];
			for (int i = 0; i < m.length; i++) {
				metric[i] = Double.parseDouble(m[i]);
			}
		}
	}

	private static LinkedList<String> queueBreadthFirst =  new LinkedList<String>();

	public static void main (String [] args) throws Exception{

		if ( (args == null) || (args.length == 0) )  {
			System.err.println("Please enter the stages.  Parameters <optional stage file prefix> <optional stage file suffix> <mandatory number of stages>");
			System.exit(-1);
		}
		String fNamePrefix;
		String fNameSuffix;
 		if (args[0].equals("") == true) {
			fNamePrefix = "stage_";
			fNameSuffix = "_candidate_file.txt";
		} else {
			fNamePrefix = args[0];
			fNameSuffix = args[1];
		}
		processStageFile(1, fNamePrefix, fNameSuffix, "");
	}


	private static void printHMap( HashMap<String, HashMap<String,LinkedList<Val>>> hMap) {
		for (String key: hMap.keySet()){
			System.out.println(key);
            		HashMap<String,LinkedList<Val>> value = hMap.get(key);  
			for (String key2: value.keySet()){
				System.out.println("     " + key2 + Arrays.toString(value.get(key2).metric));
			}
		} 
		
	}


	public static void processStageFile(int stageNum, String fNamePrefix, String fNameSuffix, String filterRegex) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(fNamePrefix);
		sb.append("_");
		sb.append(stageNum);
		sb.append("_");
		sb.append(fNameSuffix);
		HashMap<String, HashMap<String,LinkedList<Val>>> lookupListing =  readStageFile(stageNum, filterRegex , sb.toString() );
		printHMap(lookupListing);
	}


	private static String [] splitBySeparator (String str, int expectedArraySize, String exactAtLeastAtMost, String separator) throws Exception {
		//String separator = Character.toString('\004');
		//String separator = Character.toString('\003');
		String [] ret = str.split(separator);

		switch (exactAtLeastAtMost.toLoaweCase() ) {
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


// need a class to manage structure the Hash Structure. This is a little complex


	public static HashMap<String, HashMap<String,LinkedList<Val>>> readStageFile(int stageNumber, String filterRegex, String fileName) throws Exception {

		/*
		HashMap<String, HashMap<String,LinkedList<Val>>> ret1 = new HashMap<String, HashMap<String,LinkedList<Val>>>();
		HashMap<String,LinkedList<Val>> ret2 = new HashMap<String,LinkedList<Val>>();
		*/

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			String line = br.readLine();
			while (line != null) {


				////////////////////////////////////////////////////////////////////
				//Split 2netspeedTypeCS_cableosWindows Vista26631.0 into
				//      2  and
				//      netspeedTypeCS_cableosWindows Vista26631.0
				//   ***Extract the stage and FIS*** 
				////////////////////////////////////////////////////////////////////
				String [] arr1 = splitBySeparator(line, 2, "exactly", Character.toString('\004') );

				////////////////////////////////////////////////////////////////////
				//Split netspeedTypeCS_cableosWindows Vista26631.076768 into
				//      netspeedTypeCS_cableosWindows Vista,
				//      26631.0 and 
				//	76768
				//   ***Extract the FIS and separate the metrics *** 
				////////////////////////////////////////////////////////////////////
				String [] arr2 = splitBySeparator(arr1[1], 2, "atmost", Character.toString('\003') );

				////////////////////////////////////////////////////////////////////
				//Split netspeedTypeCS_cableosWindows Vista26631.0 into
				//      netspeedTypeCS_cable  and
				//      osWindows Vista
				//   ***Extract individual FIS *** 
				////////////////////////////////////////////////////////////////////
				String [] arr3 = splitBySeparator(arr2[0], -1, "atleast", Character.toString('\001') );
				String [] arr4  =  splitBySeparator(arr3[0], 1, "atleast", Character.toString('\002') );
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return ret;
	}
}




