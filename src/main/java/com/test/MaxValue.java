package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxValue {
	public static int w;

	static void test1() {
		System.out.println("-----无返回值类型的return语句测试--------");
		
		//ArrayList<? extends E> arrayList = new ArrayList<? extends E>();
		
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("c");
		list.add("b");
		list.add("d");
		String string = list.get(2);
		if ("c".equals(string)) {
			String string2 = list.remove(2);
			System.out.println(string2);
		}
		int size = list.size();
		System.out.println(size);
		
		// 排序
		Collections.sort(list);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		/*for (int i = 1;; i++) {
			if (i == 4)
				return;
			System.out.println("i = " + i);
		}*/
	}

	public static void main(String[] args) {
		
		test1();
		
		int w = 5;
		System.out.println("w = " + w);
		int[] arr = { 1, 2, 3, 4, 5, 0, 9, 18, 8, 6}; // 定义数组
		int max = arr[0]; // 定义变量max用于记住最大数，首先假设第一个元素为最大值
		
		// 下面通过一个for循环遍历数组中的元素
		for (int x = 1; x < arr.length; x++) {
			if (arr[x] > max) { // 比较 arr[x]的值是否大于max
				max = arr[x];
			}
		}
		System.out.println("最大值: " + max);
	}
}
