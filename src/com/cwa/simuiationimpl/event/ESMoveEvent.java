package com.cwa.simuiationimpl.event;

import java.util.List;

import com.cwa.simuiation.enums.EventChannelEnum;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.MoveActionContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 移动状态事件
 * 
 * @author mausmars
 * 
 */
public class ESMoveEvent extends ISStateEvent {
	private int step;

	private MoveActionContext actionContext;

	@Override
	public int getEventType() {
		return EventSubTypeEnum.ES_Move.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_Move.value();
	}

	@Override
	public int getChannel() {
		return EventChannelEnum.Whole.value();
	}

	@Override
	public int getStep() {
		return step;
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
		return actionContext.getTarget();
	}

	public MoveActionContext getActionContext() {
		return actionContext;
	}

	// -----------------------------------
	@Override
	public void setStep(int step) {
		this.step = step;
	}

	public void setActionContext(MoveActionContext actionContext) {
		this.actionContext = actionContext;
	}
}
