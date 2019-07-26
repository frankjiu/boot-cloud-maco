package com.constant;

/**
 * 枚举类
 * 
 * @author xinbe
 *
 */
public enum FreezeType {
	// 枚举类实例(枚举元素)
	NO(0, "未冻结"), YES(1, "已冻结");
	// 私有枚举类属性
	private Integer code;
	private String desc;

	// 公有set/get方法
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	// 私有带参构造
	private FreezeType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	// 根据code获取枚举类实例
	public static FreezeType getTypeByCode(Integer code) {
		// 默认返回值
		FreezeType defaultType = FreezeType.NO;
		// 遍历枚举值(枚举实例)
		for (FreezeType ftype : FreezeType.values()) {
			if (ftype.code == code) {
				return ftype;
			}
		}
		return defaultType;
	}

	// 根据code获取描述
	public static String getDescByCode(Integer code) {
		return getTypeByCode(code).desc;
	}

	// 测试方法(在实际中是在其它地方以该种方式进行调用)
	public static void main(String[] args) {
		System.out.println("----------------------------");
		System.out.println(FreezeType.getDescByCode(0));
	}
}
