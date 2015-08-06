package com.cwa.simuiation.manager;

import java.util.List;
import java.util.Set;

import com.cwa.simuiation.obj.ISObject;

/**
 * 仿真对象管理（ 管理全部的对象）
 * 
 * @author mausmars
 * 
 */
public interface ISobMgr {
	/**
	 * 添加
	 * 
	 * @param sob
	 */
	void insert(ISObject sob);

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 */
	ISObject select(int id);

	/**
	 * 移除
	 * 
	 * @param id
	 * @return
	 */
	ISObject remove(int id);

	/**
	 * 恢复
	 */
	void restore();

	/**
	 * 存储
	 */
	void store();

	/**
	 * 根据类型获取objlist
	 * 
	 * @param objType
	 * @return
	 */
	List<ISObject> getSObjectListByType(int objType);
	/**
	 * 根据子类型获取objlist
	 * @param objType
	 * @return
	 */
	Set<ISObject> getSObjectListBySubType(int objSubType);
	/**
	 * 判断是否可移除
	 * 
	 */
	public void remove();
	
}
