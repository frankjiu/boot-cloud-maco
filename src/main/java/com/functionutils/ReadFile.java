package com.functionutils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * 读取文件夹目录
 * @author xinbe
 *
 */
public class ReadFile {
	public static void readDir(String fileDir) {
		List<File> fileList = new ArrayList<File>(); //创建list集合
		File file = new File(fileDir);
		File[] files = file.listFiles(); // 获取目录下所有文件或文件夹集合
		if (files == null) { // 如果目录为空则退出
			return;
		}
		// 遍历文件夹集合
		for (File f : files) {
			if (f.isFile()) {
				fileList.add(f); //如果是文件,则添加到list集合
			} else if (f.isDirectory()) { //如果是目录,递归调用readDir() 查找
				System.out.println(">>> " + f.getAbsolutePath()); //打印文件夹名称
				readDir(f.getAbsolutePath());
			}
		}
		// 遍历文件集合
		for (File f : fileList) {
			System.out.println("... " + f.getName()); //打印文件名称
		}
	}

	public static void main(String[] args) {
		readDir("C:/used/数据戒毒局");
		
		System.out.println("'你好!'");
		for (int i = 0; i < 10; i++) {
			if (i == 3) {
				break;
			}
			System.out.println(i + "");
			
		}
		for (int i = 0; i < 1000000000; i++) {
			if (i == 3) {
				continue;
			}
			System.out.println(i + "");
		}
	}

}







