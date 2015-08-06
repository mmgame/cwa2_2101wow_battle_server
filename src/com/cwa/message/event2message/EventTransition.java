package com.cwa.message.event2message;

import java.util.Map;

import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.message.IEventTransition;

/**
 * 事件转消息
 * 
 * @author mausmars
 * 
 */
public class EventTransition implements IEventTransition {
	private Map<Integer, IEventTransition> eventTransitionMap;

	@Override
	public Object transition(ISEvent event) {
		IEventTransition eventTransition = eventTransitionMap.get(event.getEventSubType());
		if (eventTransition == null) {
			return null;
		}
		return eventTransition.transition(event);
	}

	// ---------------------------------------------------
	public void setEventTransitionMap(Map<Integer, IEventTransition> eventTransitionMap) {
		this.eventTransitionMap = eventTransitionMap;
	}
}
