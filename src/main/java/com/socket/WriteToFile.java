package com.socket;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class WriteToFile {
	
	@Test
	public void fun3() {
		DataOutputStream dos = null;
		try {
			// DataOutputStream的参数为OutputStream类型的，所以创建一个FileOutputStream对象
			dos = new DataOutputStream(new FileOutputStream("D:\\test1.txt"));
			dos.writeInt(45);
			dos.writeByte(0);
			dos.writeLong(469553L);
			dos.writeUTF("试一下啦");
			dos.writeChar('a');
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
