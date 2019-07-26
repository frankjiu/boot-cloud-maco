package com.test;

class Point1<T1, T2> {
	T1 x;
	T2 y;

	public T1 getX() {
		return x;
	}

	public void setX(T1 x) {
		this.x = x;
	}

	public T2 getY() {
		return y;
	}

	public void setY(T2 y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point1{" + "x=" + x + ", y=" + y + '}';
	}
}

public class TType {
	public static void main(String[] args) {
		Point1<Integer, Integer> p1 = new Point1<Integer, Integer>();
		p1.setX(10);
		p1.setY(15);
		System.out.println(p1);
		Point1<String, String> p2 = new Point1<String, String>();
		p2.setX("东经116");
		p2.setY("北纬39");
		System.out.println(p2);
	}
}

