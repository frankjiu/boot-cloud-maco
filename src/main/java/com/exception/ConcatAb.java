package com.exception;

import org.junit.Test;

public class ConcatAb {
	
	@Test
	public void print() {
		if (System.out.printf("a") == null) {
			System.out.println("a");
		} else {
			System.out.println("b");
		}
	}
}
