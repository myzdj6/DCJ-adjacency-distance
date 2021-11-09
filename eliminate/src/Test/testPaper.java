package Test;

import gurobi.*;

public class testPaper {
	
	static int V1 = 10;
	static int V2 = 10;
	static int[][] edges = new int[V1][V2];
	
	public static void init() {
		for(int i=0; i<V1; i++)
			for(int j=0; j<V2; j++)
				edges[i][j] = 0;
		
		edges[3][4] = 1;
		edges[4][5] = 2;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		init();
		
		try {
			GRBEnv env = new GRBEnv("mip1.log");
			GRBModel model = new GRBModel(env);
			
			// variables z, x, y1->yi, y2->yj
			GRBVar constant1;
			
			GRBVar[] z = new GRBVar[V1];
			GRBVar[][] x = new GRBVar[V1][V2];
			
			GRBVar[] y1 = new GRBVar[V1];
			GRBVar[] y2 = new GRBVar[V2];
			
			for(int i=0; i<V1; i++) {
				z[i] = model.addVar(0.0, 1.0, 0.0, GRB.CONTINUOUS, "z_"+i);
				y1[i] = model.addVar(0, V1+1, 0, GRB.INTEGER, "y1_"+i);
				for(int j=0; j<V2; j++) {
					x[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x_"+i+""+j);
				}
			}
			for(int j=0; j<V2; j++) {
				y2[j] = model.addVar(0, V2+1, 0, GRB.INTEGER, "y2_"+j);
			}
			
			constant1 = model.addVar(0, 1, 1, GRB.INTEGER, "constant");
			
			model.update();
			
			// Objective: sum of z
			GRBLinExpr linExpr = new GRBLinExpr();
			for(int i=0; i<V1; i++)
			linExpr.addTerm(1.0, z[i]);
			model.setObjective(linExpr, GRB.MAXIMIZE);
			
			//Constraints
			for(int i=0; i<V1; i++) {
				linExpr = new GRBLinExpr();
				linExpr.addTerm(i, z[i]);
				model.addConstr(linExpr, GRB.LESS_EQUAL, y1[i], "c1_"+i);
			}
			
			//TODO:constaints related to edges.
			
			int i=0, j=0;
			linExpr = new GRBLinExpr();
			linExpr.addTerm(1, y1[i]);
			linExpr.addTerm(-1, y2[j]);
			GRBLinExpr linExpr1 = new GRBLinExpr();
			linExpr1.addTerm(-i, x[i][j]);
			linExpr1.addTerm(i, constant1);
			model.addConstr(linExpr, GRB.LESS_EQUAL, linExpr1, "c2_"+i);
			model.addConstr(1.0, GRB.EQUAL, constant1, "c_constant");
				
			
			// Optimize
			
		    model.optimize();
		    System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
		    
		    // dispose of model and env
		    model.dispose();
		    env.dispose();
				
		} catch (GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " +
                      e.getMessage());
		}

	}

}
