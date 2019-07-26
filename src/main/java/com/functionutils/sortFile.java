package com.functionutils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class sortFile {
	public static void main(String[] args) {
		// 文件路径
		String path = args[0];
		List<File> list = getFileSort(path);
		for (int i = 0; i < list.size(); i++) {
			if (i >= 3) {
				list.get(i).delete();
			}
		}
		for (File file : list) {
			System.out.println(file.getName() + " : " + file.lastModified());
		}
	}

	/**
	 * 获取目录下所有文件(按时间排序)
	 */
	public static List<File> getFileSort(String path) {
		List<File> list = getFiles(path, new ArrayList<File>());
		if (list != null && list.size() > 0) {
			Collections.sort(list, new Comparator<File>() {
				public int compare(File file, File newFile) {
					if (file.lastModified() < newFile.lastModified()) {
						return 1;
					} else if (file.lastModified() == newFile.lastModified()) {
						return 0;
					} else {
						return -1;
					}

				}
			});
		}
		return list;
	}

	/**
	 * 获取目录下所有文件
	 */
	public static List<File> getFiles(String realpath, List<File> files) {
		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					getFiles(file.getAbsolutePath(), files);
				} else {
					files.add(file);
				}
			}
		}
		return files;
	}
	
	
	
}