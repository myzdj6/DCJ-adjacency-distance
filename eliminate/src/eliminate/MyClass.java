package eliminate;

import java.util.*;

public class MyClass {
	static String[] num3;
	static int length;
	static int f = 0;
	static boolean flag = true;
	private static ArrayList<String> Collection = new ArrayList<String>();
	private static ArrayList<String> Packing = new ArrayList<String>();
	public static boolean MergeSort(String[] num1, String[] num2) {
		int a = 0;

		int b = 0;

		num3 = new String[num1.length + num2.length];

		for (int i = 0; i < num3.length; i++) {

			if (a < num1.length && b < num2.length) { // 两数组都未遍历完，相互比较后加入
				if (Integer.parseInt(num1[a]) == Integer.parseInt(num2[b]))
					return false;
				if (Integer.parseInt(num1[a]) > Integer.parseInt(num2[b])) {
					num3[i] = num2[b];
					b++;
				} else {

					num3[i] = num1[a];

					a++;

				}

			} else if (a < num1.length) { // num2已经遍历完，无需比较，直接将剩余num1加入

				num3[i] = num1[a];

				a++;

			} else if (b < num2.length) { // num1已经遍历完，无需比较，直接将剩余num2加入

				num3[i] = num2[b];

				b++;

			}

		}
		return true;

	}

	
	// 预处理, 先预处理，检索出Collection每一个set和Packing中的冲突,每一次加入删除Packing中的set后，都需要预处理。
		public static void Pre(int[][] cp) {
			for (int i = 0; i < Collection.size(); i++) {
				for (int j = 0; j < Packing.size(); j++) {
					String[] a = Collection.get(i).split(",");
					String[] b = Packing.get(j).split(",");
					if (!MergeSort(a, b))// 如果冲突
						cp[i][j] = 1;// 1代表冲突
				}
			}
		}
		public static void Pr(int[][] cp){
			for (int i = 0; i < Collection.size(); i++) {
				for (int j = 0; j < Collection.size(); j++) {
					cp[i][j] = 0;//0代表不冲突，1代表冲突
				}
			}
		}
		
	public static String Random(String s) {
		char[] A = s.toCharArray();
		int strlen = A.length;
		List<String> lst = new ArrayList<String>();
		for (int i = 0; i < strlen; i++) {
			lst.add(String.valueOf(A[i]));
		}
		String str = "";
		Collections.shuffle(lst);
		for (int i = 0; i < strlen; i++) {
			str = str + lst.get(i);
		}
		return str;
	}

	// 此方法用于判断，Collection里的两个set是否能插入到Packing中
	public static boolean Feasible(int[][] cp, int pack, int i, int j) {
		int count1 = 0;
		int count2 = 0;
		for (int l = 0; l < pack; l++) {
			count1 = cp[i][l] + cp[j][l] + count1;
			if (cp[i][l] == 1 && cp[j][l] == 1) {
				f = l;// flag表示Collection里的两个set具体和Packing哪个set冲突
			}
		}
		for (int l = 0; l < pack; l++) {
			if (cp[i][l] == cp[j][l])
				count2++;
		}
		if (count2 == pack && count1 == 2) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// String a = "1,2,8,12";
		// String b = "1,3,13,14";
		// String[] x = a.split(",");
		// String[] y = b.split(",");
		//
		// System.out.println(MergeSort(x, y));
//		Collection.add("1,2,3,4");
//		Collection.add("5,6,7,8");
//		Collection.add("1,10,11,12");
//		Collection.add("2,13,14,15");
//		Collection.add("3,16,17,18");
//		Collection.add("4,19,20,21");
//		Collection.add("5,61,71,81");
//		Collection.add("6,69,79,89");
//		Collection.add("7,63,73,83");
//		Collection.add("7,63,73,89");
//		
//		
//		Packing.add("1,2,3,4");
//		Packing.add("5,6,7,8");
		
		Collection.add("1,2,3,4");
		Collection.add("2,5,6,7");
		Collection.add("3,8,15,19");
		Collection.add("2,9,10,11");
		Collection.add("5,12,13,14");

		
		
		Packing.add("1,2,3,4");
		
		int[][] cp = new int[Collection.size()][Collection.size()];
		for (int i = 0; i < Collection.size(); i++) {
			for (int j = 0; j < Collection.size(); j++) {
				cp[i][j] = 0;//0代表不冲突，1代表冲突
			}
		}
		
		
		for(int i =0;i<Packing.size();i++)
			System.out.println(Packing.get(i));
		
		System.out.println("********************");
		Pr(cp);
		Pre(cp);
		int s = 0;
		while (true) {
			Pr(cp);
			Pre(cp);
			s=0;
			for (int i = 0; i < Collection.size(); i++) {	
				for (int j = i + 1; j < Collection.size(); j++) {
					int pack = Packing.size();
					String [] temp1 = Collection.get(i).split(",");
					String [] temp2 = Collection.get(j).split(",");
					if (Feasible(cp, pack, i, j)&MergeSort(temp1,temp2)) {
						Packing.remove(f);
						Packing.add(Collection.get(i));
						Packing.add(Collection.get(j));
						Pr(cp);
						Pre(cp);
						System.out.println("!!!!!!!!!!!1");
						for(int i1 =0;i1<Packing.size();i1++)
							System.out.println(Packing.get(i1));
						System.out.println("!!!!!!!!!!!!!!!");
						break;
					}	
					else{
						s++;
					}			
				}
			}
			if(s==Collection.size()*(Collection.size()-1)/2)
				break;
		}
		
		
		int count1=0;
		
		for (int i = 0; i < Collection.size(); i++) {
			count1= 0;
			Pr(cp);
			Pre(cp);
			for (int j = 0; j < Packing.size(); j++) {
				count1 = count1 + cp[i][j];
			}
			if(count1==0){
				Packing.add(Collection.get(i));
			}
		}
		
	
		for(int i =0;i<Packing.size();i++)
			System.out.println(Packing.get(i));

		System.out.println(Packing.size());
		
//		System.out.println(s2);

//		System.out.println(Random(s2));

	}

}
