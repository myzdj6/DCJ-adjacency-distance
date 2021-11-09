package eliminate;

import java.util.*;

public class MyClass2 {
	static String[] num3;
	static int length;
	static int f = 0;
	static int f1 = 0;
	static int f2 = 0;
	static int f3 = 0;
	static boolean flag = true;
	private static ArrayList<String> Collection = new ArrayList<String>();
	private static ArrayList<String> Packing = new ArrayList<String>();
	static int packing2;
	static int packing3;
	static int packing4;
	
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

	// Ԥ����, ��Ԥ����������Collectionÿһ��set��Packing�еĳ�ͻ,ÿһ�μ���ɾ��Packing�е�set�󣬶���ҪԤ����
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

	public static void Pr(int[][] cp) {
		for (int i = 0; i < Collection.size(); i++) {
			for (int j = 0; j < Collection.size(); j++) {
				cp[i][j] = 0;// 0������ͻ��1�����ͻ
			}
		}
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
							System.out.println("***********************");
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
		// ���ϴ���2019.4.26������ע��ʱ��Ϊfeasible�����仯�ˣ�����Ҫ���ϴ����
		packing2 = Packing.size();

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

	public static void main(String[] args) {
		// String a = "1,2,8,12";
		// String b = "1,3,13,14";
		// String[] x = a.split(",");
		// String[] y = b.split(",");
		//
		// System.out.println(MergeSort(x, y));
		// Collection.add("1,2,3,4");
		// Collection.add("5,6,7,8");
		// Collection.add("1,10,11,12");
		// Collection.add("2,13,14,15");
		// Collection.add("3,16,17,18");
		// Collection.add("4,19,20,21");
		// Collection.add("5,61,71,81");
		// Collection.add("6,69,79,89");
		// Collection.add("7,63,73,83");
		// Collection.add("7,63,73,89");
		//
		//
		// Packing.add("1,2,3,4");
		// Packing.add("5,6,7,8");

		// Collection.add("1,2,3,4");
		// Collection.add("2,5,6,7");
		// Collection.add("3,8,15,19");
		// Collection.add("2,9,10,11");
		// Collection.add("5,12,13,14");
		//
		// Packing.add("1,2,3,4");

//		Collection.add("1,2,3,4");
//		Collection.add("5,6,7,8");
//		Collection.add("9,10,11,12");
//		Collection.add("1,5,9,112");
//		Collection.add("2,6,10,114");
//		Collection.add("3,7,11,116");
//		Collection.add("4,8,12,118");
//
//		Packing.add("1,2,3,4");
//		Packing.add("5,6,7,8");
//		Packing.add("9,10,11,12");

		
		Collection.add("1,2,3,4");
		Collection.add("5,6,7,8");
		Collection.add("1,9,10,11");
		Collection.add("2,12,13,14");
		Collection.add("3,15,16,17");
		Collection.add("1,5,18,19");
		Collection.add("6,9,20,21");
		Collection.add("7,10,22,23");
		Collection.add("1,2,3,24");
		Collection.add("5,12,15,25");
		Collection.add("13,16,18,26");
		Collection.add("14,17,19,27");
		Packing.add("1,2,3,4");
		Packing.add("5,6,7,8");
		
		
		int[][] cp = new int[Collection.size()][Collection.size()];
		for (int i = 0; i < Collection.size(); i++) {
			for (int j = 0; j < Collection.size(); j++) {
				cp[i][j] = 0;// 0������ͻ��1�����ͻ
			}
		}
		improved1(cp);
		for (int i = 0; i < Packing.size(); i++)
			System.out.println(Packing.get(i));
			System.out.println("********����2-optimal����**********");
		improved2(cp);
		for (int i = 0; i < Packing.size(); i++)
			System.out.println(Packing.get(i));
		System.out.println("********����3-optimal����**********");
		improved3(cp);
		for (int i = 0; i < Packing.size(); i++)
			System.out.println(Packing.get(i));
		System.out.println("********����4-optimal����**********");
		System.out.println("2-optimal:" + packing2);
		System.out.println("3-optimal:" + packing3);
		System.out.println("4-optimal:" + packing4);
		


		// System.out.println(s2);

		// System.out.println(Random(s2));

	}

}
