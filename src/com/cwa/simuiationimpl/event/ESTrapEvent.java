package com.cwa.simuiationimpl.event;

import java.util.List;

import com.cwa.message.BattleMessage.TrapTriggerBean;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.ISTrapEvent;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.obj.Trap;

public class ESTrapEvent implements ISTrapEvent{
	private Trap trap;//陷阱
	private List<TrapTriggerBean> trapTriggerBeanList;
	
	
	@Override
	public int getEventType() {
		return EventSubTypeEnum.ET_Trap.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ET_Trap.value();
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
	public void setChannel(int channel) {
		
	}

	@Override
	public void setReceivers(List<String> receivers) {
		
	}

	public Trap getTrap() {
		return trap;
	}

	public void setTrap(Trap trap) {
		this.trap = trap;
	}

	public List<TrapTriggerBean> getTrapTriggerBeanList() {
		return trapTriggerBeanList;
	}

	public void setTrapTriggerBeanList(List<TrapTriggerBean> trapTriggerBeanList) {
		this.trapTriggerBeanList = trapTriggerBeanList;
	}
}
