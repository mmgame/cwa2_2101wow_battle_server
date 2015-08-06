package com.cwa.simuiationimpl.event;

import java.util.List;

import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

public class ESDeadEvent extends ISStateEvent {
	private IPerformer performer;

	@Override
	public int getEventType() {
		return EventSubTypeEnum.ES_Dead.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_Dead.value();
	}

	@Override
	public int getChannel() {
		return 0;
	}

	@Override
	public List<String> getReceivers() {
		return null;
	}

	@Override
	public IPerformer getSource() {
		return null;
	}

	@Override
	public ISObject getTarget() {
		return null;
	}

	@Override
	public int getStep() {
		return 0;
	}

	@Override
	public void setStep(int step) {
	}

	public void setPerformer(IPerformer performer) {
		this.performer = performer;
	}

	public IPerformer getPerformer() {
		return performer;
	}
}
