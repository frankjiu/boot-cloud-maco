package com.utils;

/**
 * 判断空与非空
 * @author xinbe
 *
 */

public class StringUtil {

	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		return obj.toString().trim().equals("");
	}
	
	public static boolean isNotEmpty(Object obj) {
		if (obj != null && !obj.toString().trim().equals(""))
			return true;
		return false;
	}
	
	public static boolean isEmptyArray(Object[] obj) {
		if (obj == null || obj.length == 0)
			return true;
		return false;
	}
	
	public static boolean isNotEmptyArray(Object[] obj) {
		if (obj != null && obj.length != 0)
			return true;
		return false;
	}
	
}
