package eliminate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class shuru {
	static int [][] Black = new int[21][21];
	static int [][] Grey = new int[21][21];
	public static void main(String[] args) throws IOException {
		File file = new File("D:/BaiduNetdiskDownload/lingo���/LINGO10/b.txt"); // ����������ݵ��ļ�
		File file1 = new File("D:/BaiduNetdiskDownload/lingo���/LINGO10/c.txt"); // ����������ݵ��ļ�
		FileReader in = new FileReader(file); // �ļ�д����
		FileReader in1 = new FileReader(file1); // �ļ�д����

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
