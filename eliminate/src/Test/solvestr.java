package Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class solvestr {
	static List<String> s = new ArrayList<String>();
	static List<String> l1 = new ArrayList<String>();
	static List<String> l2 = new ArrayList<String>();
	static List<String> H = new ArrayList<String>();
	static List<String> G = new ArrayList<String>();
	static List<String> st1 = new ArrayList<String>();
	static List<String> st2 = new ArrayList<String>();
	static int[][] mark;
	static int[] flag;
	static String rep;
	public static String getRep() {
		return rep;
	}
	public static void setRep(String rep) {
		solvestr.rep = rep;
	}

	static List<String> lcs = new ArrayList<String>();
	static Stack<String> stack = new Stack();
	static HashMap<Character, Integer> map = new HashMap<>();

	public static HashMap<Character, Integer> getMap() {
		return map;
	}
	public static void setMap(HashMap<Character, Integer> map) {
		solvestr.map = map;
	}

	public static String hum = "";
	public static String gor = "";
	public static String getHum() {
		return hum;
	}
	public static void setHum(String hum) {
		solvestr.hum = hum;
	}
	public static String getGor() {
		return gor;
	}
	public static void setGor(String gor) {
		solvestr.gor = gor;
	}
	public solvestr() throws IOException{
		File file = new File("C:/Users/85221/Desktop/MosaicSDs_SDblockIndexes10.txt"); // 存放数组数据的文件
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			s.add(line);
		}
		int length = s.size();
		for (int i = 1; i < length; i++) {
			String str = s.get(i).replace(":", " ");
			String[] str1 = str.split("\\s+");
			String temp;
			for (int j = 3; j < str1.length; j++) {
				if (s.get(i).charAt(0) == 'H') {
					temp = str1[j].replace("-", "");
					l1.add(temp);
					H.add(temp);
					// H.add(str1[j].replace("-", ""));
				} else {
					temp = str1[j].replace("-", "");
					l2.add(temp);
					G.add(temp);
					// G.add(str1[j].replace("-", ""));
				}

			}
		}
		int n = H.size();
		int m = G.size();
		mark = new int[n + 1][m + 1];

		Collections.sort(l1, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.valueOf(o1)).compareTo(Integer.valueOf(o2));// 正确的方式
				// if ( Integer.valueOf(o1) > Integer.valueOf(o2) ) {
				// return 1;
				// } else {
				// return -1;
				// }
			}
		});

		Collections.sort(l2, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.valueOf(o1)).compareTo(Integer.valueOf(o2));// 正确的方式
			}
		});

		System.out.println(n);
		System.out.println(m);
		findLCS(n, m);
		obtain(n, m);
		System.out.println("st1: " + st1.size() + " st2: " + st2.size());
		l1l2print();
		presolve();
		System.out.println("st1: " + st1.size() + " st2: " + st2.size());

		System.out.println("l1: " + l1.size() + " l2: " + l2.size());

		System.out.println("H: " + H.size() + "G: " + G.size());

		l1l2print();
		generate();
		System.out.println("H: " + H.size() + "G: " + G.size());
		Writer();
		System.out.println(H.size() + " " + G.size());
		System.out.println("*****************");
		System.out.println(Hs(H));
		System.out.println(Gs(G));
		count(hum);
		System.out.println();

	}
	public static void main(String[] args) throws IOException {
		new solvestr();
		// HGprint();
		// for (int i = 0; i < lcs.size(); i++) {
		// System.out.println(lcs.get(i));
		// }

	}

	public static void Writer() throws IOException {
		File file1 = new File("C:/Users/85221/Desktop/test.txt"); // 存放数组数据的文件
		FileWriter fw = new FileWriter(file1);
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0; i < H.size(); i++) {
			bw.write("HG:" + H.get(i) + "GOR:" + G.get(i) + "\r\n");
		}
		bw.flush();
		bw.close();
	}

	public static void l1l2print() {
		System.out.println("l1和l2:");
		for (int i = 0; i < l1.size(); i++) {
			System.out.print(l1.get(i) + " ");
		}
		System.out.println();
		for (int i = 0; i < l2.size(); i++) {
			System.out.print(l2.get(i) + " ");
		}
		System.out.println();
	}

	public static void HGprint() {
		System.out.println("H和G:");
		for (int i = 0; i < H.size(); i++) {
			System.out.print(H.get(i) + " ");
		}
		System.out.println();
		for (int i = 0; i < G.size(); i++) {
			System.out.print(G.get(i) + " ");
		}
		System.out.println();
	}

	public static int findLCS(int n, int m) {
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 1; i <= n; i++) {
			mark[i][0] = 1;
		}
		for (int j = 1; j <= m; j++) {
			mark[0][j] = -1;
		}

		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = 0;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (l1.get(i - 1).equals(l2.get(j - 1))) {
					dp[i][j] = dp[i - 1][j - 1] + 1;
					mark[i][j] = 0;
				} else if (dp[i - 1][j] >= dp[i][j - 1]) {
					dp[i][j] = dp[i - 1][j];
					mark[i][j] = 1;

				} else {
					dp[i][j] = dp[i][j - 1];
					mark[i][j] = -1;
				}
			}
		}
		return dp[n][m];
	}

	public static void obtain(int x, int y) {
		while (x >= 1 && y >= 1) {
			if (l1.get(x - 1).equals(l2.get(y - 1))) {
				stack.push(l1.get(x - 1));
				x--;
				y--;
			} else if (mark[x][y] == 1) {
				x--;
			} else
				y--;
		}
		while (!stack.isEmpty()) {
			String sr = stack.pop();
			lcs.add(sr);
			st1.add(sr);
			st2.add(sr);
			// System.out.print(stack.pop() + "*");

		}
	}

	// 预处理是将排序后基因序列删除公共部分后，得出各自的基因段x和y
	public static void presolve() {
		while (!st1.isEmpty()) {
			int index = (int) (Math.random() * st1.size());
			String rand = st1.get(index);
			for (int i = 0; i < l1.size(); i++) {
				if (l1.get(i).equals(rand)) {
					l1.remove(i);
					st1.remove(rand);
					break;
				}
			}
		}

		while (!st2.isEmpty()) {
			int index = (int) (Math.random() * st2.size());
			String rand = st2.get(index);
			for (int i = 0; i < l2.size(); i++) {
				if (l2.get(i).equals(rand)) {
					l2.remove(i);
					st2.remove(rand);
					break;
				}
			}
		}
	}

	// 用各自源基因去除x和y，得到基因组A和B，它们组成相同但是排序各异。不随机：
