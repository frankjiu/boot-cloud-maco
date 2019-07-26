package com.functionutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Oracle备份
 */
public class BackUpDbTask extends TimerTask{
	
	//连接多个库
	//定时任务
	//备份服务器代码
	
	public static final String ROOTPATH = "D:/export_jiu";
	public static final String IP = "localhost";
	public static final String SERVICENAME = "orcl";
	
	/*public static final String USERNAME = "";
	public static final String PASSWORD = "";
	public static final String IP = "";
	public static final String SID = "";
	public static final String BACKPATH = "";
	public static final String FILENAME = "";
	public static final String LOGNAME = "";
	public static final String SERVICENAME = "";*/
	
	/**
	 * 删除前三天的备份文件和日志
	 * @param filePath
	 */
	public static void deleteOld(String filePath){
		//获取系统时间
		String sysDate = DateFormat.getDateInstance().format(new Date());
		//获取指定目录下的文件集并遍历
		List<File> fileList = readDir(filePath);
		System.out.println("\r\t");
		System.out.println(">>>备份目录文件处理开始>>>");
		if (fileList != null && fileList.size() > 0) {
			for (int i = 0; i < fileList.size(); i++) {
				File file = fileList.get(i);
				String fileDate = DateFormat.getDateInstance().format(new Date(file.lastModified()));
				long days = countDiffDays(sysDate,fileDate);
				//long days = (time1-time2)/1000/60/60/24;
				//判断是否过期10天以上并进行删除处理
				if (days > 10) {
					System.err.println("文件<" + file.getName()  + ">的修改时间为: " + fileDate + "  ====  当前系统时间为: " + sysDate + " 为 " + days + " 天以前的数据," + " 正在删除...");
					file.delete();
				} else {
					System.err.println("文件<" + file.getName()  + ">的修改时间为: " + fileDate + "  ====  当前系统时间为: " + sysDate + " 为 " + days + " 天以内的数据.");
				}
			}
		}
	}
	
	/**
	 * 计算相差天数
	 */
	private static long countDiffDays(String strTime1, String strTime2) {
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long days = 0L;
		try {
			Date now = df.parse(strTime1);
			Date date = df.parse(strTime2);
			long l = now.getTime() - date.getTime(); // 获取时间差
			days = l / (24 * 60 * 60 * 1000);
			//long hour = (l / (60 * 60 * 1000) - day * 24);
			//long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			//long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			//System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
			//System.out.println(days + "天");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 读取文件夹目录返回读取的所有文件集合
	 */
	public static List<File> readDir(String fileDir) {
		List<File> fileList = new ArrayList<File>(); //创建list集合
		File file = new File(fileDir);
		File[] files = file.listFiles(); // 获取目录下所有文件或文件夹集合
		if (files == null) { // 如果目录为空则退出
			return null;
		}
		// 遍历文件夹集合
		for (File f : files) {
			if (f.isFile()) {
				fileList.add(f); //如果是文件,则添加到list集合
			} else if (f.isDirectory()) { //如果是目录,递归调用readDir() 查找
				//System.out.println(">>> " + f.getAbsolutePath()); //打印文件夹名称
				readDir(f.getAbsolutePath());
			}
		}
		// 遍历文件集合
		/*for (File f : fileList) {
			//System.out.println("... " + f.getName()); //打印文件名称
		}*/
		return fileList;
	}
	
	/*
	 * 数据库导出工具
	 */
	public static String exportDBTool(String userName, String passWord, String ip, String SID, String backPath, String fileName, String logName, String serviceName) throws InterruptedException {
		File backFile = new File(backPath);
		// 若指定路径无目录则进行创建
		if (!backFile.exists()) {
			backFile.mkdirs();
		}
		//Process process = Runtime.getRuntime().exec("exp " + userName + "/" + passWord + "@" + ip + "/" + SID + " file=" + backPath + "/" + fileName + ".dmp" + " log=" + backPath + "/" + logName + ".log" + " owner=" + serviceName);
		String cmd = "exp " + userName + "/" + passWord + "@" + ip + "/" + SID + " file=" + backPath + "/" + fileName + ".dmp" + " log=" + backPath + "/" + logName + ".log" + " owner=" + serviceName;
		return cmd;
	}
	
	/**
	 * 执行导出库
	 * @param cmd
	 */
	public static void doExport(String cmd){
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			System.out.println("start run cmd=" + cmd);
			// 处理InputStream的线程
			new Thread() {
				@Override
				public void run() {
					BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;
					
					try {
						while ((line = in.readLine()) != null) {
							System.out.println("output: " + line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
			// 处理ErrorStream的线程
			new Thread() {
				@Override
				public void run() {
					BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String line = null;
					
					try {
						while ((line = err.readLine()) != null) {
							System.out.println("err: " + line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							err.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
			process.waitFor();
			System.err.println("finish run cmd=" + cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void run() {
		try {
			//String cmd = " ping 192.168.1.111 "; //测试命令
			//删除旧文件
			long st = new Date().getTime(); //开始时间
			deleteOld(ROOTPATH);
			//doExport(cmd); //测试调用
			String mark = DateFormat.getDateInstance().format(new Date());
			mark = mark.replace("/", ".");
			int num = (int) (Math.random()*10101010+369);
			mark = mark + "_" + num;
			//执行导出
			String cmd1 = exportDBTool("jiu", "jiu", IP, SERVICENAME, ROOTPATH, "jiu_backup_" + mark, "jiu_backup_log_" + mark, "jiu");  /////////////////////////////////////////////////////////配置连接实例参数
			// String cmd2 = exportDBTool("shengWei", "shengWei", IP, SERVICENAME, ROOTPATH, "shengWei_backup_"+mark, "shengWei_backup_log_"+mark, "shengWei");
			// String cmd3 = exportDBTool("system", "orcl", IP, SERVICENAME, ROOTPATH, "system_backup_"+mark, "system_backup_log_"+mark, "system");
			// String cmd_m = "mysqldump -P 3306 -u root@" + IP + " klweb > " + ROOTPATH + "/BackUp_Mysql_DB_" + mark + ".sql";
			// String cmd = " ping 192.168.1.111 ";
			//String cmd_end = "\r\t";
			// String[] cmds = {cmd1,cmd2,cmd3,cmd_m,cmd};
			String[] cmds = {cmd1};
			//String[] cmds = {cmd_m,cmd};
			for (int i = 0; i < cmds.length; i++) {
				System.out.println("\r\n");
				System.err.println(">>>开始备份第 " + (i+1) + " 个实例>>>");
				doExport(cmds[i]);
				System.out.println("\r\t");
			}
			long et = new Date().getTime(); //结束时间
			System.out.println("\r\n");
			System.err.println("备份成功! 耗时: " + (et-st)/1000/60 + " min");
		} catch (Exception e) {
			System.err.println("备份失败!");
			e.printStackTrace();
		}
		
	}
	
	//主程序调用shell命令并执行
	public static void main(String[] args) {
		//创建定时器对象
        Timer t = new Timer();
        //3秒后执行BackUpDbTask 类中的run方法,后面每12小时跑一次
        t.schedule(new BackUpDbTask(), 3000, 12*60*60*1000);
		
	}
	
	
	
	
}
