package G;


import java.util.Arrays;
import java.util.Stack;
 
/**
 * ͨ����������������ͼ���Ƿ���ڻ�
 *
 * @date 2021-01-22 15:18
 * @author hh
 */
public class CycleTest {
    /**
     * ͼ����
     */
    private Graph graph;
 
    /**
     * �����Щ���㱻����
     */
    private boolean[] marked;
 
    /**
     * �Ƿ���ڻ�
     */
    private boolean isHasCycle;
//    public static Stack<Integer> stack = new Stack<>();
    private CycleTest() {
    }
 
    public boolean hasCycle(){
        return isHasCycle;
    }
 
    public CycleTest(Graph graph) {
        this.graph = graph;
        //Ĭ�ϲ����ڻ�
        this.isHasCycle = false;
        marked = new boolean[graph.getVertices()];
        Arrays.fill(marked,Boolean.FALSE);
        this.dfs();
 
    }
 
    
    //�������������ȷ�е�6-step
    public void dfs(){
        for(int v = 0; v < graph.getVertices(); v++ ){
            if(!isMarked(v)){
                this.search(v,v);
            }
        }
    }
  
    private void search(int v,int prev){
        System.out.print(v +" ");
        marked[v] = true;
        for(Integer w : graph.adj(v)){
            if(!isMarked(w)){
                search(w,v);
            }else if (w != prev){
                isHasCycle = true;
            }
            
        }
    }
 
    
  
    

 
    
 
    /**
     * ����w�Ƿ񱻷��ʹ�
     * @param w ��������
     * @return ���Ƿ񱻷���
     */
    private boolean isMarked(int w){
        return this.marked[w];
    }
    
    
    public static void main(String[] args) throws Exception {
        String filename = "C:/Users/85221/Desktop/graph.txt";
        Graph graph = new Graph();
        graph.createGraphByFile(filename);
        CycleTest cycleTest = new CycleTest(graph);
        boolean hasCycle = cycleTest.hasCycle();
        System.out.print("\n�Ƿ���ڻ���" + hasCycle);
    }
    
}
