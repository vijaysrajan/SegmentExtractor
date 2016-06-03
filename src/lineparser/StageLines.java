package lineparser;
import java.util.ArrayList;
import java.util.ListIterator;

public class StageLines {
	private int stageNum;
	private ArrayList<LineFIS> stageLines;
	
	public StageLines(int stg){
		stageNum = stg;
		stageLines = new ArrayList<LineFIS>();
	}
	
	public int getStageNum() {
		return stageNum;
	}
	
	public ListIterator<LineFIS> getListIterator() {
		return stageLines.listIterator();
	}
	
	
	private int lineId = 0;
	public void injectLine(String line) throws Exception {
		
		FISid FISid  = new FISid(lineId, stageNum);

		LineFIS lFIS = new LineFIS(FISid, line );
		stageLines.add(lFIS);
		lineId++;
	}
	
	
	public static void main(String [] args) throws Exception{
		StageLines s = new StageLines(2);
		s.injectLine(args[0]);
		ListIterator<LineFIS> lItr = s.getListIterator();
		FISid FISid  = new FISid(0,0);
		LineFIS toComp = new LineFIS(FISid,args[1]);
		while (lItr.hasNext()) {
			System.out.println(lItr.next().isSubLineInLine(toComp));
		}
		
		lItr = s.getListIterator();
		while (lItr.hasNext()) {
			//DimVal [] v = lItr.next().getExtraDimValInLongerLineFIS(toComp);
			DimVal [] v =lItr.next().getExtraDimVal(toComp);
			for (int i = 0; i < v.length; i++) {
				if (v[i] != null) {
					System.out.println(v[i].toString());
				}
			}
		}
		
	}
}
