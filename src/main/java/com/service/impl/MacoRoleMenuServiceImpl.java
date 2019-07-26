package com.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.domain.MacoRoleMenu;
import com.repository.MacoRoleMenuRepository;
import com.service.MacoRoleMenuService;
import com.utils.ArrayToDbString;

/**
 * 服务层实现
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Service
public class MacoRoleMenuServiceImpl implements MacoRoleMenuService {

	@Autowired
	private MacoRoleMenuRepository macoRoleMenuRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 主键查询
	 */
	@Override
	public MacoRoleMenu getOne(String id) {
		MacoRoleMenu macoRoleMenu = macoRoleMenuRepository.getOne(id);
		return macoRoleMenu;
	}
	
	/**
	 * 条件查询
	 */
	@Override
	public List<MacoRoleMenu> getByRoleId(String id) {
		List<MacoRoleMenu> MacoRoleMenu = macoRoleMenuRepository.getByRoleId(id);
		return MacoRoleMenu;
	}
	
	/**
	 * 批量保存
	 */
	@Override
	@Transactional
	public boolean save(String role_id, @RequestParam(value = "menu_ids[]") String[] menu_ids) {
		boolean flag = false;
		List<String> list = new ArrayList<String>();
		if (menu_ids != null && menu_ids.length > 0) {
			for (int i = 0; i < menu_ids.length; i++) {
				MacoRoleMenu macoRoleMenu = new MacoRoleMenu();
				macoRoleMenu.setRoleId(role_id);
				macoRoleMenu.setMenuId(menu_ids[i]);
				macoRoleMenu.setCreateTime(new Date());
				macoRoleMenu = macoRoleMenuRepository.save(macoRoleMenu);
				
				list.add(macoRoleMenu.getId());
				if (i%10 == 0) {
					macoRoleMenuRepository.flush();
				}
			}
		}
		if (list.size() == menu_ids.length) {
			return flag;
		} else {
			return flag;
		}
		
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional
	public void deleteByRoleIdAndMenuIds(String role_id, @RequestParam(value = "menu_ids[]") String[] menu_ids){
		/*macoRoleMenuRepository.deleteByRoleIdAndMenuIds(role_id, menu_ids);
		List<String> list = new ArrayList<String>();
		if (menu_ids != null && menu_ids.length > 0) {
			for (int i = 0; i < menu_ids.length; i++) {
				MacoRoleMenu macoRoleMenu = new MacoRoleMenu();
				macoRoleMenu.setRoleId(role_id);
				macoRoleMenu.setMenuId(menu_ids[i]);
				macoRoleMenu.setCreateTime(new Date());
				macoRoleMenuRepository.deleteByRoleIdAndMenuIds(role_id, menu_ids);
				list.add(macoRoleMenu.getId());
				if (i%10 == 0) {
					macoRoleMenuRepository.flush();
				}
			}
		}*/
		
		String menu_id_str = ArrayToDbString.transform(menu_ids);
		String sql = " DELETE FROM MACO_ROLE_MENU WHERE ROLE_ID = '" + role_id + "' AND MENU_ID in (" + menu_id_str + ") ";
		
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
		
		//int num = getCurrentSession().createSQLQuery(sql.toString()).addEntity(MacoRoleMenu.class).executeUpdate();
		/*if (num == menu_ids.length) {
			return true;
		}else {
			return false;
		}*/
		
	}
	
}
