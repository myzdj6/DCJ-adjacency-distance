package eliminate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class shuru {
	static int [][] Black = new int[21][21];
	static int [][] Grey = new int[21][21];
	public static void main(String[] args) throws IOException {
		File file = new File("D:/BaiduNetdiskDownload/lingo软件/LINGO10/b.txt"); // 存放数组数据的文件
		File file1 = new File("D:/BaiduNetdiskDownload/lingo软件/LINGO10/c.txt"); // 存放数组数据的文件
		FileReader in = new FileReader(file); // 文件写入流
		FileReader in1 = new FileReader(file1); // 文件写入流

		for (int i = 1; i <= 21; i++) {
			for (int j = 1; j <= 21; j++) {
				Black[i][j] = in.read();
			}
		}

		for (int i = 1; i <= 21; i++) {
			for (int j = 1; j <= 21; j++) {
				Grey[i][j] = in1.read();
			}
		}

		in.close();
		in1.close();
		
		for (int i = 1; i <= 21; i++) {
			for (int j = 1; j <= 21; j++) {
				System.out.print(Black[i][j]+" ");
			}
			System.out.println();
		}
	}
}
