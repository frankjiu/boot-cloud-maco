package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class EqualsTesting {
	
	@Test
	public void equalsTest() {
		
		Integer a = 3;
		Integer aa = 3;
		
		Integer b = 150;
		Integer bb = 150;
		
		if (a == aa) {
			System.out.println("a == aa"); // true
		}
		if (b == bb) {
			System.out.println("b == bb"); // false(比较引用值, 已经在堆区)
		}
		if (a.equals(aa)) {
			System.out.println("a.equals(aa)"); // true
		}
		if (b.equals(bb)) {
			System.out.println("b.equals(bb)"); // true
		}
		
		
		String str = "a,b,c,,";
		String[] ary = str.split(",");
		for (int i = 0; i < ary.length; i++) {
			
		}
		
		// 数组转list
		List<String> asList = Arrays.asList(ary);
		asList = new ArrayList<String>(Arrays.asList(ary));
		
		int size = asList.size();
		System.out.println("size: " + size);
		
//		for (int i = 0; i < asList.size(); i++) {
//			System.out.println(asList.get(i));
//		}
		
		// 数组转list
		List<String> list = new ArrayList<>();
		Collections.addAll(list, ary);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println("..." + list.get(i) + "\n");
		}
		
		// 获取数组真实长度
		// 预期大于 3，结果是 3
		System.out.println(ary.length);
		
	}
	
}
