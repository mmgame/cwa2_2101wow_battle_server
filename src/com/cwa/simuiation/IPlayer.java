package com.cwa.simuiation;

import com.cwa.component.event.ILocalEvent;
import com.cwa.simuiation.event.ISEvent;

public interface IPlayer extends IClientSimuiation {
	/**
	 * 队伍类型
	 * 
	 * @return
	 */
	int getTroopType();

	/**
	 * 发送事件
	 * 
	 * @param event
	 */
	void sendEvent(ISEvent event);

	/**
	 * 发送事件
	 * 
	 * @param event
	 */
	void sendEvent(Object listenerType, ILocalEvent event);
}
