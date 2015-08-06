package com.cwa.message.event2message;

import com.cwa.battleimpl.event.BattleStateChangeEvent;
import com.cwa.message.BattleMessage.BattleStepDown;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.message.IEventTransition;

/**
 * 战场状态改变消息
 * 
 * @author mausmars
 *
 */
public class BattleStateChangeEventTransition implements IEventTransition {
	@Override
	public Object transition(ISEvent event) {
		BattleStateChangeEvent e = (BattleStateChangeEvent) event;

		BattleStepDown.Builder b = BattleStepDown.newBuilder();
		b.setStep(e.getStep());
		return b.build();
	}
}
