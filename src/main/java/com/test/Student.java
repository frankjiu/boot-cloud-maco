package com.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 重写对象的hashCode 和 equals方法
 * @author xinbe
 *
 */
@Data
@Entity
public class Student {
	
	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;
	
	@Transient
	private String name;// 姓名
	
	@Transient
	private String sex;// 性别
	
	@Transient
	private String age;// 年龄
	
	@Transient
	private float weight;// 体重
	
	@Transient
	private String addr;// 地址

	// 重写hashcode方法
	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 17 * result + sex.hashCode();
		result = 17 * result + age.hashCode();
		return result;
	}

	// 重写equals方法
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Student)) {
			// instanceof 已经处理了obj = null的情况
			return false;
		}
		Student stuObj = (Student) obj;
		// 地址相等
		if (this == stuObj) {
			return true;
		}
		// 如果两个对象姓名、年龄、性别相等，我们认为两个对象相等
		if (stuObj.name.equals(this.name) && stuObj.sex.equals(this.sex) && stuObj.age.equals(this.age)) {
			return true;
		} else {
			return false;
		}
	}

	
}
