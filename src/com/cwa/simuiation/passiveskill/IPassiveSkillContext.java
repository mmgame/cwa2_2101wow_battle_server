package com.cwa.simuiation.passiveskill;

import com.cwa.prototype.PassiveSkillPrototype;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.obj.IPerformer;

public interface IPassiveSkillContext {
	/**
	 * PassiveSkill接口
	 * 
	 * @return
	 */
	IPassiveSkill getPassiveSkill();

	/**
	 * PassiveSkill的id
	 * 
	 * @return
	 */
	int getId();

	/**
	 * 开始时间
	 * 
	 * @return
	 */
	SimuiationTime getStartTime();

	/**
	 * actor
	 * 
	 * @return
	 */
	IPerformer getPerformer();
	
	/**
	 * 获取原型数据
	 * @return
	 */
	PassiveSkillPrototype getPassiveSkillPrototype();
	
	void resetContext();
}
