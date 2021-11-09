package eliminate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



import Test.solvestr;

public class R2A3 {
	static String Js = "30-2-2";
	public static String getJs() {
		return Js;
	}

	public static void setJs(String js) {
		Js = js;
	}

	static int length;
	static int f = 0;
	static int f1 = 0;
	static int f2 = 0;
	static int f3 = 0;
	static boolean flag = true;
	static String[] num3;
	static int count4 = 0;
	static int count6 = 0;
	static int localmax4 = 0;
	static int localmax6 = 0;
	static int optimal4 = 0;
	static int optimal6 = 0;
	static double ratio = 0;
	static int packing2 = 0;
	static int packing3 = 0;
	static int packing4 = 0;
	static int[][] Black;
	static int ct = 0;

	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		R2A3.length = length;
	}

	public static int[][] getBlack() {
		return Black;
	}

	public static void setBlack(int[][] black) {
		Black = black;
	}

	public static int[][] getGrey() {
		return Grey;
	}

	public static void setGrey(int[][] grey) {
		Grey = grey;
	}

	static int[][] Grey;

	// 将冲突矩阵置空
	public static void Localmaxpack4() {
		int[] temp = new int[Collection.size()];
		for (int i = 0; i < Collection.size(); i++) {
			temp[i] = 1;
		}
		for (int i = 0; i < Collection.size(); i++) {
			if (temp[i] == 1)
				Packing.add(Collection.get(i));
			else
				continue;
			for (int j = i + 1; j < Collection.size(); j++) {
				String[] a = Collection.get(i).split(",");
				String[] b = Collection.get(j).split(",");
				if (!MergeSort(a, b))
					temp[j] = 0;// 0代表冲突
			}

		}
		for (int i = 0; i < Collection.size(); i++) {
			localmax4 = localmax4 + temp[i];
		}

	}

	public static void Localmaxpack6() {
		int[] temp6 = new int[Collection6.size()];
		for (int i = 0; i < Collection6.size(); i++) {
			temp6[i] = 1;
		}
		for (int i = 0; i < Collection6.size(); i++) {
			if (temp6[i] == 1)
				Packing6.add(Collection6.get(i));
			else
				continue;
			for (int j = i + 1; j < Collection6.size(); j++) {
				String[] a = Collection6.get(i).split(",");
				String[] b = Collection6.get(j).split(",");
				if (!MergeSort(a, b))
					temp6[j] = 0;// 0代表冲突
			}

		}
		for (int i = 0; i < Collection6.size(); i++) {
			localmax6 = localmax6 + temp6[i];
		}
	}

	public static void NewLocalMaxPacking4() {
		int flag = 0;
		int[] temp4 = new int[Collection.size()];
		Packing.add(Collection.get(0));
		for (int i = 0; i < Collection.size(); i++) {
			flag = 0;
			String[] a = Collection.get(i).split(",");
			for (int j = 0; j < Packing.size(); j++) {
				String[] b = Packing.get(j).split(",");
				if (!MergeSort(a, b)) {
					flag++;
				}
				if (flag > 0)
					break;
			}
			if (flag == 0)
				Packing.add(Collection.get(i));
		}
		localmax4 = Packing.size();
	}

	public static void NewLocalMaxPacking6() {
		int flag = 0;
		int[] temp6 = new int[Collection6.size()];
		Packing6.add(Collection6.get(0));
		for (int i = 0; i < Collection6.size(); i++) {
			flag = 0;
			String[] a = Collection6.get(i).split(",");
			for (int j = 0; j < Packing6.size(); j++) {
				String[] b = Packing6.get(j).split(",");
				if (!MergeSort(a, b)) {
					flag++;
				}
				if (flag > 0)
					break;
			}
			if (flag == 0)
				Packing6.add(Collection6.get(i));
		}
		localmax6 = Packing6.size();
	}

	public static void Pr6(int[][] cp) {
		for (int i = 0; i < Collection6.size(); i++) {
			for (int j = 0; j < Collection6.size(); j++) {
				cp[i][j] = 0;// 0代表不冲突，1代表冲突
			}
		}
	}

	public static void Pre6(int[][] cp) {
		for (int i = 0; i < Collection6.size(); i++) {
			for (int j = 0; j < Packing6.size(); j++) {
				String[] a = Collection6.get(i).split(",");
				String[] b = Packing6.get(j).split(",");
				if (!MergeSort(a, b))// 如果冲突
					cp[i][j] = 1;// 1代表冲突
			}
		}
	}

	public static void improved1_6(int[][] cp) {
		int s = 0;
		int count1 = 0;
		Pr6(cp);
		Pre6(cp);
		// while (true) {
		// s = 0;
		// for (int i = 0; i < Collection6.size(); i++) {
		// for (int j = i + 1; j < Collection6.size(); j++) {
		// ct++;
		// int pack = Packing6.size();
		// String[] temp1 = Collection6.get(i).split(",");
		// String[] temp2 = Collection6.get(j).split(",");
		// if (Feasible1(cp, pack, i, j) && MergeSort(temp1, temp2)) {
		// Packing6.remove(f);
		// Packing6.add(Collection6.get(i));
		// Packing6.add(Collection6.get(j));
		// Pr6(cp);
		// Pre6(cp);
		//// for(int i1 =0;i1<Packing6.size();i1++)
		//// System.out.println(Packing6.get(i1));
		// break;// 假设i=1,j=3时,将其二加入packing中,此时如何遍历?接着搜j =
		// // j+1依旧可以,但无意义,因为与此配对的i已经加入了packing,之后必然不会加入
		// } else {
		// s++;
		// }
		//
		// }
		// }
		// if (s == Collection6.size() * (Collection6.size() - 1) / 2)
		// break;
		// }
		//

		for (int i = 0; i < Collection6.size(); i++) {
			for (int j = i + 1; j < Collection6.size(); j++) {
				int pack = Packing6.size();
				String[] temp1 = Collection6.get(i).split(",");
				String[] temp2 = Collection6.get(j).split(",");
				if (Feasible1(cp, pack, i, j) && MergeSort(temp1, temp2)) {
					Packing6.remove(f);
					Packing6.add(Collection6.get(i));
					Packing6.add(Collection6.get(j));
					Pr6(cp);
					Pre6(cp);
					// for(int i1 =0;i1<Packing6.size();i1++)
					// System.out.println(Packing6.get(i1));
					break;// 假设i=1,j=3时,将其二加入packing中,此时如何遍历?接着搜j =
							// j+1依旧可以,但无意义,因为与此配对的i已经加入了packing,之后必然不会加入
				}

			}
		}
		optimal6 = Packing6.size();

	}
	// 注：最后再一次性加上是否更优，假设之前的每一次删1加t，t大于等于3时，都加上t-2再算和先不加，后者一定不必前者差
	// 原因是，每一次迭代都在进步，但是不加，施展空间更大，如果加上能优化，不加更能优化

	// for (int i = 0; i < Collection.size(); i++) {
	// count1 = 0;
	// Pr(cp);
	// Pre(cp);
	// for (int j = 0; j < Packing.size(); j++) {
	// count1 = count1 + cp[i][j];
	// }
	// if (count1 == 0) {
	// Packing.add(Collection.get(i));
	// }
	// }
	// 以上代码2019.4.26，加上注释时因为feAS4ible方法变化了，不需要如上代码段

	public static void Pr(int[][] cp) {
		for (int i = 0; i < Collection.size(); i++) {
			for (int j = 0; j < Collection.size(); j++) {
				cp[i][j] = 0;// 0代表不冲突，1代表冲突
			}
		}
	}

	// 预处理, 先预处理，检索出Collection每一个set和Packing中的冲突,每一次加入删除Packing中的set后，都需要预处理。
	// 此处是否可以改进，谁进来，针对谁修改以下就可以。
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

	// 此方法用于判断，Collection里的两个set是否能插入到Packing中
	public static boolean Feasible1(int[][] cp, int pack, int i, int j) {
		int count1 = 0;
		int count2 = 0;

		for (int l = 0; l < pack; l++) {
			int sum = cp[i][l] + cp[j][l];
			if (cp[i][l] == cp[j][l] && sum == 0) {
				count2++;
			} else {
				f = l;
			}
		}
		if (count2 == pack - 1) {
			return true;
		}
		return false;
	}

	// for (int l = 0; l < pack; l++) {
	// count1 = cp[i][l] + cp[j][l] + count1;
	// if (cp[i][l] == 1 && cp[j][l] == 1) {
	// f = l;// flag表示Collection里的两个set具体和Packing哪个set冲突
	// }
	// }
	// for (int l = 0; l < pack; l++) {
	// if (cp[i][l] == cp[j][l])
	// count2++;
	// }
	// if (count2 == pack && count1 == 2) {
	// return true;
	// }
	// return false;
	// }

	public static boolean Feasible2(int[][] cp, int pack, int i, int j, int u) {
		int count1 = 0;
		int count2 = 0;
		// for (int l = 0; l < pack; l++) {
		// count1 = cp[i][l] + cp[j][l] + cp[u][l] + count1;
		// if (cp[i][l] == 1 && cp[j][l] == 1) {
		// f = l;// flag表示Collection里的两个set具体和Packing哪个set冲突
		// }
		// }
		int count0 = 0;
		for (int l = 0; l < pack; l++) {
			int sum = cp[i][l] + cp[j][l] + cp[u][l];
			if (cp[i][l] == cp[j][l] && cp[j][l] == cp[u][l] && sum == 0) {
				count2++;
			} else {
				if (count0 == 0) {
					f1 = l;
					count0++;
					continue;
				}
				if (count0 == 1) {
					f2 = l;
				}
			}
		}
		// count2 == pack - 2 表示三个集合仅仅和当前packing中的两个冲突
		if (count2 == pack - 2) {
			return true;
		}
		return false;
	}

	public static boolean Feasible3(int[][] cp, int pack, int i, int j, int u, int v) {
		int count1 = 0;
		int count2 = 0;
		int count0 = 0;
		for (int l = 0; l < pack; l++) {
			int sum = cp[i][l] + cp[j][l] + cp[u][l] + cp[v][l];
			if (cp[i][l] == cp[j][l] && cp[j][l] == cp[u][l] && cp[u][l] == cp[v][l] && sum == 0) {
				count2++;
			} else {
				if (count0 == 0) {
					f1 = l;
					count0++;
					continue;
				}
				if (count0 == 1) {
					f2 = l;
					count0++;
					continue;
				}
				if (count0 == 2) {
					f3 = l;
				}
			}
		}
		// count2 == pack - 2 表示三个集合仅仅和当前packing中的两个冲突
		if (count2 == pack - 3) {
			return true;
		}
		return false;
	}

	public static void improved1(int[][] cp) {
		int s = 0;
		int count1 = 0;
		Pr(cp);
		Pre(cp);
		while (true) {
			s = 0;
			for (int i = 0; i < Collection.size(); i++) {
				for (int j = i + 1; j < Collection.size(); j++) {
					int pack = Packing.size();
					String[] temp1 = Collection.get(i).split(",");
					String[] temp2 = Collection.get(j).split(",");
					if (Feasible1(cp, pack, i, j) && MergeSort(temp1, temp2)) {
						Packing.remove(f);
						Packing.add(Collection.get(i));
						Packing.add(Collection.get(j));
						Pr(cp);
						Pre(cp);
						for (int i1 = 0; i1 < Packing.size(); i1++)
							System.out.println(Packing.get(i1));
						break;// 假设i=1,j=3时,将其二加入packing中,此时如何遍历?接着搜j =
								// j+1依旧可以,但无意义,因为与此配对的i已经加入了packing,之后必然不会加入
					} else {
						s++;
					}
				}
			}
			if (s == Collection.size() * (Collection.size() - 1) / 2)
				break;
		}
		// 注：最后再一次性加上是否更优，假设之前的每一次删1加t，t大于等于3时，都加上t-2再算和先不加，后者一定不必前者差
		// 原因是，每一次迭代都在进步，但是不加，施展空间更大，如果加上能优化，不加更能优化

		// for (int i = 0; i < Collection.size(); i++) {
		// count1 = 0;
		// Pr(cp);
		// Pre(cp);
		// for (int j = 0; j < Packing.size(); j++) {
		// count1 = count1 + cp[i][j];
		// }
		// if (count1 == 0) {
		// Packing.add(Collection.get(i));
		// }
		// }
		// 以上代码2019.4.26，加上注释时因为feAS4ible方法变化了，不需要如上代码段
		optimal4 = Packing.size();

	}

	public static void improved2(int[][] cp) {
		int s = 0;
		int count1 = 0;
		Pr(cp);
		Pre(cp);
		while (true) {
			s = 0;
			Z: for (int i = 0; i < Collection.size(); i++) {
				for (int j = i + 1; j < Collection.size(); j++) {
					for (int u = j + 1; u < Collection.size(); u++) {
						int pack = Packing.size();
						String[] temp1 = Collection.get(i).split(",");
						String[] temp2 = Collection.get(j).split(",");
						String[] temp3 = Collection.get(u).split(",");
						if (Feasible2(cp, pack, i, j, u) & MergeSort(temp1, temp2) & MergeSort(temp1, temp3)
								& MergeSort(temp2, temp3)) {
							String sf1 = Packing.get(f1);
							String sf2 = Packing.get(f2);
							Packing.remove(sf1);
							Packing.remove(sf2);
							Packing.add(Collection.get(i));
							Packing.add(Collection.get(j));
							Packing.add(Collection.get(u));
							for (int i1 = 0; i1 < Packing.size(); i1++)
								System.out.println(Packing.get(i1));
							Pr(cp);
							Pre(cp);
							continue Z;// 假设i=1,j=3时,将其二加入packing中,此时如何遍历?接着搜j =
										// j+1依旧可以,但无意义,因为与此配对的i已经加入了packing,之后必然不会加入
						} else {
							s++;
						}
					}
				}
			}
			if (s == Collection.size() * (Collection.size() - 1) * (Collection.size() - 2) / 6)
				break;
		}
		packing3 = Packing.size();
	}

	public static void improved3(int[][] cp) {
		int s = 0;
		int count1 = 0;
		Pr(cp);
		Pre(cp);
		while (true) {
			s = 0;
			Z: for (int i = 0; i < Collection.size(); i++) {
				for (int j = i + 1; j < Collection.size(); j++) {
					for (int u = j + 1; u < Collection.size(); u++) {
						for (int v = u + 1; v < Collection.size(); v++) {
							int pack = Packing.size();
							String[] temp1 = Collection.get(i).split(",");
							String[] temp2 = Collection.get(j).split(",");
							String[] temp3 = Collection.get(u).split(",");
							String[] temp4 = Collection.get(v).split(",");
							if (Feasible3(cp, pack, i, j, u, v) && MergeSort(temp1, temp2) && MergeSort(temp1, temp3)
									&& MergeSort(temp1, temp4) && MergeSort(temp2, temp3) && MergeSort(temp2, temp4)
									&& MergeSort(temp3, temp4)) {
								String sf1 = Packing.get(f1);
								String sf2 = Packing.get(f2);
								String sf3 = Packing.get(f3);
								Packing.remove(sf1);
								Packing.remove(sf2);
								Packing.remove(sf3);
								Packing.add(Collection.get(i));
								Packing.add(Collection.get(j));
								Packing.add(Collection.get(u));
								Packing.add(Collection.get(v));
								Pr(cp);
								Pre(cp);
								for (int i1 = 0; i1 < Packing.size(); i1++)
									System.out.println(Packing.get(i1));
								continue Z;// 假设i=1,j=3时,将其二加入packing中,此时如何遍历?接着搜j
											// =
											// j+1依旧可以,但无意义,因为与此配对的i已经加入了packing,之后必然不会加入
							} else {
								s++;
							}
						}

					}
				}
			}
			if (s == Collection.size() * (Collection.size() - 1) * (Collection.size() - 2) * (Collection.size() - 3)
					/ 24)
				break;
		}
		packing4 = Packing.size();
	}

	public static void File(int[][] Black, int Grey[][]) throws IOException {
		File file = new File("F:/有关断点消除/LINGO10/b.txt"); // 存放数组数据的文件
		File file1 = new File("F:/有关断点消除/LINGO10/c.txt"); // 存放数组数据的文件
		FileWriter out = new FileWriter(file); // 文件写入流
		FileWriter out1 = new FileWriter(file1); // 文件写入流

		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				out.write(Black[i][j] + "\t");
			}
			out.write("\r\n");
		}

		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				out1.write(Grey[i][j] + "\t");
			}
			out1.write("\r\n");

		}

		out.close();
		out1.close();

	}

	public static void PrintB(int Black[][]) {
		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				System.out.print(Black[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("*************分割线***************");
	}

	public static void PrintG(int Grey[][]) {
		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				System.out.print(Grey[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("*************分割线***************");
	}

	public static String reversel1(String str) {
		return new StringBuffer(str).reverse().toString();
	}

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

	private static ArrayList<String> T = new ArrayList<String>();
	private static ArrayList<String> S = new ArrayList<String>();
	private static ArrayList<String> Q = new ArrayList<String>();

	private static ArrayList<AdjacencyBreakpoint> A = new ArrayList<AdjacencyBreakpoint>();
	private static ArrayList<AdjacencyBreakpoint> B = new ArrayList<AdjacencyBreakpoint>();
	private static ArrayList<Seg4> AS4 = new ArrayList<Seg4>();
	private static ArrayList<Seg4> BQ4 = new ArrayList<Seg4>();

	private static ArrayList<Seg6> AS6 = new ArrayList<Seg6>();
	private static ArrayList<Seg6> BQ6 = new ArrayList<Seg6>();

	private static ArrayList<Seg6> AS46 = new ArrayList<Seg6>();
	private static ArrayList<Seg6> BQ46 = new ArrayList<Seg6>();

	private static ArrayList<String> Collection = new ArrayList<String>();
	private static ArrayList<String> Collection6 = new ArrayList<String>();
	private static ArrayList<String> Packing = new ArrayList<String>();
	private static ArrayList<String> Packing6 = new ArrayList<String>();

	public R2A3() throws IOException {
		f = 0;
		f1 = 0;
		f2 = 0;
		f3 = 0;
		flag = true;
		count4 = 0;
		count6 = 0;
		T.clear();
		S.clear();
		Q.clear();
		A.clear();
		B.clear();
		AS4.clear();
		BQ4.clear();

		AS6.clear();
		BQ6.clear();

		Collection.clear();
		Collection6.clear();

		Packing.clear();
		Packing6.clear();

		// String s1 = "abcadba-ccdc-#abcc#";
		// String s2 = "acdbbaa-#acd#-#cbcc#";

		// String s1 =
		// "#abccababccbbadcaabcdabcacbsfnhonskadnlkzAS4fbncbcnabbbcccbbbcdcdbabAS4daAS4hfgdfdkklkjgggfddssAS4dAS4dAS4daAS4dzxvxahxzzkzxkvsbsandnshshsshwwnwnwnwnacdbbbbscakknwkllssw#";
		// String s2 =
		// "#akccskcsbaczadgssvabagzsnahwacdhdscbhkazbsfbdAS4fblabnsaxAS4anblancnjscdxcwkgazacndadksaladsabaxwvacznbablwhkbbssgdwhbdxawsfsbbbndbwccbnbsdshccnkdbckokbdsdknncfbfd#";

		// String s11 =
		// "abccababccbbadcaabcdabcacbsfnhonskadnlkzAS4fbncbcnabbbcccbbbcdcdbabAS4daAS4hfgdfdkklkjgggfddssAS4dAS4dAS4daAS4dzxvxahxzzkzxkvsbsandnshshsshwwnwnwnwnacdbbbbscakknwkllsswcbabAS4kjiiqwiujAS4hsnssAS4fsAS4abbcAS4lkjjkAS4fpossfff";
		// String s22 = Random(s11);

		// 以下数据 2、3-optimal为6而4-optimal为8
		// String s1 = "#abcnAS4bacscsbabdscsadjhgf#";
		// String s2 = "#bfaajbsdhcabcsabgsdnsaccs#";

		// String s1 = "#abcnAS4bacscsbabdscsadjhgfdsbAS4vsavsAS4fkjhdfgdss#";
		// String s2 = "#bbbjcAS4sssaaagAS4bsdcdAS4fvnfdvcfjcadsshsAS4sdkhgb#";

		// String s1 =
		// "#abccaAS4dAS4faAS4AS4AS4dAS4AS4fAS4fsdgsdgsfaadAS4AS4dAS4daqaaAS4fbabsabcbcabcbcbdddghhkklloppmmjjjjjjsbsababbabbbbdlAS4dkqwretogjsdvnjdsvkajsnnAS4fndvmAS4AS4fAS4fhytrdelkjhgfiuytvfgfdfgfghfdgdfgdfgdfgdsfsnmd#";
		// String s2 =
		// "#snAS4fcjgnabhgsdisgcsdcsdcodssajAS4gfaflldjssddldsbfanmagabavssdadfbdfaaahbdldabwbdfabjrpdhsafsgqcAS4mafhfbdsfangfaesssggyjdcqbdbffdatyabubjjmkfjahstfpajekovAS4abmanrAS4davbssgkdatgjsgkaafbvdfdks#";
		// String s1 =
		// "#abccaAS4dAS4faAS4AS4AS4dAS4AS4fAS4fsdgsdgsfaadAS4AS4dAS4daqaaAS4fbabsabcbcabcbcbdddghhkklloppmmjjjjjjsbsababbabbbbdlAS4dkqwretogjsdvnjdsvkajsnnAS4fndvmAS4AS4fAS4fhytrdelkjhgfiuytvfgfdfgfghfdgdfgdfgdfgdsfsnmd#";
		// String s2 =
		// "#gvsbsvajgfAS4fslsawdhjkfcsmddsAS4ddlnbseajabtfdssjmgaaadgfdhibbapadfaenyfdgBQ4bcdAS4ssdbjsfftvfbtaadffdukccgsagAS4ayvsjadaofamdsagbaAS4aqdbfofamdnsdcnacsdhslkfjdgrbadshkfabjsabgdsjfbblrpkssaggnjah#";

		// String s1 =
		// "#abccaAS4dAS4faAS4AS4AS4dAS4AS4fAS4fsdgsdgsfaadAS4AS4dAS4daqaaAS4fbabsabcbcabcbcbdddghhkklloppmmjjjjjjsbsababbabbbbdlAS4dkqwretogjsdvnjdsvkajsnnAS4fndvmAS4AS4fAS4fhytrdelkjhgfiuytvfgfdfgfghfdgdfgdfgdfgdsfsnmd#";
		// String s2 =
		// "#aadsaiggssddfhadslsjvsdkddsnysbjpaaaqmnmgpcenaAS4gfajdckhlssouldAS4cbssgaAS4qaAS4gfrsdbsfmagfbfscdbatbggAS4grfdcdatfdtbkavganakbvbAS4AS4sfbabbsffkdddanjyaahbeafsjcsjddbaadfffmgjbwajdlhdsbdfvjojffhs#";
		// String s1 =
		// "#abccaAS4dAS4faAS4AS4AS4dAS4AS4fAS4fsdgsdgsfaadAS4AS4dAS4daqaaAS4fbabsabcbcabcbcbdddghhkklloppmmjjjjjjsbsababbabbbbdlAS4dkqwretogjsdvnjdsvkajsnnAS4fndvmAS4AS4fAS4fhytrdelkjhgfiuytvfgfdfgfghfdgdfgdfgdfgdsfsnmd#";
		// String s2 =
		// "#hfhvgsfpjbsalsfgaaabmblsktdjsdnfsbfsfachmcscrkadadjfbceaaqskajomufabdjcbdjladaAS4ssdaybfganjtgdagavbdssfftsaapigddssnjadnbjAS4msdvwblddoagfefafhaafddgfbadkjaAS4sdggAS4ssdssqgdcfhabdnbgabbsfvyrks#";
		// String s1 = "#abccaAS4dAS4fahhsiiibhsbhacbbcakkllo#";
		// String s2 = "#bsaalhhkcfblsaosiaakicaibdbshcchab#";
		// String s1 = "#abccaAS4dAS4fahhsiiibhsbhacbbcakkllo#";
		// String s2 = "#aaAS4ibiclkcsbcafhdokahsalhhscbabbi#";
		// String s1 = "#abccaAS4dAS4fahhsiiibhsbhacbbcakkllo#";
		// String s2 = "#cbhhadaaiahbsalsfbcibccbiaklAS4oshk#";

		// 2021.6.25
		// String s11 =
		// "adkqwretogjAS4fhmmjjjjAS4AS4fAS4fsdgsdgsfaadAS4AS4dAS4daqaaAS4fbaAS4dAS4AS4fbcabhstfpajekovAS4abmanrAS4davbssgkdatgjsgkaafbvdfdks";
		// String s22 = Random(s11);
		// String s1 = "#" + s11 + "#";
		// String s2 = "#" + s22 + "#";
		 
		 String s11 ="abcdefghijklmnoabcdefghijklmno";
		 String s22 = Random(s11);
 		 String s1 = "#" + s11 + "#";
		 String s2 = "#" + s22 + "#";
				

//		 new Test.solvestr();
//		 String s11 = Test.solvestr.getHum();
//		 String s22 = Test.solvestr.getGor();
//		 String s1 = "#" + s11 + "#";
//		 String s2 = "#" + s22 + "#";
		//100-5
		
		//String s1 = "#退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速#";
		//String s2 = "#送老后同小耙谁逆名少同思调送谁后通君后者者速老将者少逆君思谁将退后倒向送者者老速向速逆耙耙同小调倒向送君逆思调少速少名将向君调将倒退小同名退将老通小通送调退后耙耙同速名通思向退谁逆倒倒名少通老谁思小君#";
		
		//500-5
		//String s1 ="#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯#";
		//String s2 ="#A3思送衫耙N通K造恨fgOGe谁WoT琶调b谪l行DGH同EE君57停kl急呕d逆倾X小琶C8呕名N琵Y8nOO恨逢琶Q灯j恨4i江W同送灯mM谪I听a通TSH落小遁IRc耳7耙V送JU破kPK造hS退属名4K衣M7逢思耳吟U9m造尽f名B2k向逆吟Y后污琵者将n送FI遮听污瑟少急瑟停少2调n水急送Q调通2dmWB将呕Sd血水呕破6遮老吟hk老i污mEFH逢g0Cg2者1g速小谪Y灯VG退少少琵a将居4遁江少小8速n谁停倾君江倒呕老C5J08行1o停M调向8琶h瑟oX通谁b7君属谪速9调老NioY5退M通AC居4jd倒ilffh衣eLU后L倾V行遮血瑟i69退TWb急尽P逆X行造S衫衫5a居灯N落MD5b6A同将eI0思L0jZ3衫Wo衣R居cJ谁IQdP属名F后Z耳向a61属R老k名水S属B尽倒尽LEh落遮c破mH7O思灯A瑟ZbZT6耳jH听XQ遁l耳A后血9P2E3倒X污K速衣R水逆KR3Z逆FL后者谁停耙3耙VV急P遁造9F居DD君同江accB恨O1DYC倾j听水倒U者小破破恨nBNQ速G倾吟向l0U江遮g听谪衫血e琵血思吟琵落4污T行同GJ将f者J尽向e退耙君落琶衣1逢遁逢#";
		//200-5
//		String s1 = "#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78#";
//		String s2 = "#琵81急水6同者谁8谪谁6逢耳调退逆谁送通小64名逆君倒3向速耙同13谪谁造少退耙听5谪24通老破倒琶调3小向思思耙急思5君送倒后逢87者吟0速谁退送2少速2倒64君老0逢1琶06老向送君通1者耙急向破2思5后水0后逆吟倒向2同名耳少者耳3退破急0造老听小逢听1琵将7听少7君谪7逆同45将琶破吟调名小水耳速老通琶4逢破琵水急名通少吟小者8琶同逆名3水耙速调后造58后将送退琵造听造调思将7谪耳吟琵将#";
		
		//300-5-60-23
//		String s1 = "#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行#";
//		String s2 = "#后落急者4琵将血耳5363A落向送急G7血吟谪94耳逢将谁倾琶DD调行速2名吟君后血同耳K思倒君J后L行5E听FG尽退落C向谪退倾B倒小HK老向1BD名速B破造者思退通F老思后小1同倾6FH吟遁尽调4吟送A调I听2调9琶A少J同H通8君尽1居居AI思B琶落7老破琶谪0谁耙逆9FJBE9小君小A水听8思遁逆君居速C耙水落送8向F少D谁耙破2向遁造退遁D耳61EK尽H听J急倒6者20倒HJ少7374琶小GC居将36听5老者老7琵I逢送C倾将名急L逆C血E名L耳倒1少水行I琵造调名5耙吟急9GL逢逢琵2通水通0破谁0少0E5逆逆速8倾破K3谪谪送谁G者行造退K通后将逢耙居行4同血I水8尽L遁造同琵速#";
		
		// String s1 =
		// "#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行#";
		// String s2 =
		// "#谁E听32B吟倾5ID调名BK尽FHL0L少退居倾F1速耙E后31水逆思遁者0L耳将C君谪行87速8逢F倾送4AJ琶送D倒4将7老向速落尽H送倾B小G琶L急A7同G4速1琵谪通BC水G破思E造血落思君8破急5听耙调2L3通少琵吟IEJ退逢2调居落A5造小6小EG耙6耳向逢老4居君耙1琵4逢落逆水遁尽听逆名小1退7D退同逢调倒遁8同3少行尽琶A落血向血急破水琵倾名9破君将后造FB同0小造逆通逆少9耳J送0JD后老C血H吟老通尽后谁君送速向遁琶KDK造者名59G遁6思居急者听破6名C吟水C9居5行A2吟HI思谁听92倒倒将行耳后琶谪J78少血I倒者急KI3同将谁0老6琵耳KF行调者谪退向H通谪谁耙#";
		// String s1 = "abcadba-ccdc-#abcc#";
		// String s2 = "acdbbaa-#acd#-#cbcc#";
		// String s1 = "nm-ab-cd-ae-cf-bx";
		// String s2 = "nb-am-bd-ac-ef-cx";
		// String s1 =
		// "#退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速#";
		// String s2 = Random(s1);
		// String s1 = "#asdsadarhvvsagssgfasfaeberbsasfafadas#";
		// String s2 = Random(s1);
		
		File file3 = new File("C:/Users/85221/Desktop/近似比/(appro)" + Js + ".txt"); // 存放数组数据的文件
		// File file3 = new
		// File("C:/Users/85221/Desktop/temp"+String.format("%.2f",
		// ratio)+".txt"); // 存放数组数据的文件
		FileWriter out3 = new FileWriter(file3); // 文件写入流\

		String[] str1 = s1.split("-");
		int l1 = str1.length;
		String[] str2 = s2.split("-");
		int l2 = str2.length;
		System.out.println("生成基因组A和B:");
		for (int i = 0; i < l1; i++) {
			System.out.println(str1[i]);

			out3.write(str1[i]);

			int x = str1[i].length();
			for (int j = 0; j < x - 1; j++) {
				S.add(String.valueOf(str1[i].charAt(j) + String.valueOf(str1[i].charAt(j + 1))));
			}

		}
		out3.write("\r\n");
		for (int i = 0; i < l2; i++) {
			System.out.println(str2[i]);

			out3.write(str2[i]);

			int x = str2[i].length();
			for (int j = 0; j < x - 1; j++) {
				Q.add(String.valueOf(str2[i].charAt(j) + String.valueOf(str2[i].charAt(j + 1))));
				T.add(String.valueOf(str2[i].charAt(j) + String.valueOf(str2[i].charAt(j + 1))));
			}
		}

		int c1 = S.size();
		int c2 = Q.size();
		int t;
		if (c1 > c2) {
			t = c1 - c2;
			for (int i = 0; i < t; i++) {
				Q.add("##");
				T.add("##");
			}
		}

		else {
			t = c2 - c1;
			for (int i = 0; i < t; i++) {
				S.add("##");
			}
		}
		int m = S.size();
		int n = Q.size();
		for (int i = 0; i < m; i++) {
			System.out.print(S.get(i) + " ");
		}
		System.out.println();
		for (int i = 0; i < n; i++) {
			System.out.print(Q.get(i) + " ");
		}
		System.out.println();
		for (int i = 0; i < n; i++) {
			System.out.print(T.get(i) + " ");
		}

		for (int i = 0; i < m; i++) {
			int j = 0;
			while (true) {
				if (S.get(i).equals(Q.get(j)) || reversel1(S.get(i)).equals(Q.get(j))) {
					Q.remove(j);
					break;
				} else {
					if (j == Q.size() - 1) {
						break;
					}
					j++;
				}
			}
		}
		for (int i = 0; i < T.size(); i++) {
			int j = 0;
			while (true) {
				if (S.get(j).equals(T.get(i)) || reversel1(S.get(j)).equals(T.get(i))) {
					S.remove(j);
					break;
				} else {
					if (j == S.size() - 1) {
						break;
					}
					j++;
				}
			}
		}

		System.out.println();
		int m1 = S.size();
		int n1 = Q.size();

		for (int i = 0; i < m1; i++) {
			AdjacencyBreakpoint a = new AdjacencyBreakpoint();
			a.s = String.valueOf(S.get(i));
			a.index = i + 1;
			A.add(a);
		}
		for (int i = 0; i < n1; i++) {
			AdjacencyBreakpoint b = new AdjacencyBreakpoint();
			b.s = String.valueOf(Q.get(i));
			b.index = i + 1 + m1;
			B.add(b);
		}

		// for (int i = 0; i < n1; i++) {
		// System.out.print(A.get(i).first + A.get(i).second + " ");
		// }
		// System.out.println();
		// for (int i = 0; i < n1; i++) {
		// System.out.print(B.get(i).first + B.get(i).second + " ");
		// }
		// System.out.println();
		System.out.println("生成了A和B各自断点");
		for (int i = 0; i < n1; i++) {
			System.out.print(S.get(i) + " ");
		}
		System.out.println();
		for (int i = 0; i < n1; i++) {
			System.out.print(Q.get(i) + " ");
		}
		String A1 = " ";
		String B1 = " ";
		for (int i = 0; i < n1; i++) {
			A1 = A1 + S.get(i);
			B1 = B1 + Q.get(i);
		}
		System.out.println();
		System.out.println("******************");
		// 打印邻接组成的字符串
		System.out.println(A1);
		char[] a1 = A1.toCharArray();
		char[] b1 = B1.toCharArray();
		// 打印邻接组成的字符串
		System.out.println(B1);
		System.out.println("******************");
		System.out.println();
		length = 4 * n1;

		// 初始化黑边矩阵
		Black = new int[length + 1][length + 1];
		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				Black[i][j] = 0;
			}
		}
		for (int i = 1; i <= length; i = i + 2) {
			Black[i][i + 1] = 1;
			Black[i + 1][i] = 1;
		}

		// 初始化灰边矩阵

		Grey = new int[length + 1][length + 1];
		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				Grey[i][j] = 0;
			}
		}
		for (int i = 1; i <= length / 2; i++) {
			for (int j = 1; j <= length / 2; j++) {
				if (a1[i] == b1[j]) {
					Grey[i][j + length / 2] = 1;
					Grey[j + length / 2][i] = 1;
				}
			}
		}
		// PrintB(Black);
		// PrintG(Grey);

		// File(Black, Grey);
		// File file2 = new File("F:/有关断点消除/LINGO10/n.txt"); // 存放数组数据的文件
		// FileWriter out2 = new FileWriter(file2); // 文件写入流\
		// String s0 = "" + length;
		// out2.write(s0);
		// out2.close();
		// System.out.println(length);

		// 为了把所有可能形成的6-圈找到，先预处理，C(n，4)
		for (int i = 0; i < m1; i++) {
			for (int j = i + 1; j < m1; j++) {
				Seg4 s = new Seg4();
				s.str = A.get(i).s + A.get(j).s;
				s.first = A.get(i).index;
				s.second = A.get(j).index;
				AS4.add(s);
			}
		}

		for (int i = 0; i < n1; i++) {
			for (int j = i + 1; j < n1; j++) {
				Seg4 s = new Seg4();
				s.str = B.get(i).s + B.get(j).s;
				s.first = B.get(i).index;
				s.second = B.get(j).index;
				BQ4.add(s);
			}
		}

		// 为了把所有可能形成的6-圈找到，先预处理，C(n，6)
//		for (int i = 0; i < m1; i++) {
//			for (int j = i + 1; j < m1; j++) {
//				for (int k = j + 1; k < m1; k++) {
//					Seg6 s = new Seg6();
//					s.setStr(A.get(i).s + A.get(j).s + A.get(k).s);
//					s.setFirst(A.get(i).index);
//					s.setSecond(A.get(j).index);
//					s.setThird(A.get(k).index);
//					AS6.add(s);
//					// AS6.add(A.get(i).s + A.get(j).s +
//					// A.get(k).s+","+A.get(i).index+","+A.get(j).index+","+A.get(k).index);
//				}
//			}
//		}

//		for (int i = 0; i < n1; i++) {
//			for (int j = i + 1; j < n1; j++) {
//				for (int k = j + 1; k < n1; k++) {
//					Seg6 s = new Seg6();
//					s.setStr(B.get(i).s + B.get(j).s + B.get(k).s);
//					s.setFirst(B.get(i).index);
//					s.setSecond(B.get(j).index);
//					s.setThird(B.get(k).index);
//					BQ6.add(s);
//					// BQ6.add(B.get(i).s + B.get(j).s +
//					// B.get(k).s+","+B.get(i).index+","+B.get(j).index+","+B.get(k).index);
//				}
//			}
//		}
		System.out.println("能到这里");
		 for (int i = 0; i < n1 * (n1 - 1) / 2; i++) {
		 System.out.println(AS4.get(i).str + " " + AS4.get(i).first + " " +
		 AS4.get(i).second);
		 }
		//
		// for (int i = 0; i < n1 * (n1 - 1) * (n1-2)/ 6; i++) {
		// System.out.println(AS6.get(i).str + " " + AS6.get(i).first + " " +
		// AS6.get(i).second + " " + AS6.get(i).third);
		// }

		// #abccaba#
		// #abcabac#
		int as4 = AS4.size();
		int bq4 = BQ4.size();
		int as6 = AS6.size();
		int bq6 = BQ6.size();
		String x, y;
		// 找出可能的4-圈
		for (int i = 0; i < as4; i++) {
			for (int j = 0; j < as4; j++) {
				x = AS4.get(i).str;
				y = BQ4.get(j).str;
				char[] x1 = x.toCharArray();
				char[] y1 = y.toCharArray();
				Arrays.sort(x1);
				Arrays.sort(y1);
				x = String.valueOf(x1);
				y = String.valueOf(y1);
				if (x.equals(y)) {
//					 System.out.println("{" + AS4.get(i).first + "," +
//					 AS4.get(i).second + "," + BQ4.get(j).first + ","
//					 + BQ4.get(j).second + "}");
					Collection.add("" + AS4.get(i).first + "," + AS4.get(i).second + "," + BQ4.get(j).first + ","
							+ BQ4.get(j).second);
					Collection6.add("" + AS4.get(i).first + "," + AS4.get(i).second + "," + BQ4.get(j).first + ","
							+ BQ4.get(j).second);
				}
			}
		}
		System.out.println("Collection中有" + Collection.size()+ "个元素");
		NewLocalMaxPacking4();//此函数调用至少存在一个4-cycle可以使用，如若没有，调用4,6-圈程序
		// 先用选出的4-圈筛选一遍，得出AS46和BQ46，然后再从其中找出可能的6-圈
//		for (int i = 0; i < as6; i++) {
//			int flag = 0;
//			String[] b = { String.valueOf(AS6.get(i).first), String.valueOf(AS6.get(i).second),
//					String.valueOf(AS6.get(i).third) };
//			for (int j = 0; j < localmax4; j++) {
//				String[] a = Packing.get(j).split(",");
//				if (!MergeSort(a, b)) {
//					flag++;
//				}
//				if (flag > 0)
//					break;
//			}
//			if (flag == 0)
//				AS46.add(AS6.get(i));
//		}
//
//		for (int i = 0; i < as6; i++) {
//			int flag = 0;
//			String[] b = { String.valueOf(BQ6.get(i).first), String.valueOf(BQ6.get(i).second),
//					String.valueOf(BQ6.get(i).third) };
//			for (int j = 0; j < localmax4; j++) {
//				String[] a = Packing.get(j).split(",");
//				if (!MergeSort(a, b)) {
//					flag++;
//				}
//				if (flag > 0)
//					break;
//			}
//			if (flag == 0)
//				BQ46.add(BQ6.get(i));
//		}
//		int as46 = AS46.size();
//		int bq46 = BQ46.size();

		// 找出可能的6-圈
//		for (int i = 0; i < as46; i++) {
//			for (int j = 0; j < bq46; j++) {
//				x = AS46.get(i).str;
//				y = BQ46.get(j).str;
//				char[] x1 = x.toCharArray();
//				char[] y1 = y.toCharArray();
//				Arrays.sort(x1);
//				Arrays.sort(y1);
//				x = String.valueOf(x1);
//				y = String.valueOf(y1);
//				if (x.equals(y)) {
//					Collection6.add("" + AS46.get(i).first + "," + AS46.get(i).second + "," + AS46.get(i).third + ","
//							+ BQ46.get(j).first + "," + BQ46.get(j).second + "," + BQ46.get(j).third);
//				}
//			}
//		}
//		System.out.println("选定Collection6结束");
//
//		for (int i = 0; i < Collection6.size(); i++) {
//			System.out.println(Collection6.get(i));
//		}
//		NewLocalMaxPacking6();
//		System.out.println();

		int[][] cp = new int[Collection.size()][Collection.size()];
		int[][] cp6 = new int[Collection6.size()][Collection6.size()];
		// for (int i = 0; i < Collection.size(); i++) {
		// for (int j = 0; j < Collection.size(); j++) {
		// cp[i][j] = 0;// 0代表不冲突，1代表冲突
		// }
		// }
		System.out.println("不冲突的4-圈：");
		for (int i = 0; i < Packing.size(); i++) {
			System.out.println(Packing.get(i));
		}
		System.out.println("不冲突的4,6-圈:");
		for (int i = 0; i < Packing6.size(); i++) {
			System.out.println(Packing6.get(i));

		}
		System.out.println("*****************");

		improved1(cp);
		for (int i = 0; i < Packing.size(); i++) {
			System.out.println(Packing.get(i));
		}
		System.out.println("以上4圈的2-optimal结束");
		//improved1_6(cp6);
		for (int i = 0; i < Packing6.size(); i++) {
			System.out.println(Packing6.get(i));
		}
		System.out.println("以上4,6的圈2-optimal结束");

		out3.write("\r\n");
		out3.write("断点数:" + length / 4);
		out3.write("\r\n");
		out3.write("最最优圈数:" + length / 8);
		out3.write("\r\n");
		out3.write("找到的4圈个数：");
		out3.write("\r\n");
		out3.write("collection:" + Collection.size());
		out3.write("\r\n");
		out3.write("极大packing:" + localmax4);
		out3.write("\r\n");
		out3.write("2-optimal:" + optimal4);
		out3.write("\r\n");
		out3.write("找到的4,6圈个数：");
		out3.write("\r\n");
		out3.write("collection:" + Collection6.size());
		out3.write("\r\n");
		out3.write("极大packing:" + localmax6);
		out3.write("\r\n");
		out3.write("2-optimal:" + optimal6);
		out3.write("\r\n");
		System.out.println("as4:" + as4 + "," + "bq4:" + bq4);
		System.out.println("as6:" + as6 + "," + "bq6:" + bq6);
//		System.out.println("as46:" + as46 + "," + "bq46:" + bq46);
		System.out.println("找到的4圈个数：" + "极大为" + localmax4 + "," + "2-optimal:" + optimal4);
		System.out.println("找到的4,6圈个数：" + "极大为" + localmax6 + "," + "2-optimal:" + optimal6);
		System.out.println(Collection.size());
		System.out.println(Collection6.size());

		// out3.write("不同基因数共计:" + solvestr.getMap().size());
		// out3.write("\r\n");
		// out3.write(solvestr.getRep());
		// out3.write("\r\n");
		// out3.write("" + (double) (length / 4 - packing2) / (length / 4 -
		// (int) (length / 8)));
		// out3.write("\r\n");
		// out3.write("3-optimal:" + packing3);
		// out3.write("\r\n");
		// out3.write("" + (double) (length / 4 - packing3) / (length / 4 -
		// (int) (length / 8)));
		// out3.write("\r\n");
		// out3.write("4-optimal:" + packing4);
		// out3.write("\r\n");
		// out3.write("" + (double) (length / 4 - packing4) / (length / 4 -
		// (int) (length / 8)));
		// out3.write("\r\n");
		// out3.write("\r\n");
		// out3.write("\r\n");
		// System.out.println("*********************");
		// System.out.println();
		// System.out.println(length / 8);
		// System.out.println("collection:" + Collection.size());
		// System.out.println("极大packing:" + localmax4);
		// System.out.println("2-optimal:" + packing2);
		//
		// System.out.println((double) (length / 4 - Packing.size()) / (length /
		// 4 - (int) (length / 8)));
		//
		out3.close();
	}

	public static void main(String[] args) throws IOException, CloneNotSupportedException {

		// int COUNT = 30;
		// while (COUNT-- > 0) {
		// Start(COUNT);
		// }
		new R2A3();

	}
}