package com.cwa.simuiationimpl.event;

import java.util.List;

import com.cwa.simuiation.enums.EventChannelEnum;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.IdleActionContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

public class ESIdleEvent extends ISStateEvent {
	private int step;
	private IdleActionContext actionContext;

	@Override
	public int getEventType() {
		return EventSubTypeEnum.ES_Idle.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_Idle.value();
	}

	@Override
	public int getChannel() {
		return EventChannelEnum.Whole.value();
	}

	@Override
	public List<String> getReceivers() {
		return null;
	}

	@Override
	public IPerformer getSource() {
		return actionContext.getPerformer();
	}

	@Override
	public ISObject getTarget() {
		return null;
	}

	@Override
	public int getStep() {
		return step;
	}

	public IdleActionContext getActionContext() {
		return actionContext;
	}

	// --------------------------------------------
	@Override
	public void setStep(int step) {
		this.step = step;
	}

	public void setActionContext(IdleActionContext actionContext) {
		this.actionContext = actionContext;
	}
}
