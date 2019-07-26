/**
 * All rights Reserved, Designed By www.xcompany.com
 * 
 * @Package com.common
 * @Description: TODO 描述
 * @author: Frankjiu
 * @date: 2019年3月28日 上午12:00:38
 * @version V1.0
 */

package com.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 获取对象属性(方法传递对象,通过反射获取对象各个字段的属性名称和属性类型,同时获取字段值, 如果字段值不为空,则接收该字段值.)
 * 
 * @author: Frankjiu
 * @date: 2019年3月28日 上午12:00:38
 */

public class ConditionsFunction {

	/**
	 * 获取对象属性名
	 */
	public static String[] getFiledName(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			String[] fieldNames = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equals("serialVersionUID") || fields[i].getName() == null) continue;
				fieldNames[i] = fields[i].getName();
				//System.out.println("属性名称:" + fieldNames[i] + " === 属性类型:" + fields[i].getType() + "=========================");
			}
			fieldNames = removeNullOfArray(fieldNames);
			return fieldNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据属性名获取属性值
	 */
	public static Object getFieldValueByName(String fieldName, Object obj) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = obj.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(obj, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 移除数组空值
	 * @param strArray
	 * @return
	 */
	private static String[] removeNullOfArray(String[] strArray) {
		List<String> list = Arrays.asList(strArray);
		List<String> newList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && !list.get(i).equals("")) {
				newList.add(list.get(i));
			}
		}
		String[] strNewArray = newList.toArray(new String[newList.size()]);
		return strNewArray;
	}

}
