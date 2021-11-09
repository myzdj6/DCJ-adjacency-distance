package G;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import eliminate.HuntforCycle;
import gurobi.GRB.IntAttr;

public class Graph {
	// ������
	private int vertices;
	// ����
	private int edges;

	private List<Integer>[] adj;
	private boolean tab;
	private boolean[] marked;
	private int[][] tick;
	private int[][] c;

	private static ArrayList<String> MaxPacking = new ArrayList<String>();
	private static ArrayList<Integer> Col = new ArrayList<Integer>();

	public Graph() {
	}

	public Graph(int vertices) {
		this.init(vertices);
	}

	/**
	 * ���ļ��д���ͼ
	 * 
	 * @param path
	 *            ���ļ�·��
	 */
	public void createGraphByFile(String path) throws Exception {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)))) {
			int vs = Integer.parseInt(bufferedReader.readLine().trim());
			int es = Integer.parseInt(bufferedReader.readLine().trim());
			if (vs <= 0) {
				throw new IllegalArgumentException("���������������0��");
			}
			if (es < 0) {
				throw new IllegalArgumentException("�ߵ�����������ڵ���0��");
			}
			init(vs);
			for (int i = 0; i < es; i++) {
				String[] vl = bufferedReader.readLine().trim().split(" ");
				this.addEdge(Integer.parseInt(vl[0].trim()), Integer.parseInt(vl[1].trim()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���һ����
	 * 
	 * @param v
	 *            ������v���
	 * @param w
	 *            ������w���
	 */
	public void addEdge(int v, int w) {
		this.adj[v].add(w);
		this.adj[w].add(v);
		this.edges++;
	}

	public void removeEdge(int v, int w) {
		if (adj[v].contains((Integer) w))
			this.adj[v].remove((Integer) w);
		if (adj[w].contains((Integer) v))
			this.adj[w].remove((Integer) v);
		this.edges--;
	}

	// ɾ����
	public void remove(List<Integer> v) {
		this.edges = this.edges - v.size();
		v.clear();
	}

	/**
	 * ������v���ڵ����ж����б�
	 * 
	 * @param v
	 *            ��������
	 * @return ��������v���ڵ����ж����б�
	 */
	public Collection<Integer> adj(int v) {
		return adj[v];
	}

	public int getAdjUnvisitedVertex(int v) {
		// ��Ϊ��˳�򷵻�
		// for (int i = 0; i < vertices; i++) {
		// if (adj[v].contains(i) && tick[v][i] == 0) {
		// return i;
		// }
		// }
		// �˴��빹��һ���������,���ȡһ��δ�������ھ�
		Col.clear();
		for (int i = 0; i < vertices; i++) {
			if (adj[v].contains(i) && tick[v][i] == 0) {
				Col.add(i);
			}
		}
		int index = (int) (Math.random() * Col.size());
		if (!Col.isEmpty())
			return Col.get(index).intValue();
		else
			return -1;

	}

	/**
	 * @return �����ض�����
	 */
	public int getVertices() {
		return vertices;
	}

	/**
	 * @return �����ر���
	 */
	public int getEdges() {
		return edges;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(this.edges * this.vertices);
		for (int i = 0; i < this.vertices; i++) {
			stringBuilder.append(i).append("->").append(adj[i]).append("\n");
		}
		return stringBuilder.toString();
	}

	private void init(int vertices) {
		this.vertices = vertices;
		// ��ʼ���ڽӱ�
		this.adj = new LinkedList[vertices];
		this.marked = new boolean[vertices];
		this.tick = new int[vertices][vertices];
		this.c = new int[vertices][vertices];

		for (int i = 0; i < vertices; i++) {
			this.adj[i] = new LinkedList<>();
		}
	}

	public void DFS4(int v) {
		int flag = 0;
		int count = 0;
		Stack<Integer> stack = new Stack<>();
		marked[v] = true;// ���v�Ѿ����ʹ�������ջ��
		stack.push(v);
		String e = "";
		int[] temp4 = new int[vertices];
		int[] temp6 = new int[vertices];

		while (!stack.isEmpty()) {

			// ��Ȧ��û�б�ǹ��������
			int n = getAdjUnvisitedVertex(stack.peek());
			if (n == -1) {
				Arrays.fill(marked, Boolean.FALSE);
				tick = new int[vertices][vertices];
				return;
			}
			tick[stack.peek()][n] = 1;
			tick[n][stack.peek()] = 1;
			if (!marked[n]) {// ��ǰ��δ������
				marked[n] = true;
				stack.push(n);
			}
			// �������1
			// else if(stack.size()==1){
			// Arrays.fill(marked,Boolean.FALSE);
			// tick = new int[vertices][vertices];
			// return;
			// }
			// ����ǹ��˶���ǰ�������ں�̣�����4,6-Ȧ
			else if (n != stack.get((stack.size() - 2))) {
				while (stack.peek() != n) {
					e = e + "," + stack.peek();
					temp4[flag] = stack.peek();
					temp6[flag] = stack.peek();
					stack.pop();
					flag++;
				}
				temp4[flag] = stack.peek();
				temp6[flag] = stack.peek();
				e = stack.pop() + e;
				if (flag != 3) {
					Arrays.fill(marked, Boolean.FALSE);
					tick = new int[vertices][vertices];
					return;
				} else if (flag == 3) {
					// �ж�����4-Ȧ���Ǽ�4-Ȧ
					String[] s = e.split(",");
					String x = "";
					String y = "";
					for (int j = 0; j < s.length; j++) {
						if (vertices / 2 > Integer.parseInt(s[j])) {
							x = x + HuntforCycle.getA().get(Integer.parseInt(s[j])).getS();
						} else
							y = y + HuntforCycle.getB().get(Integer.parseInt(s[j]) - vertices / 2).getS();
					}
					char[] x1 = x.toCharArray();
					char[] y1 = y.toCharArray();
					Arrays.sort(x1);
					Arrays.sort(y1);
					x = String.valueOf(x1);
					y = String.valueOf(y1);
					if (x.equals(y)) {
						// ɾ����Щ�ߺ͵�
						MaxPacking.add(e);
						for (int i = 0; i < 4; i++) {
							int u = temp4[i];
							int k = 0;
							while (k < adj[u].size()) {
								removeEdge(u, adj[u].get(k));

							}
						}
						tab = true;
					}
					flag = 0;
					e = "";
					Arrays.fill(marked, Boolean.FALSE);
					tick = new int[vertices][vertices];
				}
				// else if (flag == 5) {
				// // ɾ����Щ�ߺ͵�
				// MaxPacking.add(e);
				// for (int i = 0; i < 6; i++) {
				// int u = temp6[i];
				// int k = 0;
				// while (k < adj[u].size()) {
				// removeEdge(u, adj[u].get(k));
				// }
				// }
				// flag = 0;
				// e = "";
				// Arrays.fill(marked, Boolean.FALSE);
				// tick = new int[vertices][vertices];
				//
				// }

			}
		}
	}

	public void DFS6(int v) {
		int flag = 0;
		int count = 0;
		Stack<Integer> stack = new Stack<>();
		marked[v] = true;// ���v�Ѿ����ʹ�������ջ��
		stack.push(v);
		String e = "";
		int[] temp4 = new int[vertices];
		int[] temp6 = new int[vertices];

		while (!stack.isEmpty()) {

			// ��Ȧ��û�б�ǹ��������
			int n = getAdjUnvisitedVertex(stack.peek());
			if (n == -1) {
				Arrays.fill(marked, Boolean.FALSE);
				tick = new int[vertices][vertices];
				return;
			}
			tick[stack.peek()][n] = 1;
			tick[n][stack.peek()] = 1;
			if (!marked[n]) {// ��ǰ��δ������
				marked[n] = true;
				stack.push(n);
			}
			// �������1
			// else if(stack.size()==1){
			// Arrays.fill(marked,Boolean.FALSE);
			// tick = new int[vertices][vertices];
			// return;
			// }
			// ����ǹ��˶���ǰ�������ں�̣�����4,6-Ȧ
			else if (n != stack.get((stack.size() - 2))) {
				while (stack.peek() != n) {
					e = e + "," + stack.peek();
					temp4[flag] = stack.peek();
					temp6[flag] = stack.peek();
					stack.pop();
					flag++;
				}
				temp4[flag] = stack.peek();
				temp6[flag] = stack.peek();
				e = stack.pop() + e;
				if (flag != 5) {
					Arrays.fill(marked, Boolean.FALSE);
					tick = new int[vertices][vertices];
					return;
				}
				// else if (flag == 3) {
				//
				// // ɾ����Щ�ߺ͵�
				// MaxPacking.add(e);
				// for (int i = 0; i < 4; i++) {
				// int u = temp4[i];
				// int k = 0;
				// while (k < adj[u].size()) {
				// removeEdge(u, adj[u].get(k));
				//
				// }
				// }
				// flag = 0;
				// e ="";
				// Arrays.fill(marked,Boolean.FALSE);
				// tick = new int[vertices][vertices];
				// }
				else if (flag == 5) {
					String[] s = e.split(",");
					String x = "";
					String y = "";
					for (int j = 0; j < s.length; j++) {
						if (vertices / 2 > Integer.parseInt(s[j])) {
							x = x + HuntforCycle.getA().get(Integer.parseInt(s[j])).getS();
						} else
							y = y + HuntforCycle.getB().get(Integer.parseInt(s[j]) - vertices / 2).getS();
					}
					char[] x1 = x.toCharArray();
					char[] y1 = y.toCharArray();
					Arrays.sort(x1);
					Arrays.sort(y1);
					x = String.valueOf(x1);
					y = String.valueOf(y1);
					// ɾ����Щ�ߺ͵�
					if (x.equals(y)) {
						MaxPacking.add(e);
						for (int i = 0; i < 6; i++) {
							int u = temp6[i];
							int k = 0;
							while (k < adj[u].size()) {
								removeEdge(u, adj[u].get(k));
							}
						}
						tab = true;
					}
					flag = 0;
					e = "";
					Arrays.fill(marked, Boolean.FALSE);
					tick = new int[vertices][vertices];
				}

			}
		}
	}
	
	public void Roll(){
		int count1 = 0;
		while (true) {
			tab = false;
			for (int i = 0; i < vertices; i++) {
				if (!adj[i].isEmpty()) {
					DFS4(i);
				}

			}	
			for (int i = 0; i < vertices; i++) {
				if (!adj[i].isEmpty()) {
					DFS6(i);
				}
			}
			
			for (int i = 0; i < vertices; i++) {
				if (!adj[i].isEmpty()) {
					count1++;
				}
			}
			// ����ͼ�е�Ȧ�����н���>6Ȧ����Ȧ�����������ѭ������Ҫ��DFF4��DFS6�����÷�������
			System.out.println(count1 + "," + vertices);
			if (!tab)
				break;
		}
	}

	public static void main(String[] args) throws Exception {
		new HuntforCycle();
		String filename = "C:/Users/85221/Desktop/genome.txt";
		Graph graph = new Graph();
		graph.createGraphByFile(filename);
		System.out.println(graph);
		System.out.println(graph.vertices);
		int count = 0;
		int count1 = 0;
		int count2 = 0;
		int time = graph.vertices/40;
		//�˴��ǲ��ǿ��԰�2�ȵ㶼ɾ��!
		
		
		while(time-->0){
			graph.Roll();
		}
		
		
		//���ؼ���4-Ȧ
		int count4 = MaxPacking.size();
//		HuntforCycle.setPacking(MaxPacking);
//		HuntforCycle.collection4(graph.vertices/2);
//		int [][] cp = new int[HuntforCycle.getCollection().size()][HuntforCycle.getCollection().size()];
//		HuntforCycle.improved1(cp);
		
//		while (true) {
//			count2=0;
//			graph.tab=false;
//			for (int i = 0; i < graph.vertices; i++) {
//				if (!graph.adj[i].isEmpty()) {
//					graph.DFS6(i);
//				}
//
//			}
//			
//
//			for (int i = 0; i < graph.vertices; i++) {
//				if (!graph.adj[i].isEmpty()) {
//					count2++;
//				}
//			}
//			// ����ͼ�е�Ȧ�����н���>6Ȧ����Ȧ�����������ѭ������Ҫ��DFF4��DFS6�����÷�������
//			System.out.println(count2 + " 111");
//			if (!graph.tab)
//				break;
//		}

		
		
		
		

		for (int i = 0; i < graph.vertices; i++) {
			// int index = (int) (Math.random() * graph.vertices);
			if (!graph.adj[i].isEmpty()) {
				System.out.println(i + "û��");
			} else
				;
			count++;
		}

		for (int i = 0; i < MaxPacking.size(); i++) {
			System.out.println(MaxPacking.get(i));
		}
		System.out.println(MaxPacking.size());

		for (int i = 0; i < MaxPacking.size(); i++) {
			String[] s = MaxPacking.get(i).split(",");
			for (int j = 0; j < s.length; j++) {
				if (graph.vertices / 2 > Integer.parseInt(s[j])) {
					System.out.print(HuntforCycle.getA().get(Integer.parseInt(s[j])).getS() + ",");
				} else
					System.out.print(HuntforCycle.getB().get(Integer.parseInt(s[j]) - graph.vertices / 2).getS() + ",");
			}
			System.out.println();
		}

		System.out.println(MaxPacking.size());
		System.out.println("����4-Ȧ:" + count4+","+"����4,6-Ȧ:" + MaxPacking.size());
		System.out.println("�Ľ����4Ȧ:" + HuntforCycle.getPacking().size());
		
		File file1 = new File("C:/Users/85221/Desktop/4,6-cycle-random/30-1.txt"); // ����������ݵ��ļ�
		FileWriter fw = new FileWriter(file1);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("����4,6-Ȧ:" + MaxPacking.size());
		bw.write("\r\n");

		for (int i = 0; i < MaxPacking.size(); i++) {
			bw.write(MaxPacking.get(i));
			bw.write("\r\n");
		}
		bw.flush();
		bw.close();
	}

}