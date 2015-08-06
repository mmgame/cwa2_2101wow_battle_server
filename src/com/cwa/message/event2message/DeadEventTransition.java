package com.cwa.message.event2message;

import com.cwa.message.BattleMessage.DeadDown;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.message.IEventTransition;
import com.cwa.simuiationimpl.event.ESDeadEvent;

public class DeadEventTransition implements IEventTransition {
	@Override
	public Object transition(ISEvent event) {
		ESDeadEvent e = (ESDeadEvent) event;
		int pid = e.getPerformer().getId();
		DeadDown.Builder builder = DeadDown.newBuilder();
		builder.setPerformerId(pid);
		return builder.build();
	}
}
