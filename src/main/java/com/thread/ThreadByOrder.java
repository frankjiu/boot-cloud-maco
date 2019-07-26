package com.thread;

/**
 * 保证T1,T2,T3按序执行
 * 在T2的run中调用T1.join 让T1执行完成后再让T2执行
 * 在T3的run中调用T2.join 让T2执行完成后再让T3执行
 */
public class ThreadByOrder {
	
	static Thread t1 = new Thread(new Runnable() {
		@Override
		public void run() {
			System.out.println("t1");
		}
	});
	
	static Thread t2 = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				t1.join();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("t2");
		}
	});
	
	static Thread t3 = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				t2.join();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("t3");
		}
	});

	public static void main(String[] args) {
		t1.start();
		t2.start();
		t3.start();
	}
}