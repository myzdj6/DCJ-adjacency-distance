package eliminate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HuntforCycle {
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

	
	
	public static void collection4(int m1){
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

		for (int i = 0; i < m1; i++) {
			for (int j = i + 1; j < m1; j++) {
				Seg4 s = new Seg4();
				s.str = B.get(i).s + B.get(j).s;
				s.first = B.get(i).index;
				s.second = B.get(j).index;
				BQ4.add(s);
			}
		}
		int as4 = AS4.size();
		int bq4 = BQ4.size();
		
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
					// System.out.println("{" + AS4.get(i).first + "," +
					// AS4.get(i).second + "," + BQ4.get(j).first + ","
					// + BQ4.get(j).second + "}");
					Collection.add("" + AS4.get(i).first + "," + AS4.get(i).second + "," + BQ4.get(j).first + ","
							+ BQ4.get(j).second);
					Collection6.add("" + AS4.get(i).first + "," + AS4.get(i).second + "," + BQ4.get(j).first + ","
							+ BQ4.get(j).second);
				}
			}
		}
		
	}
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
		// Packing6.add(Collection6.get(0)); //此处注释是因为Packing6已经有一些值了，是Semi(临时)值
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

	public static void SemiLocalMaxPacking(ArrayList<String> Collection) {
		int flag = 0;
		int[] temp6 = new int[Collection.size()];
		Packing6.add(Collection.get(0));
		for (int i = 0; i < Collection.size(); i++) {
			flag = 0;
			String[] a = Collection.get(i).split(",");
			for (int j = 0; j < Packing6.size(); j++) {
				String[] b = Packing6.get(j).split(",");
				if (!MergeSort(a, b)) {
					flag++;
				}
				if (flag > 0)
					break;
			}
			if (flag == 0)
				Packing6.add(Collection.get(i));
		}
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
						// for (int i1 = 0; i1 < Packing.size(); i1++)
						// System.out.println(Packing.get(i1));
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
	public static ArrayList<AdjacencyBreakpoint> getA() {
		return A;
	}

	public static void setA(ArrayList<AdjacencyBreakpoint> a) {
		A = a;
	}

	public static ArrayList<AdjacencyBreakpoint> getB() {
		return B;
	}

	public static void setB(ArrayList<AdjacencyBreakpoint> b) {
		B = b;
	}

	private static ArrayList<AdjacencyBreakpoint> B = new ArrayList<AdjacencyBreakpoint>();

	private static ArrayList<AdjacencyBreakpoint> A6 = new ArrayList<AdjacencyBreakpoint>();
	private static ArrayList<AdjacencyBreakpoint> B6 = new ArrayList<AdjacencyBreakpoint>();

	private static ArrayList<Seg4> AS4 = new ArrayList<Seg4>();
	private static ArrayList<Seg4> BQ4 = new ArrayList<Seg4>();

	private static ArrayList<Seg6> AS6 = new ArrayList<Seg6>();
	private static ArrayList<Seg6> BQ6 = new ArrayList<Seg6>();

	private static ArrayList<Seg6> AS46 = new ArrayList<Seg6>();
	private static ArrayList<Seg6> BQ46 = new ArrayList<Seg6>();

	public static ArrayList<String> getCollection() {
		return Collection;
	}

	public static void setCollection(ArrayList<String> collection) {
		Collection = collection;
	}

	private static ArrayList<String> Collection = new ArrayList<String>();
	private static ArrayList<String> Collection6 = new ArrayList<String>();
	private static ArrayList<String> SemiCollection6 = new ArrayList<String>();

	public static ArrayList<String> getPacking() {
		return Packing;
	}

	public static void setPacking(ArrayList<String> packing) {
		Packing = packing;
	}

	private static ArrayList<String> Packing = new ArrayList<String>();
	private static ArrayList<String> Packing6 = new ArrayList<String>();

	public HuntforCycle() throws IOException {
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

//		 new Test.solvestr();
//		 String s11 = Test.solvestr.getHum();
//		 String s22 = Test.solvestr.getGor();
//		 String s1 = "#" + s11 + "#";
//		 String s2 = "#" + s22 + "#";
		// 100-5
//		 String s1 =
//		"#退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速退送谁老调者逆将同名后小少向倒耙通君思速#";
//		 String s2 =
//		 "#送老后同小耙谁逆名少同思调送谁后通君后者者速老将者少逆君思谁将退后倒向送者者老速向速逆耙耙同小调倒向送君逆思调少速少名将向君调将倒退小同名退将老通小通送调退后耙耙同速名通思向退谁逆倒倒名少通老谁思小君#";

		// 500-5
//		 String s1
//		 ="#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯#";
//		 String s2
//		 ="#A3思送衫耙N通K造恨fgOGe谁WoT琶调b谪l行DGH同EE君57停kl急呕d逆倾X小琶C8呕名N琵Y8nOO恨逢琶Q灯j恨4i江W同送灯mM谪I听a通TSH落小遁IRc耳7耙V送JU破kPK造hS退属名4K衣M7逢思耳吟U9m造尽f名B2k向逆吟Y后污琵者将n送FI遮听污瑟少急瑟停少2调n水急送Q调通2dmWB将呕Sd血水呕破6遮老吟hk老i污mEFH逢g0Cg2者1g速小谪Y灯VG退少少琵a将居4遁江少小8速n谁停倾君江倒呕老C5J08行1o停M调向8琶h瑟oX通谁b7君属谪速9调老NioY5退M通AC居4jd倒ilffh衣eLU后L倾V行遮血瑟i69退TWb急尽P逆X行造S衫衫5a居灯N落MD5b6A同将eI0思L0jZ3衫Wo衣R居cJ谁IQdP属名F后Z耳向a61属R老k名水S属B尽倒尽LEh落遮c破mH7O思灯A瑟ZbZT6耳jH听XQ遁l耳A后血9P2E3倒X污K速衣R水逆KR3Z逆FL后者谁停耙3耙VV急P遁造9F居DD君同江accB恨O1DYC倾j听水倒U者小破破恨nBNQ速G倾吟向l0U江遮g听谪衫血e琵血思吟琵落4污T行同GJ将f者J尽向e退耙君落琶衣1逢遁逢#";

		//1000-5-85-41
//		String s1 ="#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整#";
//		String s2 ="#者梦呼尽耳裳泉商夜哳女Navc镋名关送惨裂污kH谪s铁急Mq唤唯僻邀p调灯泉U名甲向EA泣愁商o哑行唧铁数惨泣岂唤走裙xj逢泣后倒壬名者息僻速棍衣惨棍先ppPs撼浮钿速水逢哭A商碎居c胜落沉沦Mnfll沦银Crv邀头哀酒n酉浔浮先zM琵g壬多z退5瓶钿C浮岂败浮l通0收复愁老泪H颜教岂山谁悄钩耳逢生感行W哑泣咽锤钿岂哭部梁裂O血错F先岁通属J沉悄U头V退酒少4唧Q江愁谁QG申瑟J6元元送故向顿属D耙颠V6天yS裂流e者6唤尽邀岁遮血造锏b天六棒l尽听z放被8走裳XS情哭小tT将哳i锤复x关E棍慧慢慧慧L收饮六倾Q酒1颠0哳钺错2琶UT六sk放锤故数造S速唯沉O将裙多2思钺天3声裳老岁mJ瓶t落d头v哭风D少恨U顿遮敛泉停遁速通fT耳裂r咽落倾壬天泪琵H甲名败bV咽教哑造浆愁思9q尽0fo沦浆关声倒老U败Hh锏君邀饮PE碎申耙山恨EOt夜Y呼唤u耳灯wz向故先故八o裂敛沦先裙小感酉c琶顿M浮向壬颜pS水息灯Yd0N元饮贵贵xRF衫行浆女逆X4八流同g调行将悄Q遁X衫EQLF破x君哀逢属哭K3银遮唤属1灯哀破C慢走锤教s愁Y5息情元吟瓶颜停风梦慧破逆谪老污W8浔造停夜琵aN山碎L恨哳画急呼咽酉沉银y流山浆恨走听颜r8复小画整O污落PILXn速遁i梁倒申i风F夜被将耙Kg后通老同锏梦锏谪哑呕君江送教梁钩尽胜整遮八八5RA破商B关大1泪p少贵梦Z呕耙惨水停4T思锤3裳居僻f急RZ棒遁r泣8倒Py泉小向裙谁泉9头衫uK画镋镋棒名灯饮J惨6Z居k谁吟僻d唯琶谪流q倾mS饮1同锏2V9思部铁吟k血声生八壬走3wR将w血5浔岂唧收酒t颠bm数k退息敛慧镋瓶MR江钩sa钺部退oX错吟部碎钺棍ejf呼被梁琵镋关h耳g感颠梁P女听D听撼nwG浔A梦j者酉倾棒v贵错邀大哑整急元声放胜放大复棍大琶女1衫G风Iye属胜生落衣瑟钿收居唯eN君收Wi停血天江头h小败B7KO裳x六多244思w酉z沉h被3画甲君送DaY撼7钺衫敛水造教C钿0水放同铁呕qW悄退错数僻逆衣哀a情y撼琶部H败贵江数情女A瑟风d恨污C商污逢ch银n调酒后息画呕碎泪唧慢遁胜I生流少顿悄B调瑟撼8多情jB浆岁gu钩急同谪慢77T逆u倒i少逆L申琵Fc慢d衣颠tG通W裙G哀山故大复甲m声呕棒vY哳申调5JZ者铁泪呼l银岁uNmj颜耙b吟V沦感咽遮o唯瑟倾敛9Z送后夜97bBIe瓶行K被6破甲居D整多唧听2钩浔整衣q后顿六rI生感谁#";
		//1000-5-84-43
//		String s1 = "#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故浆商先酉镋酒浔教敛慢慧八六饮浮数走关女整#";
//		String s2 = "#遁t岁y息ouF倒L逆顿kr大唯感哀停送后s唯山放教浔e沦c造饮画浆AI同n属风锏裂顿衫o八CN多5悄哳倒衣吟岂颠j梦复情d听先撼I愁教居AC关血倾向V风逢女生哳吟iu江饮居僻后nw棍z6棍向画头慢S部唤甲故错U胜思G故5裂4呕梁浮愁数c裂颠酒b通酒耳唤关q呼部棒1元a0钿P呼调放申琵D逢Q颠哀q部锏钿通棒EY耳感急僻银撼U梁SQ者t感遁先瓶eJ少走胜O泪N破遮行裙g愁呕p哀血急尽XSH后泪名元申尽浮夜钿教复钺mER钺江愁撼岁落居8尽瑟锏酒慧2岁天瑟调j邀后r恨女Zl女V小2将浔酉裳F生急酒钩7fpKa咽倾衣退复敛血商p夜H整声头锤d敛悄唧镋锏t敛5六瓶b速v唯敛l贵哳Ii呕泪J六遁流衣恨咽衫Z0V声酉z浮惨y商F错ML急银僻遁岂谁浆g哳铁浮I慧听速速沦倒邀衣属x锤逆流被遮壬3小后女耳沦向泉哳wK泣撼Z者送呕琶先sIcr耙退数z4邀B部J衫小收尽泣同y瑟退哭声裳3P错者谪走收铁情n多思A愁慧错沉衫梦沉泉T八落裙碎镋谁大i君元灯锤惨瑟哑送整恨H顿G山衫2银颜听六4U头琶通MG名q琵O画P壬造p裙停琵wP水岂山速甲甲8c向风恨饮胜x唧OM思碎棒慢S琶D咽1行污j遮大wTkW哭污颜思整f遁同裳9大aq6泉h颜耳头v铁污谁酉C钿R灯耙慢耙8落部放T裳Y被吟K钺数Q泣污撼银生血江6敛逆5水调E被浆行属贵梁哑调慧元唤W呼败2逆八逢7多退m慢ht女rz哑败耙同0QiKy八钩T流壬流棒4cD申生倾琶jd通唤倒L梦Gb裂d走kxY瑟破老m调邀b贵J君放镋EF铁声少走天s风X慧裙岂惨s败B6o灯惨天LN画江哀酉夜名qNB逆浔流v者3hF情名顿停碎教沦t破颠锤谪悄邀梦君老裙胜咽尽速画碎E锤唯污shHK天情教唧7耳m少A贵XR山Uy感贵4rZ谁恨5六衣颠元吟急0d沉岁B梁S镋l浔送泣银n唧壬棍9故听息被裂8j3商数u水h哭7整e复n沉镋哭败少老g行申唯W壬浆君造Y沉逢唤属V钩错x整TxQl通锏u梦倾Y故山2听06呼9琶沦颜瓶吟钩钺岁关感造造慢老八琵瓶e1惨僻血OiW饮颜者六碎P将顿哑X商8将MB棍1f倒9W9谪泪关情L停头申7C棒倾遮uU走G数将N浔收声收行泪铁a息君泉商江饮复酉M甲C琵V放破梁唧先落先灯哑名Ogwp多风瓶XJa呼浮哀R息败退钿棍1少b被H老水3fe浆耙大停z遮咽kl夜夜钺f居同甲落泉向Ag思o呕僻灯Zk收故裳息将天酒R逢哭omvD多岂小生悄胜居水钩D属悄破泣谁小送谪关谪v#";
		 //900-81-47
//		String s1 = "#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行MNOPQRSTU呕VWXYZ停属江瑟a污bc衣defgh恨ijk衫lmn遮o灯息pq山rstuvwxyz呼顿邀梁岁岂悄沉碎颜颠梦沦钩被钺撼咽钿哀铁裂情泉棍风哑棒裙胜泣惨部泪壬哭声裳哳银瓶僻愁复锏错多夜生感锤唤败大唧天唯甲申头贵收画放流元故#";
//		String s2 = "#z1收衣m画衫贵通唤血尽者小Wu哀棍棍部钺c壬J调Z天D撼同惨QV江GO急棍少倒哳生落哀调唤W哳思大j顿唧放Bfu遁江声5Ta哀棍耳岂1夜8A谁Tl复8大尽通K泉6I尽Zpx退G后停琵瓶泣棒衫s通eA错唯a破向P行q棒1部逆谪唤落k岁多颜梁贵o唤居同瑟U颠p造S复悄逆倒H钩锤唯哑哳W壬咽7居P泉L头沦RA破顿琵倾将rsJ顿哭9梦衣江B顿败耳锤钩裙衫送名耳mh恨衫衣风急遮哑A哀邀复r天96l落泪颠5衣zaWhR1向老天哭血琶声碎生小唧头Z0n被者铁L败夜颠听撼僻沉2头泣夜琶裂F梦f0向nx听逢感Lv泣HNe感速锏山s泣锤咽画钺棍nXN倒停调灯瓶逆X者4小污jJz僻T棒0岁水哭HO多倾泉壬f同C败裳梁少F7画僻速bs元I停锏错5夜悄oli钿恨Ow岁多风裂行送遮n1SE属N收G唧E衣愁污血U泪Fb速琵逆岂头d泪g遁耙逢Q哀H遮息vhS破放逢属申造呼被呼8生裙B碎4h哑裂x2恨思胜rn愁3银岁退调多y急锏D梁息愁vbP送岂PcO甲U撼铁6惨情k息泣急p血a3d钺z泉im裳C污沉沉钿行哑钿声a谪情梦G故名颜吟山生造感cr风Y呕q居哑流O6D谁收放fM琵呼沉g铁I情元岁c遁江银q银颜e咽KQ感z裂S放颠M将风裙思C锏M咽钩t君水2落申r属谁大7山贵4情gBgY风属呕vt名裳灯顿僻岂居败衫银U复逆3RK后Co破老瑟w僻悄G向急S恨琶9天锤送碎梦属N呕D灯P2w裳思YV申9j沦惨U大K壬瓶名悄生kB倾9u裂Q思小感水故E唧倾唤听少b故老锤名造谁N35t耙J愁y倒8泪T钿通甲l天停D君It水L将退君7MI哭瑟锏u瓶老唯呼裳遮流惨b4VWdR吟听故倾居速邀岂息R耳E血沦吟琵s部遮cyy污惨lJ造耙声颜KC钿收iLQpi放元行将胜碎唯甲TZ声撼铁谁污v邀颠愁呼江g情钺小邀贵撼梁被3尽w咽胜息唯向琶悄A部后Vej哳呕d谪落7申x被耳被者Z壬0沦Fq画画沦灯逢邀Et瓶败2吟贵棒调谪逢同通Xu后5V老kmX沉尽君f同M部少后倒铁胜梦君F申吟i钩6钩颜夜哳x谪故退唧泪裙q呕流恨H甲w水p哭者je流速0银多破y大遁瑟错山行o收h退灯听将元k瑟琶流错胜Xo耙m复钺棒耙8Y裙错甲d山泉Y碎送梁头少元4遁停#";
		 //200-5-54-13
//		 String s1 =
//		 "#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶78#";
//		 String s2 =
//		 "#琵81急水6同者谁8谪谁6逢耳调退逆谁送通小64名逆君倒3向速耙同13谪谁造少退耙听5谪24通老破倒琶调3小向思思耙急思5君送倒后逢87者吟0速谁退送2少速2倒64君老0逢1琶06老向送君通1者耙急向破2思5后水0后逆吟倒向2同名耳少者耳3退破急0造老听小逢听1琵将7听少7君谪7逆同45将琶破吟调名小水耳速老通琶4逢破琵水急名通少吟小者8琶同逆名3水耙速调后造58后将送退琵造听造调思将7谪耳吟琵将#";

		// 300-5-60-25
//		 String s1 =
//		 "#退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行退送谁老调者逆将同名后小少向倒耙通君思速吟造逢急谪听0123耳4水破5琵6琶789尽落倾血A遁BCDE居FGHIJKL行#";
//		 String s2 =
//		 "#谁E听32B吟倾5ID调名BK尽FHL0L少退居倾F1速耙E后31水逆思遁者0L耳将C君谪行87速8逢F倾送4AJ琶送D倒4将7老向速落尽H送倾B小G琶L急A7同G4速1琵谪通BC水G破思E造血落思君8破急5听耙调2L3通少琵吟IEJ退逢2调居落A5造小6小EG耙6耳向逢老4居君耙1琵4逢落逆水遁尽听逆名小1退7D退同逢调倒遁8同3少行尽琶A落血向血急破水琵倾名9破君将后造FB同0小造逆通逆少9耳J送0JD后老C血H吟老通尽后谁君送速向遁琶KDK造者名59G遁6思居急者听破6名C吟水C9居5行A2吟HI思谁听92倒倒将行耳后琶谪J78少血I倒者急KI3同将谁0老6琵耳KF行调者谪退向H通谪谁耙#";
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
		
		
//		 String s11 = "asfasfafbgfs";
//		 String s22 = Random(s11);
//		 String s1 = "#" + s11 + "#";
//		 String s2 = "#" + s22 + "#";
		 File File = new File("D:/断点数据/(appro)30-2-1.txt"); // 存放数组数据的文件
		 FileReader fr = new FileReader(File);
		 BufferedReader br = new BufferedReader(fr);
		 String line = null;
		 String s1="";
		 String s2="";
		 for(int i =0;i<2;i++){
			 if(i==0&&(line = br.readLine()) != null){
				 s1 = line;
			 }
			 if(i==1&&(line = br.readLine()) != null){
				 s2 = line;
			 }
		 }
		 System.out.println(s1);
		 System.out.println(s2);
//			

		File file3 = new File("C:/Users/85221/Desktop/temp" + 1 + ".txt"); // 存放数组数据的文件
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
			a.index = i ;
			A.add(a);
		}
		for (int i = 0; i < n1; i++) {
			AdjacencyBreakpoint b = new AdjacencyBreakpoint();
			b.s = String.valueOf(Q.get(i));
			b.index = i  + m1;
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
		String ns1, ns2;
		int edge = 0;
		int[][] matrix = new int[2*m1][2*m1];
		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < m1; j++) {
				ns1 = A.get(i).s;
				ns2 = B.get(j).s;
				char[] x1 = ns1.toCharArray();
				char[] y1 = ns2.toCharArray();
				Arrays.sort(x1);
				Arrays.sort(y1);
				ns1 = String.valueOf(x1);
				ns2 = String.valueOf(y1);
				if (ns1.charAt(0) == ns2.charAt(0) || ns1.charAt(0) == ns2.charAt(1) || ns1.charAt(1) == ns2.charAt(0)
						|| ns1.charAt(1) == ns2.charAt(1))
				// 把断点抽象成点，点a，b之间有边
				{
					matrix[A.get(i).index][B.get(j).index] = 1;
					matrix[B.get(i).index][A.get(j).index] = 1;
					edge++;
				}
			}
		}

		File file1 = new File("C:/Users/85221/Desktop/genome.txt"); // 存放数组数据的文件
		FileWriter fw = new FileWriter(file1);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(String.valueOf(2 * m1));
		bw.write("\r\n");
		bw.write(String.valueOf(edge));
		bw.write("\r\n");


		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < 2 * m1; j++) {
				System.out.print(matrix[i][j]+" ");
				if (matrix[i][j] == 1) {				
					bw.write(i + " " + j);
					bw.write("\r\n");
				}
			}
			System.out.println();
		}
		bw.flush();
		bw.close();

		
		
		
				//求出极大packing(4-圈)
				//NewLocalMaxPacking4();
				int[][] cp = new int[Collection.size()][Collection.size()];
				System.out.println(Packing.size());
	}

	public static void main(String[] args) throws IOException, CloneNotSupportedException {

		// int COUNT = 30;
		// while (COUNT-- > 0) {
		// Start(COUNT);
		// }
		new HuntforCycle();

	}
}