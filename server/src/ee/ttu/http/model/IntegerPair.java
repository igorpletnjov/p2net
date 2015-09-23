package ee.ttu.http.model;

public class IntegerPair {
	
	Integer start;
	Integer end;
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "IntegerPair [start=" + start + ", end=" + end + "]";
	}
	
	

}
