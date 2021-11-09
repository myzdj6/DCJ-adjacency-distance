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
		// Ϊ�˰����п����γɵ�6-Ȧ�ҵ�����Ԥ����C(n��4)
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
		// �ҳ����ܵ�4-Ȧ
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
	// ����ͻ�����ÿ�
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
					temp[j] = 0;// 0�����ͻ
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
					temp6[j] = 0;// 0�����ͻ
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
		// Packing6.add(Collection6.get(0)); //�˴�ע������ΪPacking6�Ѿ���һЩֵ�ˣ���Semi(��ʱ)ֵ
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
				cp[i][j] = 0;// 0������ͻ��1�����ͻ
			}
		}
	}

	public static void Pre6(int[][] cp) {
		for (int i = 0; i < Collection6.size(); i++) {
			for (int j = 0; j < Packing6.size(); j++) {
				String[] a = Collection6.get(i).split(",");
				String[] b = Packing6.get(j).split(",");
				if (!MergeSort(a, b))// �����ͻ
					cp[i][j] = 1;// 1�����ͻ
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
		// break;// ����i=1,j=3ʱ,���������packing��,��ʱ��α���?������j =
		// // j+1���ɿ���,��������,��Ϊ�����Ե�i�Ѿ�������packing,֮���Ȼ�������
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
					break;// ����i=1,j=3ʱ,���������packing��,��ʱ��α���?������j =
							// j+1���ɿ���,��������,��Ϊ�����Ե�i�Ѿ�������packing,֮���Ȼ�������
				}

			}
		}
		optimal6 = Packing6.size();

	}
	// ע�������һ���Լ����Ƿ���ţ�����֮ǰ��ÿһ��ɾ1��t��t���ڵ���3ʱ��������t-2������Ȳ��ӣ�����һ������ǰ�߲�
	// ԭ���ǣ�ÿһ�ε������ڽ��������ǲ��ӣ�ʩչ�ռ��������������Ż������Ӹ����Ż�

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
	// ���ϴ���2019.4.26������ע��ʱ��ΪfeAS4ible�����仯�ˣ�����Ҫ���ϴ����

	public static void Pr(int[][] cp) {
		for (int i = 0; i < Collection.size(); i++) {
			for (int j = 0; j < Collection.size(); j++) {
				cp[i][j] = 0;// 0������ͻ��1�����ͻ
			}
		}
	}

	// Ԥ����, ��Ԥ����������Collectionÿһ��set��Packing�еĳ�ͻ,ÿһ�μ���ɾ��Packing�е�set�󣬶���ҪԤ����
	// �˴��Ƿ���ԸĽ���˭���������˭�޸����¾Ϳ��ԡ�
	public static void Pre(int[][] cp) {
		for (int i = 0; i < Collection.size(); i++) {
			for (int j = 0; j < Packing.size(); j++) {
				String[] a = Collection.get(i).split(",");
				String[] b = Packing.get(j).split(",");
				if (!MergeSort(a, b))// �����ͻ
					cp[i][j] = 1;// 1�����ͻ
			}
		}
	}

	// �˷��������жϣ�Collection�������set�Ƿ��ܲ��뵽Packing��
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
	// f = l;// flag��ʾCollection�������set�����Packing�ĸ�set��ͻ
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
		// f = l;// flag��ʾCollection�������set�����Packing�ĸ�set��ͻ
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
		// count2 == pack - 2 ��ʾ�������Ͻ����͵�ǰpacking�е�������ͻ
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
		// count2 == pack - 2 ��ʾ�������Ͻ����͵�ǰpacking�е�������ͻ
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
						break;// ����i=1,j=3ʱ,���������packing��,��ʱ��α���?������j =
								// j+1���ɿ���,��������,��Ϊ�����Ե�i�Ѿ�������packing,֮���Ȼ�������
					} else {
						s++;
					}
				}
			}
			if (s == Collection.size() * (Collection.size() - 1) / 2)
				break;
		}
		// ע�������һ���Լ����Ƿ���ţ�����֮ǰ��ÿһ��ɾ1��t��t���ڵ���3ʱ��������t-2������Ȳ��ӣ�����һ������ǰ�߲�
		// ԭ���ǣ�ÿһ�ε������ڽ��������ǲ��ӣ�ʩչ�ռ��������������Ż������Ӹ����Ż�

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
		// ���ϴ���2019.4.26������ע��ʱ��ΪfeAS4ible�����仯�ˣ�����Ҫ���ϴ����
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
							continue Z;// ����i=1,j=3ʱ,���������packing��,��ʱ��α���?������j =
										// j+1���ɿ���,��������,��Ϊ�����Ե�i�Ѿ�������packing,֮���Ȼ�������
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
								continue Z;// ����i=1,j=3ʱ,���������packing��,��ʱ��α���?������j
											// =
											// j+1���ɿ���,��������,��Ϊ�����Ե�i�Ѿ�������packing,֮���Ȼ�������
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
		File file = new File("F:/�йضϵ�����/LINGO10/b.txt"); // ����������ݵ��ļ�
		File file1 = new File("F:/�йضϵ�����/LINGO10/c.txt"); // ����������ݵ��ļ�
		FileWriter out = new FileWriter(file); // �ļ�д����
		FileWriter out1 = new FileWriter(file1); // �ļ�д����

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
		System.out.println("*************�ָ���***************");
	}

	public static void PrintG(int Grey[][]) {
		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				System.out.print(Grey[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("*************�ָ���***************");
	}

	public static String reversel1(String str) {
		return new StringBuffer(str).reverse().toString();
	}

	public static boolean MergeSort(String[] num1, String[] num2) {
		int a = 0;

		int b = 0;

		num3 = new String[num1.length + num2.length];

		for (int i = 0; i < num3.length; i++) {

			if (a < num1.length && b < num2.length) { // �����鶼δ�����꣬�໥�ȽϺ����
				if (Integer.parseInt(num1[a]) == Integer.parseInt(num2[b]))
					return false;
				if (Integer.parseInt(num1[a]) > Integer.parseInt(num2[b])) {
					num3[i] = num2[b];
					b++;
				} else {

					num3[i] = num1[a];

					a++;

				}

			} else if (a < num1.length) { // num2�Ѿ������꣬����Ƚϣ�ֱ�ӽ�ʣ��num1����

				num3[i] = num1[a];

				a++;

			} else if (b < num2.length) { // num1�Ѿ������꣬����Ƚϣ�ֱ�ӽ�ʣ��num2����

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

		// �������� 2��3-optimalΪ6��4-optimalΪ8
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
//		"#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼��#";
//		 String s2 =
//		 "#���Ϻ�ͬС��˭������ͬ˼����˭��ͨ�����������Ͻ��������˼˭���˺�������������������Ұ�ͬС�������;���˼�����������������������Сͬ���˽���ͨСͨ�͵��˺�Ұ�ͬ����ͨ˼����˭�浹������ͨ��˭˼С��#";

		// 500-5
//		 String s1
//		 ="#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��#";
//		 String s2
//		 ="#A3˼������NͨK���fgOGe˭WoT�õ�b��l��DGHͬEE��57ͣkl��Żd����XС��C8Ż��N��Y8nOO�޷���Q��j��4i��Wͬ�͵�mM��I��aͨTSH��С��IRc��7��V��JU��kPK��hS������4K��M7��˼����U9m�쾡f��B2k������Y�������߽�n��FI������ɪ�ټ�ɪͣ��2��nˮ����Q��ͨ2dmWB��ŻSdѪˮŻ��6������hk��i��mEFH��g0Cg2��1g��С��Y��VG��������a����4�ݽ���С8��n˭ͣ�������Ż��C5J08��1oͣM����8��hɪoXͨ˭b7��������9����NioY5��MͨAC��4jd��ilffh��eLU��L��V����Ѫɪi69��TWb����P��X����S����5a�ӵ�N��MD5b6Aͬ��eI0˼L0jZ3��Wo��R��cJ˭IQdP����F��Z����a61��R��k��ˮS��B������LEh����c��mH7O˼��AɪZbZT6��jH��XQ��l��A��Ѫ9P2E3��X��K����Rˮ��KR3Z��FL����˭ͣ��3��VV��P����9F��DD��ͬ��accB��O1DYC��j��ˮ��U��С���ƺ�nBNQ��G������l0U����g������Ѫe��Ѫ˼������4��T��ͬGJ��f��J����e�˰Ҿ�������1��ݷ�#";

		//1000-5-85-41
//		String s1 ="#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů��#";
//		String s2 ="#���κ�������Ȫ��ҹ��ŮNavc�E�����Ͳ�����kH��s����Mq��ΨƧ��p����ȪU������EA������o������������������ȹxj������������ϢƧ�ٹ��²ҹ���ppPs��������ˮ���A�����cʤ�����Mnfll����Crv��ͷ����n��䱸���zM��g�ɶ�z��5ƿ��C����ܸ�lͨ0�ո�������H�ս���ɽ˭�Ĺ�����������W�����ʴ�����޲�����OѪ��F����ͨ��J����UͷV�˾���4��Q����˭QG��ɪJ6ԪԪ�͹������D�ҵ�V6��yS����e��6����������Ѫ���b������l����z�ű�8����XS���СtT����i����x��E�������ۻ�L��������Q��1��0�����2��UT��sk�Ŵ�������S��Ψ��O��ȹ��2˼����3��������mJƿt��dͷv�޷�D�ٺ�U������Ȫͣ����ͨfT����r��������������H������bV�ʽ����콬��˼9q��0fo�ٽ���������U��Hhﵾ�����PE�����ɽ��EOtҹY����u����wz����ȹʰ�o��������ȹС����c�ö�M��������pSˮϢ��Yd0NԪ�����xRF���н�Ů��X4����ͬg���н���Q��X��EQLF��x����������K3���ڻ���1�ư���C���ߴ���s��Y5Ϣ��Ԫ��ƿ��ͣ���λ�����������W8���ͣҹ��aNɽ��L����������ϳ���y��ɽ����������r8��С����O����PILXn�ٶ�i������i��Fҹ������Kg��ͨ��ͬ��������Ż�����ͽ�������ʤ���ڰ˰�5RA����B�ش�1��p�ٹ���ZŻ�Ҳ�ˮͣ4T˼��3�Ѿ�Ƨf��RZ����r��8��PyȪС��ȹ˭Ȫ9ͷ��uK���E�E��������J��6Z��k˭��ƧdΨ������q��mS��1ͬ�2V9˼������kѪ����������3wR��wѪ5������վ�t��bm��k��Ϣ�����EƿMR����sa�Ჿ��oX�����������ejf���������E��h��g�е���PŮ��D����nwG�A��j�������v�������������Ԫ����ʤ�Ŵ󸴹�����Ů1��G��Iye��ʤ������ɪ���վ�ΨeN����WiͣѪ�콭ͷhС��B7KO��x����244˼w��z��h��3���׾���DaY��7������ˮ���C��0ˮ��ͬ��ŻqW���˴���Ƨ���°�a��y���ò�H�ܹ�����ŮAɪ��d����C���۷�ch��n���ƺ�Ϣ��Ż����������ʤI�����ٶ���B��ɪ��8����jB����gu����ͬ����77T��u��i����L����Fc��d�µ�tGͨWȹG��ɽ�ʴ󸴼�m��Ż��vY�����5JZ�������l����uNmj�հ�b��V�ٸ�����oΨɪ����9Z�ͺ�ҹ97bBIeƿ��K��6�Ƽ׾�D��������2�������q�����rI����˭#";
		//1000-5-84-43
//		String s1 = "#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ�ʽ��������E��䱽������۰����������߹�Ů��#";
//		String s2 = "#��t��yϢouF��L���kr��Ψ�а�ͣ�ͺ�sΨɽ�Ž��e��c��������AIͬn������Ѷ���o��CN��5����������j�θ���d���Ⱥ�I��̾�AC��Ѫ����V���Ů������iu������Ƨ��nw��z6����ͷ��S�����׹ʴ�Uʤ˼G��5��4Ż��������c�ѵ߾�bͨ�ƶ�����q������1Ԫa0��P����������D��Q�߰�q�����ͨ��EY���м�Ƨ����U��SQ��t�ж���ƿeJ����ʤO��N������ȹg��Żp��Ѫ����XSH������Ԫ�꾡��ҹ��̸���mER�Ὥ������8��ɪﵾƻ�2����ɪ��j����r��ŮZlŮVС2�������F�����ƹ�7fpKa�������˸���Ѫ��pҹH����ͷ��d�������E�t��5��ƿb��vΨ��l����IiŻ��J�������º�����Z0V����z����y��F��ML����Ƨ����˭��g������I���������ٵ�������x������������3С��Ů������Ȫ��wK����Z����Ż����sIcr������z4��B��J��С�վ���ͬyɪ�˿�����3P��������������n��˼A��۴�����γ�ȪT����ȹ���E˭��i��Ԫ�ƴ���ɪ��������H��Gɽ��2��������4Uͷ��ͨMG��q��O��P����pȹͣ��wPˮ��ɽ�ټ׼�8c������ʤx��OM˼�����S��D��1����j�ڴ�wTkW������˼��f��ͬ��9��aq6Ȫh�ն�ͷv����˭��C��R�ư�����8�䲿��T��Y����K����Q���ۺ�����Ѫ��6����5ˮ��E�������������Ƶ���Ԫ��W����2��˷�7����m��htŮrz�ưܰ�ͬ0QiKy�˹�T��������4cD��������jdͨ����L��Gb��d��kxYɪ����m����b��J�����EEF����������s��X��ȹ���s��B6o�Ʋ���LN��������ҹ��qNB�����v��3hF������ͣ�����t�Ƶߴ��������ξ���ȹʤ�ʾ��ٻ���E��Ψ��shHK�������7��m��A��XRɽUy�й�4rZ˭��5���µ�Ԫ����0d����B��S�El�������n���ɹ�9����Ϣ����8j3����uˮh��7��e��n���E�ް�����g����ΨW�ɽ�����Y���껽��V����x��TxQlͨ�u����Y��ɽ2��06��9������ƿ��������ظ��������ϰ���ƿe1��ƧѪOiW����������P������X��8��MB��1f��9W9�������Lͣͷ��7C������uU��G����N�������������aϢ��Ȫ�̽�������M��C��V�������������ȵ�����Ogwp���ƿXJa������RϢ�������1��b��H��ˮ3fe���Ҵ�ͣz����klҹҹ��f��ͬ����Ȫ��Ag˼oŻƧ��Zk�չ���Ϣ�����R���omvD����С����ʤ��ˮ��D��������˭С���ع���v#";
		 //900-81-47
//		String s1 = "#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��MNOPQRSTUŻVWXYZͣ����ɪa��bc��defgh��ijk��lmn��o��Ϣpqɽrstuvwxyz�������������ĳ����յ����ٹ����ẳ���䰧������Ȫ�����ư�ȹʤ���Ҳ����ɿ���������ƿƧ�ﵴ��ҹ���д����ܴ�����Ψ����ͷ���ջ�����Ԫ��#";
//		String s2 = "#z1����m������ͨ��Ѫ����СWu����������c��J��Z��D��ͬ��QV��GO�����ٵ������䰧����W��˼��j�����Bfu�ݽ���5Ta��������1ҹ8A˭Tl��8��ͨKȪ6I��Zpx��G��ͣ��ƿ������sͨeA��Ψa����P��q��1�����ػ���k���������o����ͬɪU��p��S�����浹H����Ψ����W����7��PȪLͷ��RA�ƶ����㽫rsJ�ٿ�9���½�B�ٰܶ�����ȹ��������mh�����·缱����A������r��96l�����5��zaWhR1�������Ѫ��������С��ͷZ0n������L��ҹ������Ƨ��2ͷ��ҹ����F��f0��nx�����Lv��HNe�����ɽs�����ʻ����nXN��ͣ����ƿ��X��4С��jJzƧT��0��ˮ��HO����Ȫ��fͬC��������F7��Ƨ��bsԪIͣﵴ�5ҹ��oli���Ow������������n1SE��N��G��E�³���ѪU��Fb��������ͷd��g�ݰҷ�Q��H��ϢvhS�Ʒŷ������������8��ȹB��4h����x2��˼ʤrn��3�����˵���y���D��Ϣ��vbP����PcO��U����6����kϢ����pѪa3d��zȪim��C�۳�������������a������G��������ɽ�����cr��YŻq������O6D˭�շ�fM������g��I��Ԫ��c�ݽ���q����e��KQ��z��S�ŵ�M����ȹ˼C�M�ʹ�t��ˮ2����r��˭��7ɽ��4��gBgY����Żvt���ѵƶ�Ƨ��Ӱ�����U����3RK��Co����ɪwƧ��G��S����9�촸��������NŻD��P2w��˼YV��9j�ٲ�U��K��ƿ������kB��9u��Q˼С��ˮ��E���㻽����b���ϴ�����˭N35t��J��y��8��T��ͨ��l��ͣD��ItˮL���˾�7MI��ɪ�uƿ��Ψ����������b4VWdR���������������ϢR��EѪ������s����cyy�۲�lJ�������KC����iLQpi��Ԫ�н�ʤ��Ψ��TZ������˭��v���߳����g����С��������3��w��ʤϢΨ������A����Vej��Żd����7��x��������Z��0��Fq�����ٵƷ���Etƿ��2��������ط�ͬͨXu��5V��kmX������fͬM���ٺ���ʤ�ξ�F����i��6����ҹ��x�ع�������ȹqŻ����H��wˮp����je����0������y���ɪ��ɽ��o��h�˵�����Ԫkɪ������ʤXo��m�������8Yȹ���dɽȪY������ͷ��Ԫ4��ͣ#";
		 //200-5-54-13
//		 String s1 =
//		 "#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��78����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��78����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��78����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��78����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��78#";
//		 String s2 =
//		 "#��81��ˮ6ͬ��˭8��˭6���������˭��ͨС64�������3���ٰ�ͬ13��˭�����˰���5��24ͨ���Ƶ��õ�3С��˼˼�Ҽ�˼5���͵����87����0��˭����2����2��64����0��1��06�����;�ͨ1�߰Ҽ�����2˼5��ˮ0����������2ͬ�������߶�3���Ƽ�0������С����1����7����7����7��ͬ45������������Сˮ������ͨ��4������ˮ����ͨ����С��8��ͬ����3ˮ���ٵ�����58���������������˼��7�ض�������#";

		// 300-5-60-25
//		 String s1 =
//		 "#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��#";
//		 String s2 =
//		 "#˭E��32B����5ID����BK��FHL0L���˾���F1�ٰ�E��31ˮ��˼����0L����C������87��8��F����4AJ����D��4��7�������価H����BСG��L��A7ͬG4��1����ͨBCˮG��˼E��Ѫ��˼��8�Ƽ�5���ҵ�2L3ͨ������IEJ�˷�2������A5��С6СEG��6�������4�Ӿ���1��4������ˮ�ݾ�������С1��7D��ͬ�������8ͬ3���о���A��Ѫ��Ѫ����ˮ������9�ƾ�������FBͬ0С����ͨ����9��J��0JD����CѪH����ͨ����˭�����������KDK������59G��6˼�Ӽ�������6��C��ˮC9��5��A2��HI˼˭��92�������ж�������J78��ѪI���߼�KI3ͬ��˭0��6����KF�е���������Hͨ��˭��#";
		// String s1 =
		// "#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������꼱����0123��4ˮ��5��6��789������ѪA��BCDE��FGHIJKL��#";
		// String s2 =
		// "#˭E��32B����5ID����BK��FHL0L���˾���F1�ٰ�E��31ˮ��˼����0L����C������87��8��F����4AJ����D��4��7�������価H����BСG��L��A7ͬG4��1����ͨBCˮG��˼E��Ѫ��˼��8�Ƽ�5���ҵ�2L3ͨ������IEJ�˷�2������A5��С6СEG��6�������4�Ӿ���1��4������ˮ�ݾ�������С1��7D��ͬ�������8ͬ3���о���A��Ѫ��Ѫ����ˮ������9�ƾ�������FBͬ0С����ͨ����9��J��0JD����CѪH����ͨ����˭�����������KDK������59G��6˼�Ӽ�������6��C��ˮC9��5��A2��HI˼˭��92�������ж�������J78��ѪI���߼�KI3ͬ��˭0��6����KF�е���������Hͨ��˭��#";
		// String s1 = "abcadba-ccdc-#abcc#";
		// String s2 = "acdbbaa-#acd#-#cbcc#";
		// String s1 = "nm-ab-cd-ae-cf-bx";
		// String s2 = "nb-am-bd-ac-ef-cx";
		// String s1 =
		// "#����˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼������˭�ϵ����潫ͬ����С���򵹰�ͨ��˼��#";
		
		
//		 String s11 = "asfasfafbgfs";
//		 String s22 = Random(s11);
//		 String s1 = "#" + s11 + "#";
//		 String s2 = "#" + s22 + "#";
		 File File = new File("D:/�ϵ�����/(appro)30-2-1.txt"); // ����������ݵ��ļ�
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

		File file3 = new File("C:/Users/85221/Desktop/temp" + 1 + ".txt"); // ����������ݵ��ļ�
		// File file3 = new
		// File("C:/Users/85221/Desktop/temp"+String.format("%.2f",
		// ratio)+".txt"); // ����������ݵ��ļ�
		FileWriter out3 = new FileWriter(file3); // �ļ�д����\

		String[] str1 = s1.split("-");
		int l1 = str1.length;
		String[] str2 = s2.split("-");
		int l2 = str2.length;
		System.out.println("���ɻ�����A��B:");
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
		System.out.println("������A��B���Զϵ�");
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
		// ��ӡ�ڽ���ɵ��ַ���
		System.out.println(A1);
		char[] a1 = A1.toCharArray();
		char[] b1 = B1.toCharArray();
		// ��ӡ�ڽ���ɵ��ַ���
		System.out.println(B1);
		System.out.println("******************");
		System.out.println();
		length = 4 * n1;

		// ��ʼ���ڱ߾���
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

		// ��ʼ���ұ߾���

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
		// File file2 = new File("F:/�йضϵ�����/LINGO10/n.txt"); // ����������ݵ��ļ�
		// FileWriter out2 = new FileWriter(file2); // �ļ�д����\
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
				// �Ѷϵ����ɵ㣬��a��b֮���б�
				{
					matrix[A.get(i).index][B.get(j).index] = 1;
					matrix[B.get(i).index][A.get(j).index] = 1;
					edge++;
				}
			}
		}

		File file1 = new File("C:/Users/85221/Desktop/genome.txt"); // ����������ݵ��ļ�
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

		
		
		
				//�������packing(4-Ȧ)
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