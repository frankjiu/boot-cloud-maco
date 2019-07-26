package com.thread;

public class ThreadExample {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
		
		// 线程运行的随机性, 先启动的后完成运行
		for (int i = 0; i < 10; i++) {
			System.out.println("启动线程-" + i);
			//Thread.sleep(500);
			new Thread(String.valueOf(i)) {
				public void run() {
					System.out.println("Thread-" + getName() + " is running...");
				}
			}.start();
		}
	}
}
