package com.functionutils;

import java.util.Timer;
import java.util.TimerTask;

public class TestTask extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("hello world");
		
	}
	
    public static void main(String[] args) {
        //创建定时器对象
        Timer t=new Timer();
        //在3秒后执行MyTask类中的run方法,后面每10秒跑一次
        t.schedule(new TestTask(), 1000,2000);

    }
}