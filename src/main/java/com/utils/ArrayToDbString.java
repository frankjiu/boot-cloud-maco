package com.utils;

/**
 * 将数组转化为便于数据库批量操作的以逗号分隔的String字符串
 * @author xinbe
 *
 */
public class ArrayToDbString {
	
	public static String transform (String[] array){
		String db_id_str = "";
		for (String id : array) {
			if (db_id_str.length() > 0) {
				db_id_str += ",";
			}
			db_id_str += "'" + id + "'";
		}
		return db_id_str;
	}
	
}