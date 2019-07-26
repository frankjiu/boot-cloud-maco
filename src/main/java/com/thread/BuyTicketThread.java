package com.thread;

//一般都把当前对象this作为同步对象.

//synchronized(同步对象) {
    //需要同步的代码
//}

/**
* 同步:买票  效率低
*/
public class BuyTicketThread implements Runnable {
	
	private int count = 5;
	
	//重写run方法
	public void run() {
		for (int i = 0; i < 10; ++i) {
			//同步块,this表示 com.thread.BuyTicketThread@1c4b4746
			synchronized (this) {
				if (count > 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(count--);
				}
			}
		}
	}

	public static void main(String[] args) {
		BuyTicketThread buyTicketThread = new BuyTicketThread();
		
		//创建多个线程
		Thread thread1 = new Thread(buyTicketThread);
		Thread thread2 = new Thread(buyTicketThread);
		Thread thread3 = new Thread(buyTicketThread);
		
		//启动多个线程
		thread1.start();
		thread2.start();
		thread3.start();
	}

}
