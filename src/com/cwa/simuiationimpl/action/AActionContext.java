package com.cwa.simuiationimpl.action;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.action.ISAction;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;

/**
 * 抽象动作上下文
 * 
 * @author mausmars
 * 
 */
public abstract class AActionContext implements IActionContext {
	protected IPerformer performer;
	protected ISAction action;
	protected ISObject target;

	protected SimuiationTime startTime;

	protected int over = StepTypeEnum.ST_Null.value();

	// ----------------------------
	@Override
	public ISAction getAction() {
		return action;
	}

	@Override
	public ISObject getTarget() {
		return target;
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
	public int getOver() {
		return over;
	}

	@Override
	public void setOver(int interrupt) {
		this.over = interrupt;
	}

	@Override
	public void resetStartTime() {
		startTime = performer.getGlobalContext().getClock().getCurrentSTime();
	}

	@Override
	public void resetContextByFrame() {
	}

	// -----------------------------------
	public void setPerformer(IPerformer performer) {
		this.performer = performer;
	}

	public void setAction(ISAction action) {
		this.action = action;
	}

	public void setTarget(ISObject target) {
		this.target = target;
	}

	public void setStartTime(SimuiationTime startTime) {
		this.startTime = startTime;
	}
}
