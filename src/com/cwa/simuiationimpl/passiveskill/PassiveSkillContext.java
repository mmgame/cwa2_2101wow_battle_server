package com.cwa.simuiationimpl.passiveskill;

import com.cwa.prototype.PassiveSkillPrototype;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.passiveskill.IPassiveSkill;
import com.cwa.simuiation.passiveskill.IPassiveSkillContext;

public class PassiveSkillContext implements IPassiveSkillContext {
	private PassiveSkillPrototype passiveSkillPrototype;
	private SimuiationTime startTime;
	private IPerformer performer;
	private IPassiveSkill passiveSkill;
	@Override
	public IPassiveSkill getPassiveSkill() {
		return passiveSkill;
	}

	@Override
	public int getId() {
		return passiveSkillPrototype.getKeyId();
	}

	@Override
	public SimuiationTime getStartTime() {
		return startTime;
	}

	@Override
	public IPerformer getPerformer() {
		return performer;
	}

	@Override
	public PassiveSkillPrototype getPassiveSkillPrototype() {
		return passiveSkillPrototype;
	}

	public void setPassiveSkillPrototype(PassiveSkillPrototype passiveSkillPrototype) {
		this.passiveSkillPrototype = passiveSkillPrototype;
	}

	public void setStartTime(SimuiationTime startTime) {
		this.startTime = startTime;
	}

	public void setPerformer(IPerformer performer) {
		this.performer = performer;
	}

	public void setPassiveSkill(IPassiveSkill passiveSkill) {
		this.passiveSkill = passiveSkill;
	}

	@Override
	public void resetContext() {
		this.startTime = performer.getGlobalContext().getClock().getCurrentSTime();
	}
}
