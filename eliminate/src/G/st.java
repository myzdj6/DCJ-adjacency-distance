package G;

import java.util.Stack;

public class st {
	public static void main(String[] args) {
		Stack<String> st = new Stack<String>();
		st.push("1");
		st.push("123");
		st.push("1234");
		st.push("1235");

		System.out.println(st.get(1));
		System.out.println(st);
	}
	
	
	
}
