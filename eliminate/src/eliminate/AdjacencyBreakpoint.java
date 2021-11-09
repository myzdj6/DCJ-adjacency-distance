package eliminate;

public class AdjacencyBreakpoint {
	String s;
	int index;
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
class Seg4 implements Cloneable{
	String str;
	int first;
	int second;
	public Seg4 clone() throws CloneNotSupportedException{
        return (Seg4)super.clone();
    }
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	
}

class Seg6 implements Cloneable{ 
	String str;
	int first;
	int second;
	int third;
	public Seg6 clone() throws CloneNotSupportedException{
        return (Seg6)super.clone();
    }

	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getThird() {
		return third;
	}
	public void setThird(int third) {
		this.third = third;
	}
	
}
