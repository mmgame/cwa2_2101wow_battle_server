package com.cwa.simuiationimpl.event;

import java.util.List;

import com.cwa.simuiation.enums.EventChannelEnum;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.ISSystemEvent;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 仿真结束事件
 * 
 * @author mausmars
 * 
 */
public class ESOverEvent implements ISSystemEvent {
	// private static ESOverEvent overEvent = new ESOverEvent();

	private int failureTroopType;// 失败方攻击类型
	// public static ESOverEvent getInstance() {
	// return overEvent;
	// }

	@Override
	public int getEventType() {
		return EventSubTypeEnum.ES_SOver.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_SOver.value();
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

	public int getFailureTroopType() {
		return failureTroopType;
	}

	public void setFailureTroopType(int failureTroopType) {
		this.failureTroopType = failureTroopType;
	}
}
