package com.cwa.simuiationimpl.manage;

import java.util.HashMap;
import java.util.Map;

import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.manager.ISkillManager;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 技能管理类
 * 
 * @author yangfeng
 * 
 */
public class SkillManager implements ISkillManager {
	private Map<Integer, SimuiationTime> skillCdMap = new HashMap<Integer, SimuiationTime>();

	// 判断cd时间是否符合
	public boolean isFinishedCd(int skillId, long cdTime, SimuiationTime ct) {
		SimuiationTime cd = skillCdMap.get(skillId);
		if (cd == null) {
			return true;
		}
		return (ct.getFrameCount()-cd.getFrameCount())*(SClock.getFrameInterval()+SimuiationConstant.SystemUpdateTime)+ct.getOffset()-cd.getOffset()>=cdTime;
	}

	// cd重置
	public void resetCd(int skillId, SimuiationTime ct) {
		skillCdMap.put(skillId, ct);
	}
	
	public long getCdTime(int skillId){
		SimuiationTime cd = skillCdMap.get(skillId);
		if(cd == null){
			return 0;
		}
		return cd.getMSTime();
//		return cd.getFrameCount()*(SClock.getFrameInterval()+SimuiationConstant.SystemUpdateTime)+cd.getOffset();
	}
}