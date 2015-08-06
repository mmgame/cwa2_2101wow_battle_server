package com.cwa.simuiationimpl.event;

import java.util.List;

import com.cwa.simuiation.enums.EventChannelEnum;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.ISSystemEvent;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 仿真器时间结束
 * 
 * @author mausmars
 *
 */
public class ESTimeOverEvent implements ISSystemEvent {
	@Override
	public int getEventType() {
		return EventSubTypeEnum.ES_TimeOver.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_TimeOver.value();
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
	public void setChannel(int channel) {

	}

	@Override
	public void setReceivers(List<String> receivers) {

	}
}
