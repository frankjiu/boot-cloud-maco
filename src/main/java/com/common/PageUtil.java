package com.common;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据列表分页工具类
 * 
 * @param currentPage
 *            当前页
 * @param pageSize
 *            每页的大小     
 * @param totalCount
 *            总记录数     
 */
public class PageUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	// 分页相关参数
	@Setter @Getter private int firstResult; // 分页的起始索引
	@Setter @Getter private int maxResult; // 结束索引
	@Setter @Getter private int pageSize; // 每页的记录条数
	@Setter @Getter private int currentPage; // 当前页
	@Setter @Getter private int totalPage; // 总页数
	@Setter @Getter private int totalCount; // 总的记录数
	@SuppressWarnings("rawtypes")
	@Setter @Getter private List pageList; // 每页的数据(用于list分页查询)
	
	// 分页逻辑计算
	public void pageInit(int currentPage, int pageSize, int totalCount) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage = totalCount % pageSize != 0 ? totalCount / pageSize + 1 : totalCount / pageSize;
		this.currentPage = currentPage >= totalPage ? totalPage : currentPage;
		this.currentPage = this.currentPage <= 1 ? 1 : this.currentPage;
		this.firstResult = (currentPage - 1) * pageSize;
		this.maxResult = pageSize * currentPage;
		if (this.maxResult >= totalCount) {
			this.maxResult = totalCount;
		}
	}
	
}
