package Test;

import gurobi.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import eliminate.R2A3;

public class testPaper2 {
	static int length;
	static int[][] B;// 存储黑边
	static int[][] G;// 存储灰边

	// public static void init() {
	// for (int i = 0; i < V1; i++)
	// for (int j = 0; j < V2; j++)
	// edges[i][j] = 0;
	//
	// edges[3][4] = 1;
	// edges[4][5] = 2;
	// }

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new R2A3();
		length = R2A3.getLength();
		B = R2A3.getBlack();
		G = R2A3.getGrey();
		// init();
		System.out.println(length);
		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				System.out.print(B[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println("*************************");

		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {
				System.out.print(G[i][j] + " ");
			}
			System.out.println();
		}

		try {
			GRBEnv env = new GRBEnv("mip1.log");
			GRBModel model = new GRBModel(env);

			// variables z, x, y1->yi, y2->yj
			GRBVar constant1;

			GRBVar[] z = new GRBVar[length + 1];
			GRBVar[][] x = new GRBVar[length + 1][length + 1];// 针对灰边的x
			GRBVar[][] b = new GRBVar[length + 1][length + 1];// 针对黑边的g

			GRBVar[] y1 = new GRBVar[length + 1];
			GRBVar[] y2 = new GRBVar[length + 1];

			for (int i = 1; i <= length; i++) {
				for (int j = 1; j <= length; j++) {
					x[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x_" + i + "" + j);
					b[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "b_" + i + "" + j);
				}
			}

			for (int j = 1; j <= length; j++) {
				z[j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "z_" + j);
				y1[j] = model.addVar(0, j, 0, GRB.INTEGER, "y1_" + j);
				y2[j] = model.addVar(0, j, 0, GRB.INTEGER, "y2_" + j);
			}
			constant1 = model.addVar(1, 1, 1, GRB.INTEGER, "constant");
			model.update();

			// Objective: sum of z
			GRBLinExpr linExpr = new GRBLinExpr();
			for (int i = 1; i <= length; i++)
				linExpr.addTerm(1.0, z[i]);
			model.setObjective(linExpr, GRB.MAXIMIZE);

			// Constraints
			// 以下保证圈不被共享
			for (int i = 1; i <= length; i++) {
				linExpr = new GRBLinExpr();
				for (int j = 1; j <= length; j++) {
					linExpr.addTerm(B[i][j], b[i][j]);
				}
				model.addConstr(linExpr, GRB.EQUAL, 1, "c10 _" + i);
			}

			for (int i = 1; i <= length; i++) {
				linExpr = new GRBLinExpr();
				for (int j = 1; j <= length; j++) {
					linExpr.addTerm(G[i][j], x[i][j]);
				}
				model.addConstr(linExpr, GRB.EQUAL, 1, "c11_" + i);
			}
			for (int j = 1; j <= length; j++) {
				linExpr = new GRBLinExpr();
				for (int i = 1; i <= length; i++) {
					linExpr.addTerm(G[i][j], x[i][j]);
				}
				model.addConstr(linExpr, GRB.EQUAL, 1, "c12_" + j);
			}

			// 保证圈不被共享
			// for (int i = 1; i <= length; i++) {
			// GRBLinExpr linExpr1 = new GRBLinExpr();
			// GRBLinExpr linExpr2 = new GRBLinExpr();
			// for (int j = 1; j <= length; j++) {
			// linExpr1.addTerm(G[i][j], x[i][j]);
			// linExpr2.addTerm(G[j][i], x[j][i]);
			// }
			// model.addConstr(linExpr1, GRB.EQUAL, 1, "c11_" + i);
			// model.addConstr(linExpr2, GRB.EQUAL, 1, "c12_" + i);
			//
			// }

			for (int i = 1; i <= length - 1; i++) {
				for (int j = i + 1; j <= length; j++) {
					GRBLinExpr linExpr1 = new GRBLinExpr();
					linExpr1.addTerm(1, y1[i]);
					linExpr1.addTerm(-1, y1[j]);
					GRBLinExpr linExpr2 = new GRBLinExpr();
					linExpr2.addTerm(-i, x[i][j]);
					linExpr2.addTerm(i, constant1);
					GRBLinExpr linExpr3 = new GRBLinExpr();
					linExpr3.addTerm(j, x[i][j]);
					linExpr3.addTerm(-j, constant1);
					model.addConstr(linExpr1, GRB.LESS_EQUAL, linExpr2, "c2_" + i);
					model.addConstr(1.0, GRB.EQUAL, constant1, "c_constant");
					model.addConstr(linExpr1, GRB.GREATER_EQUAL, linExpr3, "c2_" + i);
				}
			}
			for (int i = 1; i <= length - 1; i++) {
				for (int j = i + 1; j <= length; j++) {
					GRBLinExpr linExpr1 = new GRBLinExpr();
					linExpr1.addTerm(1, y1[i]);
					linExpr1.addTerm(-1, y1[j]);
					GRBLinExpr linExpr2 = new GRBLinExpr();
					linExpr2.addTerm(-i, b[i][j]);
					linExpr2.addTerm(i, constant1);
					GRBLinExpr linExpr3 = new GRBLinExpr();
					linExpr3.addTerm(j, b[i][j]);
					linExpr3.addTerm(-j, constant1);
					model.addConstr(linExpr1, GRB.LESS_EQUAL, linExpr2, "c2_" + i);
					model.addConstr(1.0, GRB.EQUAL, constant1, "c_constant");
					model.addConstr(linExpr1, GRB.GREATER_EQUAL, linExpr3, "c2_" + i);

				}
			}
			// 标号相同的圈数
			// for (int i = 1; i <= length; i++) {
			// for (int j = 1; j <= length; j++) {
			// GRBLinExpr linExpr1 = new GRBLinExpr();
			// linExpr1.addTerm(1, y1[i]);
			// linExpr1.addTerm(-1, y2[j]);
			// GRBLinExpr linExpr2 = new GRBLinExpr();
			// linExpr2.addTerm(-i, x[i][j]);
			// linExpr2.addTerm(i, constant1);
			// GRBLinExpr linExpr3 = new GRBLinExpr();
			// linExpr3.addTerm(j, x[i][j]);
			// linExpr3.addTerm(-j, constant1);
			// model.addConstr(linExpr1, GRB.LESS_EQUAL, linExpr2, "c2_"+i);
			// model.addConstr(1.0, GRB.EQUAL, constant1, "c_constant");
			// model.addConstr(linExpr1, GRB.GREATER_EQUAL, linExpr3, "c2_"+i);
			// }
			// }
			//
			//
			// //标号相同圈数
			// for (int i = 1; i <= length; i++) {
			// for (int j = 1; j <= length; j++) {
			// GRBLinExpr linExpr1 = new GRBLinExpr();
			// linExpr1.addTerm(1, y1[i]);
			// linExpr1.addTerm(-1, y2[j]);
			// GRBLinExpr linExpr2 = new GRBLinExpr();
			// linExpr2.addTerm(-i, b[i][j]);
			// linExpr2.addTerm(i, constant1);
			// GRBLinExpr linExpr3 = new GRBLinExpr();
			// linExpr3.addTerm(j, b[i][j]);
			// linExpr3.addTerm(-j, constant1);
			// model.addConstr(linExpr1, GRB.LESS_EQUAL, linExpr2, "c2_"+i);
			// model.addConstr(1.0, GRB.EQUAL, constant1, "c_constant");
			// model.addConstr(linExpr1, GRB.GREATER_EQUAL, linExpr3, "c2_"+i);
			//
			// }
			// }
			//

			// 求尽可能大的圈
			for (int i = 1; i <= length; i++) {
				linExpr = new GRBLinExpr();
				linExpr.addTerm(i, z[i]);
				model.addConstr(linExpr, GRB.LESS_EQUAL, y1[i], "c1_" + i);
			}

			// Optimize
			model.optimize();
			System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
			File file1 = new File("C:/Users/85221/Desktop/最优解/(obj)" + eliminate.R2A3.getJs() +".txt"); // 存放数组数据的文件
			FileWriter fw = new FileWriter(file1);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
			bw.flush();
			bw.close();
			for (int i = 1; i <= length; i++) {
				System.out.println(z[i].get(GRB.StringAttr.VarName) + " " + z[i].get(GRB.DoubleAttr.X));
			}
			System.out.println("******************************");
			for (int i = 1; i <= length; i++) {
				System.out.println(y1[i].get(GRB.StringAttr.VarName) + " " + y1[i].get(GRB.DoubleAttr.X) + " "
						+ y2[i].get(GRB.StringAttr.VarName) + " " + y2[i].get(GRB.DoubleAttr.X));
			}
			System.out.println("******************************");

			// dispose of model and env
			model.dispose();
			env.dispose();

		} catch (GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

	}

}
