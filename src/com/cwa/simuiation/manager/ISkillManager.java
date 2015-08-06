package com.cwa.simuiation.manager;

import com.cwa.simuiation.clock.SimuiationTime;

/**
 * 技能管理
 * 
 * @author mausmars
 * 
 */
public interface ISkillManager {
	/**
	 * cd验证
	 * 
	 * @param skillId
	 * @param cdTime
	 * @param ct
	 * @return
	 */
	boolean isFinishedCd(int skillId, long cdTime, SimuiationTime ct);

	/**
	 * 重置cd
	 * 
	 * @param skillId
	 * @param ct
	 */
	void resetCd(int skillId, SimuiationTime ct);
	
	/**
	 * 得到当前cd时间
	 * @param skillId
	 * @return
	 */
	long getCdTime(int skillId);
}
