package com.cwa.message.event2message;

import com.cwa.message.BattleMessage.BattleOverDown;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.message.IEventTransition;
import com.cwa.simuiationimpl.event.ESOverEvent;

public class BattleOverEventTransition implements IEventTransition{

	@Override
	public Object transition(ISEvent event) {
		ESOverEvent e = (ESOverEvent) event;
		int failureTroopType = e.getFailureTroopType();
		BattleOverDown.Builder builder = BattleOverDown.newBuilder();
		builder.setFailureTroopType(failureTroopType);
		return builder.build();
	}

}
