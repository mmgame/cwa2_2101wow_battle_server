package com.cwa.message.event2message;

import com.cwa.message.BattleMessage.TrapTriggerDown;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.message.IEventTransition;
import com.cwa.simuiationimpl.event.ESTrapEvent;
import com.cwa.simuiationimpl.obj.Trap;

/**
 * 陷阱触发事件转消息
 * 
 * @author mausmars
 * 
 */
public class TrapEventTransition implements IEventTransition {
	@Override
	public Object transition(ISEvent event) {
		ESTrapEvent e = (ESTrapEvent) event;

		Trap t=e.getTrap();
		TrapTriggerDown.Builder tBuilder = TrapTriggerDown.newBuilder();
		tBuilder.setPerformerId(t.getId());
		tBuilder.setKeyId(t.getTrapPrototype().getKeyId());
		tBuilder.addAllTrapTriggerBean(e.getTrapTriggerBeanList());
		
		return tBuilder.build();
	}
}
