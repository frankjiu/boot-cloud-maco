package com.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.MacoArticle;
import com.domain.MacoArticleHelp;
import com.repository.MacoArticleRepository;
import com.service.MacoArticleService;
import com.utils.StringUtil;

/**
 * 服务层实现
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Service
public class MacoArticleServiceImpl implements MacoArticleService {

	@Autowired
	private MacoArticleRepository macoArticleRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 主键查询
	 */
	@Override
	public MacoArticle getOne(String id) {
		MacoArticle macoArticle = macoArticleRepository.getOne(id);
		return macoArticle;
	}
	
	/**
	 * 条件查询
	 */
	@Override
	public List<MacoArticle> findByTitleOrContent(String title, String content) {
		List<MacoArticle> MacoArticle = macoArticleRepository.findByTitleOrContent(title, content);
		return MacoArticle;
	}
	
	/**
	 * 树查询
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<MacoArticleHelp> findTree(MacoArticleHelp query) {
		//List<MacoArticleHelp> list = macoArticleRepository.findTree(query);
		//return list;
		
		String sqla = " SELECT DISTINCT TB.ID ID, TB.PID PID, TB.MENU_NAME MENU_NAME, TB.INDEX_ORDER INDEX_ORDER, TB.UPDATE_TIME UPDATE_TIME " +
				" FROM " + 
				"     (SELECT M.ID ID, M.PID PID, M.MENU_NAME MENU_NAME, M.INDEX_ORDER INDEX_ORDER, M.UPDATE_TIME UPDATE_TIME " + 
				"         FROM   MACO_MENU M " + 
				"         WHERE  1 = 1 ";
		
		String sqlb = " UNION " + 
				"         SELECT A.ID ID, A.PID PID, A.TITLE MENU_NAME, TRUNC(A.TIMES*10000) INDEX_ORDER, A.UPDATE_TIME UPDATE_TIME " + 
				"         FROM   MACO_ARTICLE A " + 
				"         WHERE  1 = 1 ";
				
		String sqlc = " START  WITH 1 = 1 " + 
				" CONNECT BY PRIOR TB.ID = TB.PID " + 
				" ORDER  BY TB.INDEX_ORDER ASC, TB.UPDATE_TIME DESC ";
		
		StringBuffer stb = new StringBuffer(sqla);
		
		// 加入权限
		stb.append(" AND M.ID IN(" + query.getMenuDbStr() + ") ");
		stb.append(sqlb);
		
		// 加入查询条件
		if (StringUtil.isNotEmpty(query.getKeyword()) && StringUtil.isNotEmpty(query.getAndKeyword()) && StringUtil.isNotEmpty(query.getOrKeyword())) {
			return null;
		}
		
		if (StringUtil.isNotEmpty(query.getKeyword())) {
			stb.append(" AND DBMS_LOB.INSTR(A.CONTENT,  '" + query.getKeyword() + "', 1, 1) > 0 ");
		}
		
		if (StringUtil.isNotEmpty(query.getKeyword()) && StringUtil.isNotEmpty(query.getAndKeyword())) {
			stb.append(" AND DBMS_LOB.INSTR(A.CONTENT,  '" + query.getAndKeyword() + "', 1, 1) > 0");
		}
		
		if (StringUtil.isNotEmpty(query.getKeyword()) && StringUtil.isNotEmpty(query.getOrKeyword())) {
			stb.append(" OR DBMS_LOB.INSTR(A.CONTENT,  '" + query.getOrKeyword() + "', 1, 1) > 0 ");
		}
		
		// 结束语句
		stb.append(" ) TB ");
		
		// 递归语句
		stb.append(sqlc);
		
		Query nativeQuery = entityManager.createNativeQuery(stb.toString());
		List list = nativeQuery.getResultList();
		
		//List list = macoArticleRepository.findTree(stb.toString(), query.getMenuDbStr());
		// createSQLQuery
    	/*List list = createSQLQuery(stb.toString())
    			.addScalar("ID", StandardBasicTypes.STRING)
    			.addScalar("PID", StandardBasicTypes.STRING)
    			.addScalar("MENU_NAME", StandardBasicTypes.STRING)
    			.addScalar("INDEX_ORDER", StandardBasicTypes.STRING)
    			.addScalar("UPDATE_TIME", StandardBasicTypes.DATE)
    			.list();  //addEntity(MacoArticleHelp.class).list();
*/    	
    	List<MacoArticleHelp> macoArticleHelpList = new ArrayList<>();
    	
    	for(Iterator iterator = list.iterator(); iterator.hasNext();){  
            //每个集合元素都是一个数组,数组元素是id,pid,menu_name三列值  
			Object[] objects = (Object[]) iterator.next();
			MacoArticleHelp macoArticleHelp = new MacoArticleHelp();
			macoArticleHelp.setId(objects[0].toString());
			macoArticleHelp.setPid(objects[1].toString());
			macoArticleHelp.setMenuName(objects[2].toString());
			macoArticleHelpList.add(macoArticleHelp);
        }  
		return macoArticleHelpList;
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional
	public MacoArticle save(MacoArticle macoArticle) {
		return macoArticleRepository.saveAndFlush(macoArticle);
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional
	public void delete(String id){
		macoArticleRepository.deleteById(id);
	}
	
	/**
	 * 修改
	 */
	@Override
	@Transactional
	public void update(MacoArticle newMacoArticle) {
		macoArticleRepository.saveAndFlush(newMacoArticle);
	}
	
}
