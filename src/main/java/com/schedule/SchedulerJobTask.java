package com.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 创建定时任务
 */
@Component
public class SchedulerJobTask {
	private int count = 1;

	@Scheduled(cron = "*/10 * * * * ?")
	private void process() {
		System.out.println("定时任务  " + (count++) + "已开启... 执行完成!");
	}
}