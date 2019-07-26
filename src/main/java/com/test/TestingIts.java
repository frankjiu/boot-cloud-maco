package com.test;

class Teacher{
	int age = 60;
	String name = "laowang";
	TestingIts s = new TestingIts();
	String result(){
		return s.a + "";
	}
}

public class TestingIts {
	int a = 10;
	public static void main(String[] args) {
		Teacher teacher = new Teacher();
		String result = teacher.result();
		System.out.println(result);
		System.out.println("对象内容为: " + teacher.age + "======" + teacher.name);
		
	}
	
}
