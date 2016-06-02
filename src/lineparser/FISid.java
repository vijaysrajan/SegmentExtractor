package lineparser;

public class FISid {
	
	private int stage;
	private int id;
	
	
	public int getStage() {
		return stage;
	}
	public int getId() {
		return id;
	}

	public FISid(int i, int stg) {
		id = i;
		stage = stg;
	}

	public String toString() {	
		StringBuilder sb = new StringBuilder();
		sb.append(stage);
		sb.append("_");
		sb.append(id);
		return sb.toString();
	}
	
}
