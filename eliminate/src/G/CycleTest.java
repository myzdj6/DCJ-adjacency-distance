package G;


import java.util.Arrays;
import java.util.Stack;
 
/**
 * 通过深度优先搜索检测图中是否存在环
 *
 * @date 2021-01-22 15:18
 * @author hh
 */
public class CycleTest {
    /**
     * 图对象
     */
    private Graph graph;
 
    /**
     * 标记哪些顶点被访问
     */
    private boolean[] marked;
 
    /**
     * 是否存在环
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
        //默认不存在环
        this.isHasCycle = false;
        marked = new boolean[graph.getVertices()];
        Arrays.fill(marked,Boolean.FALSE);
        this.dfs();
 
    }
 
    
    //深度优先搜索，确切的6-step
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
     * 顶点w是否被访问过
     * @param w ：顶点编号
     * @return ；是否被访问
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
        System.out.print("\n是否存在环：" + hasCycle);
    }
    
}
