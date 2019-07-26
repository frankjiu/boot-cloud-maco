package com.thread;

public class RunnableDemo implements Runnable {
	private Thread t;
	private String threadName;

	public RunnableDemo(String name) {
		threadName = name;
	}

	public void run() {
		try {
			System.out.println("Thread: " + threadName);
			Thread.sleep(50);
		} catch (InterruptedException e) {
			System.out.println("Thread " + threadName + " interrupted.");
		}
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}