//	public static void generate() {
//		while (!l1.isEmpty()) {
//			int index = (int) (Math.random() * l1.size());
//			String rand = l1.get(index);
//			for (int i = 0; i < H.size(); i++) {
//				if (H.get(i).equals(rand)) {
//					H.remove(i);
//					l1.remove(rand);
//					break;
//				}
//			}
//		}
//
//		while (!l2.isEmpty()) {
//			int index = (int) (Math.random() * l2.size());
//			String rand = l2.get(index);
//			for (int i = 0; i < G.size(); i++) {
//				if (G.get(i).equals(rand)) {
//					G.remove(i);
//					l2.remove(rand);
//					break;
//				}
//			}
//		}
//	}
	// 用各自源基因去除x和y，得到基因组A和B，它们组成相同但是排序各异。随机：
	public static void generate() {
		flag = new int[H.size()];
		while (!l1.isEmpty()) {
			for(int i = 0;i<flag.length;i++){
				flag[i] = 0;
			}
			st1.clear();
			int index = (int) (Math.random() * l1.size());
			String rand = l1.get(index);
			for (int i = 0; i < H.size(); i++) {
				if (H.get(i).equals(rand)) {
					// H.remove(rand);
					// l1.remove(rand);
					// break;
					flag[i] = 1;
					st1.add(String.valueOf(i));
				}
			}
//			System.out.println(st1.size());
			Random r = new Random();
			int random = r.nextInt(st1.size());
			String str1 = st1.get(random);
			H.remove(Integer.valueOf(str1).intValue());
			l1.remove(rand);

		}
		

		while (!l2.isEmpty()) {
			flag = new int[G.size()];
			for(int i = 0;i<flag.length;i++){
				flag[i] = 0;
			}
			st2.clear();
			int index = (int) (Math.random() * l2.size());
			String rand = l2.get(index);
			for (int i = 0; i < G.size(); i++) {
				if (G.get(i).equals(rand)) {
					// G.remove(rand);
					// l2.remove(rand);
					// break;

					flag[i] = 1;
					st2.add(String.valueOf(i));
				}
			}
			Random r = new Random();
			int random = r.nextInt(st2.size());
			String str2 = st2.get(random);
			G.remove(Integer.valueOf(str2).intValue());
			l2.remove(rand);

		}
	}
	
	
	
	public static void count(String s) throws IOException{
		for (int i = 0; i < s.length(); i++) {
			if (null == map.get(s.charAt(i))) {
				map.put(s.charAt(i), 1);
			} else {
				map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
			}
		}
		int Count = 0;
		int max = 1;
		
		System.out.println("不同基因数共计:" + map.size());
		for(Character key:map.keySet()){
			int a = map.get(key);
			if(a>1)
				{
				//System.out.println("Key" + key+ "Value" +map.get(key));
				Count++;
				}
			if(max <map.get(key))
				max = map.get(key);
		}
		System.out.println("重复基因共计"+ Count + "个,"+"基因最多重复"+ max +"次");
		rep = "重复基因共计"+ Count + "个,"+"基因最多重复"+ max +"次";
	}
	
	public static String Hs(List<String> hs) throws IOException {
//		File file1 = new File("C:/Users/85221/Desktop/Hs.txt"); // 存放数组数据的文件
//		FileWriter fw = new FileWriter(file1);
//		BufferedWriter bw = new BufferedWriter(fw);
		
		for (int i = 0; i < hs.size(); i++) {
			hum = hum + (char) (Integer.valueOf(hs.get(i)) + 19968);
		}
//		bw.write(hum);
//		bw.flush();
//		bw.close();
		return hum;

	}

	public static String Gs(List<String> gs) throws IOException {
		File file1 = new File("C:/Users/85221/Desktop/Gs.txt"); // 存放数组数据的文件
		FileWriter fw = new FileWriter(file1);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (int i = 0; i < gs.size(); i++) {
			gor = gor + (char) (Integer.valueOf(gs.get(i)) + 19968);
		}
		bw.write(gor);
		bw.flush();
		bw.close();
		return gor;

	}
}
