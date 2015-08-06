package com.cwa.simuiation.passiveskill;

import com.cwa.simuiation.obj.IPerformer;

public interface IPassiveSkillManager {
	/**
	 * 拥有者
	 */
	IPerformer getPerformer();

	/**
	 * 进入
	 */
	void enter(Integer id);
	
	/**
	 * 更新
	 */
	void update();
	
	/**
	 * 清空
	 */
	void clear();
}
