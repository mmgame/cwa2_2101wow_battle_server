package com.cwa.simuiationimpl.passiveskill;

import java.util.HashMap;
import java.util.Map;

import com.cwa.prototype.PassiveSkillPrototype;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.passiveskill.IPassiveSkillContext;
import com.cwa.simuiation.passiveskill.IPassiveSkillManager;
import com.cwa.simuiationimpl.passiveskill.impl.GiveBloodPassiveSkill;

public class PassiveSkillManager implements IPassiveSkillManager {
	// 拥有者
	private IPerformer performer;
	private Map<Integer, IPassiveSkillContext> contextMap = new HashMap<Integer, IPassiveSkillContext>();
	
	public PassiveSkillManager(IPerformer performer) {
		this.performer = performer;
	}
	@Override
	public IPerformer getPerformer() {
		return performer;
	}

	@Override
	public void enter(Integer id) {
		PassiveSkillPrototype passiveSkillPrototype = performer.getGlobalContext().getProtpyeMgr().getPassiveSkillPrototype(id);
		if (passiveSkillPrototype == null) {
			return ;
		}
		PassiveSkillContext context = new PassiveSkillContext();
		context.setPerformer(performer);
		context.setPassiveSkillPrototype(passiveSkillPrototype);
		context.setStartTime(performer.getGlobalContext().getClock().getCurrentSTime());
		context.setPassiveSkill(new GiveBloodPassiveSkill());
		contextMap.put(passiveSkillPrototype.getKeyId(), context);
	}

	@Override
	public void update() {
		for(IPassiveSkillContext context : contextMap.values()){
			context.getPassiveSkill().update(context);
		}
	}

	@Override
	public void clear() {
		contextMap.clear();
	}

}
