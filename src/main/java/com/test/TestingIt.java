package com.test;

class StudentSpecial{
	int age = 30;
	String name = "frank";
}

public class TestingIt {
	
	public static void main(String[] args) {
		
		try {
			StudentSpecial student = new StudentSpecial();
			System.out.println("学生内容为: " + student.age + "======" + student.name);
			
			Teacher teacher = new Teacher();
			System.out.println("老师内容为: " + teacher.age + "======" + teacher.name);
			System.out.println(3/0);
		} catch (Exception e) {
			e = new RuntimeException("未知异常!");
			e.printStackTrace();
		}
		
		
		
	}
	
}
