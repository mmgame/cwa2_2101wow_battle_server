package com.cwa.message.event2message;

import com.cwa.message.BattleMessage.CoordinateBean;
import com.cwa.message.BattleMessage.IdleActionDown;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.message.IEventTransition;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.event.ESIdleEvent;

public class IdleEventTransition implements IEventTransition {
	@Override
	public Object transition(ISEvent event) {
		ESIdleEvent e = (ESIdleEvent) event;

		IPerformer source = e.getSource();
		IdleActionDown.Builder builder = IdleActionDown.newBuilder();
		builder.setPerformerId(source.getId());

		CoordinateBean.Builder coordinate = CoordinateBean.newBuilder();
		for (int i = 0; i < source.getPosition().getDimensions(); i++) {
			double v = source.getPosition().getCoordinate(i);
			coordinate.addC((int) v);
		}
		builder.setPosition(coordinate);
		return builder.build();
	}
}
