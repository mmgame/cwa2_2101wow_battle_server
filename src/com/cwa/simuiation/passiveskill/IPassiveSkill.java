package com.cwa.simuiation.passiveskill;


public interface IPassiveSkill {
	/**
	 * 得到PassiveSkill类型
	 * 
	 * @return
	 */
	int getType();

	/**
	 * PassiveSkill持续
	 * 
	 * @param performer
	 */
	void update(IPassiveSkillContext context);
}
