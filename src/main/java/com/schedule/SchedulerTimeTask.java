package com.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 创建定时任务
 * @author xinbe
 */
@Component
public class SchedulerTimeTask {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 10000)
	public void reportCurrentTime() {
		System.out.println("定时任务执行完成时间:" + dateFormat.format(new Date()) + "\n");
	}
}